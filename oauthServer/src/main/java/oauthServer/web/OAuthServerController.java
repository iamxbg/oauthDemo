package oauthServer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import oauthServer.exception.OAuthAuthzException;
import oauthServer.exception.OAuthClientHttpResponseException;
import oauthServer.exception.OAuthRequestParamsException;
import oauthServer.model.Registration;
import oauthServer.model.User;
import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;
import oauthServer.redis.RefreshToken;
import oauthServer.service.OAuthService;
import oauthServer.service.RegistrationService;
import oauthServer.service.UserService;
import oauthServer.util.AuthorizeResponse;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;
import oauthServer.util.TokenResponse;

@Controller
@RequestMapping(path="/osflow")
public class OAuthServerController {

	
	private static Logger logger=LogManager.getLogger(OAuthServerController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService regService;
	@Autowired
	private OAuthService oauthService;
	
	//unfinished:	
	//				send error message to user's server!
	// 				authorization View should be extract from code!
	
	/**
	 *  Pack authorization Parameters in request and redirect user to authorizationView
	 * @param req
	 * @param mav
	 * @param session
	 * @return
	 */
	@RequestMapping(path="/authorizeView")
	public ModelAndView authorizeView(HttpServletRequest req,ModelAndView mav,HttpSession session){

		
		try {
			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
				
				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);

				return mav;
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());

			
		} 

