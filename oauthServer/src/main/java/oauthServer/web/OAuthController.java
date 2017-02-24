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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
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
import org.json.JSONObject;
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

import oauthServer.exception.OAuthAuthzException;
import oauthServer.exception.OAuthClientHttpResponseException;
import oauthServer.exception.OAuthRequestParamsException;
import oauthServer.exception.OAuthRequestParamsException;
import oauthServer.model.Registration;
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
	private RegistrationService regService;
	@Autowired
	private OAuthService oauthService;
	
	@RequestMapping(path="/authorizeView")
	public ModelAndView authorizeView(HttpServletRequest req,ModelAndView mav,HttpSession session){

		
		try {
			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
				
//				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
//				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
//				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
//				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
			
				session.setAttribute(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
				session.setAttribute(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
				session.setAttribute(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
				
				//can set this requestParams to Session, later retrive them.			
				//how to define state of request!!!
				mav.addObject("state", authzReq.getState());

				return mav;
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());
			
		} 
		//authorization-page should has a place show this system errors!!!
		mav.setViewName("/authorization.jsp");
		return mav;
	

	}


	@RequestMapping(path="/authorize")
	@Consumes("application/x-www-form-urlencoded")   //?
	@Produces("application/x-www-form-urlencoded")
	public ModelAndView authorize(HttpServletRequest req,ModelAndView mav){

		try {
			OAuthAuthzRequest request=new OAuthAuthzRequest(req);

				String response_type=request.getResponseType();
				String client_id=request.getClientId();
				//String redirect_uri=request.getRedirectURI();
				Set<String> scopes=request.getScopes();
				String state=request.getState();
				String user_id=request.getParam("user_id");
				String username=request.getParam("username");
				String password=request.getParam("password");
				//validate from DB if clent_id ,redirect_uri, and user is ok!
				String scope=OAuthUtils.convertScopesToScopeStr(scopes);

				if(client_id==null || "".equals(client_id)) throw new OAuthRequestParamsException("client_id should not be empty!");
				//else if(redirect_uri==null || "".equals(redirect_uri)) throw new OAuthParamsException("redirect_uri should not be empty");
				else if(scope==null || "".equals(scope)) throw new OAuthRequestParamsException("scope should not be empty");
				else if(response_type==null || "".equals(response_type)) throw new OAuthRequestParamsException("responseType should not be empty");
				else if(user_id==null || "".equals(user_id)) throw new OAuthRequestParamsException("user_id should not be empty");
				else if(username==null || "".equals(username)) throw new OAuthRequestParamsException("username should not be empty");
				else if(password==null || "".equals(password)) throw new OAuthRequestParamsException("password should not be empty");
				else if(state==null || "".equals(state)) throw new OAuthRequestParamsException("state should not be empty!");
				

				User user=userService.findByUsernameAndPassword(username, password);
				
				//authorizatiion-OK
				if(user!=null){
						//check if DB's user_id equal with form-submit! ++++
						//redirectUri
						
						String authzCode=oauthService.addAuthzCode(client_id, user_id, scope);
						Registration regClient=regService.findByClientId(client_id);
						String  receiveAuthzCodeUri=regClient.getReceive_authz_code_uri();
						HttpResponse authzResp=sendAuthorizationCodeToClient(receiveAuthzCodeUri, authzCode, state);
						
						
						//send authorization-code OK
						//SC_OK indent Authzrizatio_code is sending to client_server successfully ! 
						// Get client_secrect and others params, if valid , then send token to client
						if(authzResp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
							
								logger.info("send authorization-code successfully!");
							
								String str=EntityUtils.toString(authzResp.getEntity(), "UTF-8");
								logger.info("### str:"+str);
								

								JSONObject obj=new JSONObject(str);
									String code=obj.getString(OAuth.OAUTH_CODE);
									String client_secrect=obj.getString(OAuth.OAUTH_CLIENT_SECRET);
								
								logger.info("#code:"+code);
								logger.info("#client_secrect:"+client_secrect);
	

								//String code=authzResp.getFirstHeader("code").getValue();
								//String client_secrect=authzResp.getFirstHeader("client_secrect").getValue();
								
								String tokenRedirectUri=regClient.getReceive_token_uri();
								//apply for token passed
								//check usre's clinet_secrect and authorization_code
								logger.info("server@authzCode:"+authzCode+" client_serect:"+regClient.getClient_secrect());
								logger.info("client@code:"+code);
								logger.info("client@client_secrect:"+client_secrect);
								if(authzCode.equals(code) && regClient.getClient_secrect().equals(client_secrect)){
									
									//send token to client-server
									//if pass
									// send token to client-server
									String access_token="fakeToken";
									String refresh_token="fakeRefreshToken";
									
									logger.info("@receiveTokenUri:"+tokenRedirectUri);
									
									HttpResponse tokenResp=sendTokenToClient(tokenRedirectUri, access_token, OAuth.OAUTH_BEARER_TOKEN, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRE_IN, refresh_token);
									//send Token OK
									if(tokenResp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
										//client_id
										/*
										 * 	HTTP/1.1 302 Found
										 * 	Location:https://example.com/callback?
										 * 		code=...&state (put code and state in headers or body is both OK!)
										 */
										
										//is this redirect_uri OK? 
										//In my opinion, there should be three redirect_uri, the basic for view redirect, and
										// one for authorization response, one for token response,may be can add four for refresh response!
										//mav.setView(new RedirectView(redirect_uri, false));
										//send view to view redirectUri
										logger.log(Level.INFO, "send Token successful! client_id:"+client_id+" user_id:"+user_id+" -code:"+authzCode);
										mav.setStatus(HttpStatus.OK);
										mav.setViewName("some_redirect_view");
									}else {
										
										// what about time out?
										 int status=tokenResp.getStatusLine().getStatusCode();
										 String rp=tokenResp.getStatusLine().getReasonPhrase();	
										 
										 throw new OAuthClientHttpResponseException("process=4,status="+status+",msg="+rp);
										// throw some Exception, conains client-server repsonse
										 //redirect to AuthorizationView
									}
								}else{
									//not pass,throw some error
									String msg="authzCode or client_secrect validate not pass! authzCode="+authzCode+"&client_secrect="+client_secrect;
									throw new OAuthAuthzException("process=3,msg="+msg);
								}

						}else{
							int respCode=authzResp.getStatusLine().getStatusCode();
							String respMsg=authzResp.getStatusLine().getReasonPhrase();
							logger.log(Level.WARN, "send AuthzCode fail: reason- code:"+respCode+" msg:"+respMsg);
							
							throw new OAuthClientHttpResponseException("status="+respCode+"process=2,msg="+respMsg);
						}
						
				}else{
					//Authorization not pass
					//throw Customer Exception
					String msg="user is not exists?!";
					throw new OAuthAuthzException("Erro,process=1, msg="+msg);
				}

		} catch (OAuthSystemException 
				| OAuthProblemException|IOException
				| URISyntaxException
				| OAuthRequestParamsException
				| OAuthClientHttpResponseException
				| OAuthAuthzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 logger.log(Level.ERROR, "In AuthzProcess:"+e.getClass().getName());
			 mav.setStatus(HttpStatus.BAD_REQUEST);
			 mav.addObject("Error", e.getMessage());

		}
		return mav; 


	}
	
	
	/**
	 *  use authorization-code change for access token.
	 * @param params
	 * @param req
	 * @return
	 */
//	@RequestMapping(path="/token")
//	@Produces("application/json")
//	@Consumes("application/x-www-form-urlencoded")
//	public ResponseEntity<Map<String, String>> token(HttpServletRequest req){
//		
//		OAuthTokenRequest request;
//
//				try {
//					request = new OAuthTokenRequest(req);
//					if(request.getGrantType().equalsIgnoreCase("AUTHORIZATION_CODE")){
//						
//						String client_id=request.getClientId();
//						String user_id=request.getParam("user_id");
//						Set<String> scopes=request.getScopes();
//						
//						if(oauthService.isAuthzCodeExist(request.getCode())){
//							
//								String scope=OAuthUtils.convertScopesToScopeStr(scopes);
//								String token=oauthService.addAccessToken(client_id, user_id, scope);	
//							
//								Map<String, String> result=new HashMap<>();
//									result.put(OAuth.OAUTH_ACCESS_TOKEN,token);
//									result.put(OAuth.OAUTH_EXPIRES_IN, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRE_IN);
//									result.put(OAuth.OAUTH_TOKEN_TYPE, OAuth.OAUTH_BEARER_TOKEN);
//									//result.put(OAuth.OAUTH_REFRESH_TOKEN, value)
//									result.put(OAuth.OAUTH_CLIENT_ID, request.getClientId());
//									result.put("user_id", request.getParam("user_id"));
//									
//									return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
//							}
//							
//							
//						}else{
//
//							
//						}
//				} catch (OAuthSystemException | OAuthProblemException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//
//
//		
//		
//		return null;
//	}
	
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
	
	
	public HttpResponse sendAuthorizationCodeToClient(String redirect_uri,String code,String state) throws ClientProtocolException, IOException, URISyntaxException{
		
			//clientManager should be registry as a bean.
			//PoolingHttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager();
			HttpClient client=HttpClients.createDefault();
			
			logger.log(Level.INFO, "redirect_uri:"+redirect_uri);
			//code
			//state
			URI uri=new URI(redirect_uri+"?code="+code+"&state="+state);

			HttpGet get=new HttpGet();
				get.setURI(uri);

			HttpResponse response=client.execute(get);
			
			return response;

	}
	
	
	public HttpResponse sendTokenToClient(String redirectUri,String access_token,String token_type,String expire_in,String refresh_token) throws ClientProtocolException, IOException{

				HttpClient client=HttpClients.createDefault();

				//add response params
				List<NameValuePair> params=new ArrayList<>();
					params.add(new BasicNameValuePair("acess_token", access_token));	//access_token
					params.add(new BasicNameValuePair("token_type", token_type));		//token_type
					params.add(new BasicNameValuePair("expire_in", expire_in));			//expire_in
					params.add(new BasicNameValuePair("refresh_token", refresh_token));	//refresh_token
	
					Map<String,String> paramsMap=new HashMap<>();
						paramsMap.put("acess_token", access_token);
						paramsMap.put("token_type", token_type);
						paramsMap.put("expire_in", expire_in);
						paramsMap.put("refresh_token", refresh_token);

					JSONObject obj=new JSONObject(paramsMap);
																							// other_params
					HttpPost post=new HttpPost(redirectUri);
							post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
							post.setEntity(new  StringEntity(obj.toString(), ContentType.APPLICATION_JSON));

					HttpResponse response=client.execute(post);
					
					return response;
	}
	
	
	
	
}
