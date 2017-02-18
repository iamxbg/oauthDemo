package oauthServer.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.apache.oltu.oauth2.common.parameters.OAuthParametersApplier;
import org.apache.oltu.oauth2.httpclient4.HttpClient4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.User;
import oauthServer.service.OAuthService;
import oauthServer.service.UserService;

@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	private static String AUTHORIZATION_ENDPOINT="http://localhost:8082/oauthServer/oauth/authorizationEndpoint";
	private static String TOKEN_ENDPOINT="http://localhost:8082/oauthServer/oauth/tokenEndpoint";
	
	private static String ACCESS_TOKEN="FAKE_TOKEN";
	
	private static String REDIRECTION_URI="localhost:8081/oauthClient/oauth/";
	
	private static int RESPONSE_CODE_ACCEPT=302;
	
	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private OAuthService oauthService;
	
	/*
	OAuthClientRequest ocRequest=OAuthClientRequest.tokenLocation(TOKEN_ENDPOINT)
				.setClientId(OAuthClientConstants.CLIENT_ID)
				.setClientSecret(OAuthClientConstants.CLIENT_SECRECT)
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				//.setCode(code)
				.buildHeaderMessage();
	
	OAuthJSONAccessTokenResponse resp=ocli.accessToken(ocRequest, "post");
	*/
	
	
	@RequestMapping(path="/authorize")
	public ModelAndView authorize_credential(@RequestParam("oauthParams") 
			,HttpServletRequest req){
		
		logger.log(Level.INFO, "username:"+username+" password:"+password);
		
		//validate user info

		try {
			OAuthAuthzRequest request=new OAuthAuthzRequest(req);
				request.getParam("username");
				request.getParam("password");
				
				String redirectURI=request.getRedirectURI();
				String responseType=request.getResponseType();
				
				//request.getState();
				
				
				User user=userService.findByUsernameAndPassword(username, password);
				
				
				
				if(user!=null){
				
					//OAuthAuthzResponse response=OAuthAuthzResponse.oauthCodeAuthzResponse(request)
					
					OAuthResponse response=null;
							
					if(ResponseType.CODE.equals(request.getResponseType())){
						
						String redirectUri=oauthService.findRedirectLocationUriByClient_id("get client_id from requset-header");
						
						response=new OAuthAuthorizationResponseBuilder(req, RESPONSE_CODE_ACCEPT)
									.setCode("fake_access_code")
									.setExpiresIn(3600l)
									.setScope("xxx_scope")
									//.setParam(key, value)
									.location(redirectUri)
									.buildHeaderMessage();
						
					}else if(ResponseType.TOKEN.equals(request.getResponseType())){
						response=new OAuthAuthorizationResponseBuilder(req, RESPONSE_CODE_ACCEPT)
								.setAccessToken("accessToken")
								.setExpiresIn(1000l)
								.setScope("xxx_scope")
								.location("location, which supplied by client_app")
								//.setParam(key, value)
								.buildHeaderMessage();
					}
					
					OAuthResponse response=new OAuthAuthorizationResponseBuilder(req, RESPONSE_CODE_ACCEPT)
							.setCode("fake")
							.setExpiresIn(3600l)
							.setScope("xxxx")
							//.setParam(key, value)
							.buildHeaderMessage();
					
					
					response.setLocationUri(uri);
						
								
					
				}else{
					return null;
				}
				
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	@RequestMapping(path="/registry")
	public ModelAndView registry(@RequestParam("client_id") String client_id
			,@RequestParam("client_secrect") String client_secrect,
			HttpRequest req){
		logger.log(Level.INFO, "server@registration -- #client_id:"+client_id+" #client_sercret:"+client_secrect);
		
		// validate if client_id combine client_secrect is both OK.
		
		
		return null;
	}
	
	
	@RequestMapping(path="/changeToken")
	public ModelAndView token(@RequestParam("oauthTokenParam") OAuthTokenParams params,HttpServletRequest req){
		OAuthTokenRequest request=new OAuthTokenRequest(req);
		//OAuthJSONAccessTokenResponse response=new OAuthJSONAccessTokenResponse();
		
		//Validate key by state and tag,validate expire_in...
		params
		
		OAuthTokenResponseBuilder builder=new OAuthTokenResponseBuilder(HttpStatus.OK)
						.setAccessToken(ACCESS_TOKEN)
						.setTokenType(TokenType.BEARER.toString())
						
						
		return null;
	}
	
	
	
	
	
}
