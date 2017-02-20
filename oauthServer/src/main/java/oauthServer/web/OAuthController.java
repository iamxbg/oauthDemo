package oauthServer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import static oauthServer.util.OAuthUtils.*;

import java.util.List;
import java.util.Properties;
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
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
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
import oauthServer.service.RegistrationService;
import oauthServer.service.UserService;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthTokenParams;

@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	
	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService oauthService;

	
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

		
		//prepare the error page!
		
		return null;
	

	}


	@RequestMapping(path="/authorize")
	public ModelAndView authorize(@RequestParam("username") String username
				,@RequestParam("password") String password
				,HttpServletRequest req
				,HttpServletResponse  resp
				,ModelAndView mav){

		
		User user=userService.findByUsernameAndPassword(username, password);

		
		OAuthAuthzRequest authzRequest=null;
		OAuthResponse response=null;
		
		
		try {
			authzRequest=new OAuthAuthzRequest(req);
		
			if(user!=null){
				
				logger.log(Level.INFO, "found user:"+user.getName());
				//authorize successful, return authorization-code ,
				// and redirect to client_server.
				
				String authzCode=generateAuthorizationCode();
				
//				 response=OAuthASResponse.authorizationResponse(req, HttpServletResponse.SC_FOUND)
//					.setCode("authzCode")
//					.setExpiresIn(OAuthConstants.AUTHORIZATION_CODE_EXPIRE_IN)
//					.setScope(getScope())
//					.location(authzRequest.getRedirectURI())
//					.buildJSONMessage();

				 
				 mav.addObject(OAuth.OAUTH_CODE, "AuthzCode233");
				// mav.addObject(OAuth.OAuth, OAuthConstants.AUTHORIZATION_CODE_EXPIRE_IN);
				 mav.addObject(OAuth.OAUTH_SCOPE, findScope());
				 mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzRequest.getRedirectURI());
				 
				 
				 
				 mav.setView(new RedirectView("http://localhost:8081/oauthClient/oauth/authzResult"));
				 
				 return mav;
			}else{
				
				//user not authorization pass, return authorization view.
				
				response=OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setState(authzRequest.getState())
					.location(authzRequest.getRedirectURI())
					.buildHeaderMessage();
		
			
				
				
			}

		} catch (OAuthSystemException e1) {
			// TODO Auto-generated catch block		
			e1.printStackTrace();
			logger.log(Level.ERROR, e1.getMessage());
			
		} catch (OAuthProblemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.log(Level.ERROR, e1.getMessage());

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
	public ResponseEntity token(HttpServletRequest req){
		
		OAuthTokenRequest request;
		try {
			request = new OAuthTokenRequest(req);
			
			if(request.getGrantType().equalsIgnoreCase("AUTHORIZATION_CODE")){
				
				//validate authorization_code
				//use redis
				
				// if valid,generate token
				
			}else{
				logger.log(Level.ERROR, "Error grant type:"+request.getGrantType());
			}
			
		} catch (OAuthSystemException |OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
			
		
		
		return null;
	}
	
	
	
	
	
}
