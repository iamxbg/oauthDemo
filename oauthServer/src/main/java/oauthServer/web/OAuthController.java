package oauthServer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import static oauthServer.util.OAuthUtils.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthAuthorizationResponseBuilder;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthTokenResponseBuilder;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import oauthServer.util.OAuthParamsException;
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
	public ModelAndView authorizeView(HttpServletRequest req,ModelAndView mav,HttpSession session){

		
		try {
			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
				
//				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
//				mav.addObject(OAuth.OAUTH_CLIENT_SECRET, authzReq.getClientSecret());
//				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
//				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
//				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
			
				mav.addObject("state", authzReq.getState());
				
				mav.setViewName("/authorization.jsp");
				
				return mav;
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	

	}

	@RequestMapping(path="/redirectUri")
	public ModelAndView successAuthzRedirect(@RequestParam("redirectUri") String redirectUri,
					@RequestParam("state") String state, ModelAndView mav){
		
		mav.addObject("redirectUri", redirectUri);
		mav.addObject("state", state);
		
		mav.setViewName("http://localhost:8081/oauthClient/oauth/authzResult");
		
	}

	@RequestMapping(path="/authorize")
	@Consumes("application/x-www-form-urlencoded")   //?
	@Produces("application/json")
	public ResponseEntity authorize(HttpServletRequest req,ModelAndView mav){

		
		try {
			OAuthAuthzRequest request=new OAuthAuthzRequest(req);
			
			
				
				String response_type=request.getResponseType();
				String client_id=request.getClientId();
				String redirect_uri=request.getRedirectURI();
				Set<String> scopes=request.getScopes();
				String state=request.getState();
				String user_id=request.getParam("user_id");
				String username=request.getParam("username");
				String password=request.getParam("password");
				//validate from DB if clent_id ,redirect_uri, and user is ok!
				String scope=OAuthUtils.convertScopesToScopeStr(scopes);
				
				try{
					if(client_id==null || "".equals(client_id)) throw new OAuthParamsException("client_id should not be empty!");
					else if(redirect_uri==null || "".equals(redirect_uri)) throw new OAuthParamsException("redirect_uri should not be empty");
					else if(scope==null || "".equals(scope)) throw new OAuthParamsException("scope should not be empty");
					else if(response_type==null || "".equals(response_type)) throw new OAuthParamsException("responseType should not be empty");
					else if(user_id==null || "".equals(user_id)) throw new OAuthParamsException("user_id should not be empty");
					else if(username==null || "".equals(username)) throw new OAuthParamsException("username should not be empty");
					else if(password==null || "".equals(password)) throw new OAuthParamsException("password should not be empty");
					else if(state==null || "".equals(state)) throw new OAuthParamsException("state should not be empty!");
				}catch(OAuthParamsException e){
					mav.addObject("Error", e.getMessage());
					mav.setStatus(HttpStatus.BAD_REQUEST);
					//return mav;
					return null;
				}

				User user=userService.findByUsernameAndPassword(username, password);
				
				
				if(user!=null){
					//check if DB's user_id equal with form-submit!
					
					//if equals
						//authorize success,return data:
							/*
							 * 	HTTP/1.1 302 Found
							 * 	Location:https://example.com/callback?
							 * 		code=...&state (put code and state in headers or body is both OK!)
							 */
					
						String authzCode=oauthService.addAuthzCode(client_id, user_id, scope);
						
//						mav.addObject(OAuth.OAUTH_CODE, authzCode);
//						mav.addObject(OAuth.OAUTH_STATE, state);
//						mav.setViewName(new RedirectView(redirect_uri,false).toString());
//						mav.setStatus(HttpStatus.OK);
//						return null;
						
						HttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager();
						HttpClient client=HttpClients.custom().setConnectionManager(connManager)
											.setDefaultRequestConfig(
													RequestConfig.custom()
													.setConnectionRequestTimeout(2000)
													.setConnectTimeout(5000)
												.build()).build();
						HttpHost host=new  HttpHost("localhost", 8081, "http");
						org.apache.http.HttpRequest httpReq=new BasicHttpEntityEnclosingRequest("POST", "http://localhost:8081/oauthClient/oauth/authzResult");
						HttpResponse resp=client.execute(host, httpReq);
							
						if(resp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
							//redirect view to predefined redirect-view
							// need some params?
							mav.setView(new RedirectView(redirect_uri, false));
						}
						
				}else{

				}

		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, "Oh,some error here!");
			//mav.addObject("Error", e.getMessage());
			//mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			//mav.setView(new RedirectView("/oauth/authorizeView", true));
			//return mav;
			
			
//			ConnectionConfig config=ConnectionConfig.custom()
//							.setCharset(Charset.forName("utf-8")).build();
//			
//			PoolingHttpClientConnectionManager manager=new PoolingHttpClientConnectionManager();
//			
//			HttpClient client=HttpClients.custom()
//							.setConnectionManager(manager).build();
//				
//				HttpResponse resp=client.execute(target, request)

//				try {
//					return ResponseEntity.created(new URI("http://localhost:8081/oauthClient/oauth/authorize"))
//								.body(e.getMessage());
//
//				} catch (URISyntaxException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//					return null;
//				}

				//return ResponseEntity.badRequest().body(e.getMessage());

				

					
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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