		mav.setViewName("/authorization.jsp");
		return mav;
	

	}
	
	//unfinished:
	//			when authorize, should check with user_id? How get user_id,form openId shared?
	//			authorization token and refresh token is fake yet, implements it!
	
	/**
	 *  authorization user's authorize request,
	 *  	1.authorization user's certification
	 *  			if pass,send authzCode to user
	 *  			else, send error message
	 *  	2.client use authzCode with client-secrect to change for token
	 *  			if pass , send token back to client-server
	 *  			else , send error message
	 *  	3.if client-server receive token successfully
	 *  			then authorization-server redirect user to predefined redirectView!
	 *  	
	 *  	4.if token is not found , may be expired,
	 *  		then send error message to client-server
	 *  				it can
	 *  						use refresh token for ask for a new token
	 *  						or <re-authorize again  > --not recommanded! 
	 *  
	 *  	request: 						
	 * 			parameters
	 * 				response_type : must be code
	 * 				client_id:  must exists
	 * 				redirect_uri: 	if all pass , redirect user to redirectView finally.
	 * 				scope: 	(optional)
	 * 				state: (recommended)  
	 * 
	 * 				custom-defined:(user_id,username,password)
	 * 					
	 * 		response:
	 * 			success:
	 *				redirect to redirect_uri in request
	 *  		error:
	 *  			redirect to authorization view with errors
	 * @param req
	 * @param mav
	 * @return
	 */
	@RequestMapping(path="/authorize")
	@Consumes("application/x-www-form-urlencoded")   
	@Produces("application/x-www-form-urlencoded")
	public ModelAndView authorize(HttpServletRequest req,ModelAndView mav){

		try {
			OAuthAuthzRequest request=new OAuthAuthzRequest(req);
				


				String response_type=request.getResponseType();
				String client_id=request.getClientId();
				Set<String> scopes=request.getScopes();
						String scope=OAuthUtils.convertScopesToScopeStr(scopes);
				String state=request.getState();
				String redirect_uri=request.getRedirectURI();
				
				
				String user_id=request.getParam("user_id");
				String username=request.getParam("username");
				String password=request.getParam("password");

				if(client_id==null || "".equals(client_id)) 
					throw new OAuthRequestParamsException("client_id should not be empty!");
				else if(redirect_uri==null || "".equals(redirect_uri)) 
					throw new OAuthRequestParamsException("redirect_uri should not be empty");
//				else if(scope==null || "".equals(scope)) 
//					throw new OAuthRequestParamsException("scope should not be empty");
				else if(response_type==null || "".equals(response_type)) 
					throw new OAuthRequestParamsException("responseType should not be empty");
				else if(user_id==null || "".equals(user_id)) 
					throw new OAuthRequestParamsException("user_id should not be empty");
				else if(username==null || "".equals(username)) 
					throw new OAuthRequestParamsException("username should not be empty");
				else if(password==null || "".equals(password)) 
					throw new OAuthRequestParamsException("password should not be empty");
				else if(state==null || "".equals(state)) 
					throw new OAuthRequestParamsException("state should not be empty!");
				
				Registration regClient=regService.findByClientId(client_id);
					if(regClient==null) throw new OAuthAuthzException("may application not registred yet ?");
					
					String tokenRedirectUri=regClient.getReceive_token_uri();
					String  receiveAuthzCodeUri=regClient.getReceive_authz_code_uri();
					
				User user=userService.findByUsernameAndPassword(username, password);	
				//authorize OK
				if(user!=null){
						
						String authzCode=OAuthUtils.generateAuthorizationCode();
						AuthorizationCode code=new AuthorizationCode(client_id, user_id, scopes, OAuthConstants.AUTHORIZATION_CODE_EXPIRES_IN, authzCode);
					
						
						HttpResponse authzResp=sendAuthorizationCodeToClient(receiveAuthzCodeUri, new AuthorizeResponse(authzCode, state));
						
						
						//send authorization-code OK
						if(authzResp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
								logger.info("authServer@success#  send authorization code ");
								
								String str=EntityUtils.toString(authzResp.getEntity(), "UTF-8");
								

								JSONObject obj=new JSONObject(str);
									String clientCode=obj.getString(OAuth.OAUTH_CODE);
									String client_secrect=obj.getString(OAuth.OAUTH_CLIENT_SECRET);
								
								logger.info("authServer@received# code "+clientCode+" client_secrect"+client_secrect);
								
								

								//check code success
								if(authzCode.equals(code) && regClient.getClient_secrect().equals(client_secrect)){
									logger.info("authServer@success# check code and client-secrect");
	
									String access_token="fakeToken";
									String refresh_token="fakeRefreshToken";
									
									HttpResponse tokenResp=sendTokenToClient(tokenRedirectUri, new  TokenResponse(access_token,
																												TokenType.BEARER.name(),
																												OAuthConstants.ACCESS_TOKEN_CODE_EXPIRES_IN,
																												refresh_token, scope));
									
									//send token success
									if(tokenResp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
										logger.log(Level.INFO, "authServer@success# - send token to user");
										/*
										 * 	HTTP/1.1 302 Found
										 * 	Location:https://example.com/callback?&state	
										 */										
										mav.setStatus(HttpStatus.OK);
										mav.addObject("state", state);
										mav.setView(new RedirectView(redirect_uri,false));
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
					//unauthorized_client
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
	 *  response:
			success:
				parameters:
		 *  		code(Required)
		 *  			 in specification , it should has a short life time, there I don't save it
		 *  				just make it exists in session, no problem ???
		 *  		state
		 *  				if request has one, then return it in response!
	 *  	
			 *  	e.g.:
			 *  			HTTP/1.1 302 Found
								Location: https://example.com/callback?
								code=SplxlOBeZQQYbYS6WxSbIA&
								state=xyz
							
			error:
				parameters:
					error(required)
						invalid_request
						unauthorized_client
						access_denied
						unsupported_response_type
						invalid_scope
						server_error
						temporarily_unaviliable
				error_description(optional)
				error_uri(optional)
				state(return if contains in request)
				e.g.:
					HTTP/1.1 400 Bad request
					Location: https://example.com/callback?
					error=access_denied&
					state=xyz
					
	 */
	protected HttpResponse sendAuthorizationCodeToClient(String codeReceiveUri,AuthorizeResponse resp) throws ClientProtocolException, IOException, URISyntaxException{
		
			//clientManager should be registry as a bean.
			//PoolingHttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager();
			HttpClient client=HttpClients.createDefault();

			//code
			//state
			URI uri=new URI(codeReceiveUri+"?code="+resp.getCode()+"&state="+resp.getCode());

			HttpGet get=new HttpGet();
				get.setURI(uri);

			HttpResponse response=client.execute(get);
			
			return response;

	}
	
	/**
	 *  response:
	 *  	success:
	 *  		access-token(required)	
	 *  		token_type(required)
	 *  		expires_in(optional)
	 *  		refresh_token(optional)
	 *  		scope( if request has , contains it in response)
	 *  
	 *  		e.g.:
	 *  			HTTP/1.1 200 OK
					Content-Type: application/json;charset=UTF-8
					Cache-Control: no-store
					Pragma: no-cache
					{
					"access_token":"2YotnFZFEjr1zCsicMWpAA",
					"token_type":"bearer",
					"expires_in":3600,
					"refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
					"example_parameter":"example_value"
					}
			error:
				error:(required)
					invalid_request
					invalid_client
					invalid_grant_type
					unauthorized_client
					unsupported_client
					invalid_scope
					
				error_description(optional)
				error_uri(optinal)
				
				e.g.:
					HTTP/1.1 400 Bad Request
					Content-Type: application/json;charset=UTF-8
					Cache-Control: no-store
					Pragma: no-cache
					{
					"error":"invalid_request"
					}
	 *  		
	 * @param redirectUri
	 * @param access_token
	 * @param token_type
	 * @param expire_in
	 * @param refresh_token
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected HttpResponse sendTokenToClient(String receiveTokenUri,TokenResponse resp) throws ClientProtocolException, IOException{

				
				HttpClient client=HttpClients.createDefault();

				//add response params
					
					Map<String,String> paramsMap=new HashMap<>();
						paramsMap.put("acess_token", resp.getAccess_token());
						paramsMap.put("token_type", resp.getToken_type());
						paramsMap.put("expire_in", resp.getExpires_in());
						paramsMap.put("refresh_token", resp.getRefresh_token());

					JSONObject obj=new JSONObject(paramsMap);
																							// other_params
					HttpPost post=new HttpPost(receiveTokenUri);
							//post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
							post.setEntity(new  StringEntity(obj.toString(), ContentType.APPLICATION_JSON));

					HttpResponse response=client.execute(post);
					
					return response;
	}
	
	
	/**
	 * 
	 *  reqeust:
	 * 		 	parameters:
	 * 				grant_type (required) : refresh_token
	 * 				refresh_token(optional)
	 * 				scope(optional)
	 * 			e.g.:
		 *  		POST /token HTTP/1.1
							Host: server.example.com
							Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
							Content-Type: application/x-www-form-urlencoded
							grant_type=refresh_token&
							refresh_token=tGzv3JOkF0XG5Qx2TlKWIA
							
		success response:
			parameters:
				access_token (required)
				token_type	(required)
				expires_in (optional)
				fresh_token (optional)
				scope	(if contains , return it)
			e.g.:
					HTTP/1.1 200 OK
						Content-Type: application/json;charset=UTF-8
						Cache-Control: no-store
						Pragma: no-cache
						{
						"access_token":"2YotnFZFEjr1zCsicMWpAA",
						"token_type":"bearer",
						"expires_in":3600,
						"refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
						"example_parameter":"example_value"
						}
		error response:
				parameters:
					error (required)
						invalid_request
						invalid_client
						invalid_grant
						unauthorized_client
						unsupported_grant_type
						invalid_scope
						
					error_descriptioni(optional)
					error_uri(optional)
				e.g.:
					HTTP/1.1 400 Bad Request
					Content-Type: application/json;charset=UTF-8
					Cache-Control: no-store
					Pragma: no-cache
					{
					"error":"invalid_request"
					}	
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
			
				//search for refresh-token

				RefreshToken refreshToken=oauthService.getRefreshToken(request.getCode());
					
					String client_id=refreshToken.getClient_id();
					String expires_in=refreshToken.getExpire_in();
					String user_id=refreshToken.getUser_id();
					Set<String> scopes=refreshToken.getScopes();
					
					
				if(request.getGrantType().equals(OAuth.OAUTH_REFRESH_TOKEN) && refreshToken!=null){
					//create new token
					String tokenKey=OAuthUtils.generateToken();
					AccessToken token=new AccessToken(client_id, user_id, scopes, expires_in, tokenKey);
					oauthService.addAccessToken(token);
					//create new refreshToken
					String newRefreshTokenKey=OAuthUtils.generateRefreshToken();
					RefreshToken newRefreshToken=new RefreshToken(client_id, user_id, OAuthConstants.REFRESH_TOKEN_EXPIRES_IN, newRefreshTokenKey, scopes);
					oauthService.addRefreshToken(newRefreshToken);
					//send token and refresh token back to client
					
					Map<String, String> map=new HashMap<>();
						map.put(OAuth.OAUTH_ACCESS_TOKEN, tokenKey);	//access_token
						map.put(OAuth.OAUTH_TOKEN_TYPE, TokenType.BEARER.name());	//token_type
						map.put(OAuth.OAUTH_EXPIRES_IN, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRES_IN);	//expires_in
						map.put(OAuth.OAUTH_REFRESH_TOKEN, newRefreshTokenKey);		//refresh_token
												//scope ?
						
					return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
						
					
				}else{
					//may refresh_token is expires too?!
					Map<String, String> map=new HashMap<>();
						map.put("error","not found fresht_token");
					return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
				}


		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, String> map=new HashMap<>();
				map.put("error", e.getMessage());
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}
