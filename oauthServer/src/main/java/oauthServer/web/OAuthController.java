package oauthServer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import static oauthServer.util.OAuthUtils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthAuthorizationResponseBuilder;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthTokenResponseBuilder;
import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthErrorResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthErrorResponseBuilder;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.apache.oltu.oauth2.common.parameters.OAuthParametersApplier;
import org.apache.oltu.oauth2.httpclient4.HttpClient4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import oauthServer.model.User;
import oauthServer.service.OAuthService;
import oauthServer.service.RegistrationService;
import oauthServer.service.UserService;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthTokenParams;
import oauthServer.util.OAuthUtils;

@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	
	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private OAuthService oauthService;
	
	@RequestMapping(path="/authorizeView")
	public ModelAndView authorize_credential(HttpServletRequest req,ModelAndView mav){

		
		try {
			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
				
				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
				mav.addObject(OAuth.OAUTH_CLIENT_SECRET, authzReq.getClientSecret());
				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
				
				mav.setViewName("/authorization.jsp");
				
				return mav;
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	

	}


	@RequestMapping(path="/authorize")
	@Consumes("application/x-www-form-urlencoded")   //?
	@Produces("application/x-www-form-urlencoded")
	public ModelAndView authorize(HttpServletRequest req,ModelAndView mav){

		
		try {
			OAuthAuthzRequest request=new OAuthAuthzRequest(req);
				
				String response_type=request.getResponseType();
				String client_id=request.getClientId();
				String redirect_uri=request.getRedirectURI();
				Set<String> scopes=request.getScopes();
				String state=request.getState();
				String user_id=request.getParam("user_id");
				
				//using username and password to validate now, may replcae later!
				
				String username=request.getParam("username");
				String password=request.getParam("password");
				
				//validate from DB if clent_id ,redirect_uri, and user is ok!
				
				User user=userService.findByUsernameAndPassword(username, password);
				
				
				if(user!=null){
					//check if DB's user_id equal with form-submit!
					
					//if equals
						String scope=OAuthUtils.convertScopesToScopeStr(scopes);
						
						
						String authzCode=oauthService.addAuthzCode(client_id, user_id, scope);
						
						mav.addObject(OAuth.OAUTH_CODE, "code");
						mav.addObject(OAuth.OAUTH_STATE, "state");
	
						
						mav.setViewName(new RedirectView(redirect_uri,false).toString());
						return mav;
						
				}
				
				
				
			
		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	
	/**
	 *  use authorization-code change for access token.
	 * @param params
	 * @param req
	 * @return
	 */
	@RequestMapping(path="/token")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public ResponseEntity<Map<String, String>> token(HttpServletRequest req){
		
		OAuthTokenRequest request;

				try {
					request = new OAuthTokenRequest(req);
					if(request.getGrantType().equalsIgnoreCase("AUTHORIZATION_CODE")){
						
						String client_id=request.getClientId();
						String user_id=request.getParam("user_id");
						Set<String> scopes=request.getScopes();
						
						if(oauthService.isAuthzCodeExist(request.getCode())){
							
								String scope=OAuthUtils.convertScopesToScopeStr(scopes);
								String token=oauthService.addAccessToken(client_id, user_id, scope);
								
								// resposne with acess-code
//								OAuthResponse response=new OAuthTokenResponseBuilder(404)
//									.setAccessToken(token)
//									.setExpiresIn(OAuthConstants.ACCESS_TOKEN_TOKEN_EXPIRE_IN)
//									.setTokenType(TokenType.BEARER.toString())
//									.setScope("fake_scope")
//									.buildJSONMessage();
							
								Map<String, String> result=new HashMap<>();
									result.put(OAuth.OAUTH_ACCESS_TOKEN,token);
									result.put(OAuth.OAUTH_EXPIRES_IN, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRE_IN);
									result.put(OAuth.OAUTH_TOKEN_TYPE, OAuth.OAUTH_BEARER_TOKEN);
									//result.put(OAuth.OAUTH_REFRESH_TOKEN, value)
									result.put(OAuth.OAUTH_CLIENT_ID, request.getClientId());
									result.put("user_id", request.getParam("user_id"));
									
									return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
							}
							
							
						}else{
							//token not exist or expired
							
							//check reason
							
							
						}
				} catch (OAuthSystemException | OAuthProblemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				


		
		
		return null;
	}
	
	/**
	 *  use refresh token change for access_token
	 * @param req
	 * @return
	 */
	@RequestMapping(path="/refreshToken")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest req){
		
		OAuthTokenRequest request;
		try {
			request = new OAuthTokenRequest(req);
			if(request.getGrantType().equals(OAuth.OAUTH_REFRESH_TOKEN)){
				String getRefreshToken=request.getRefreshToken();
				
				//refresh_token save in where is properly?
				
				//search for refresh-token
				
				
				Map<String, String> result=new HashMap<>();
					result.put(OAuth.OAUTH_ACCESS_TOKEN, "");
					result.put(OAuth.OAUTH_TOKEN_TYPE, OAuth.OAUTH_BEARER_TOKEN);
					result.put(OAuth.OAUTH_EXPIRES_IN, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRE_IN);
					result.put(OAuth.OAUTH_REFRESH_TOKEN, "");
					result.put(OAuth.OAUTH_SCOPE, "");
					
					
				
				return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
			}else{
				
			}
		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
		
		
		return null;
	}
	
	
	
	
	
}
