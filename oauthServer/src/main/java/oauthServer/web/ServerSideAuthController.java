package oauthServer.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthAuthorizationResponseBuilder;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import oauthServer.exception.OAuthAuthzException;
import oauthServer.exception.OAuthRefreshTokenException;
import oauthServer.exception.OAuthTokenException;
import oauthServer.model.Registration;
import oauthServer.model.User;
import oauthServer.model.tool.RefreshTokenRequestParams;
import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;
import oauthServer.redis.RefreshToken;
import oauthServer.redis.Token;
import oauthServer.service.OAuthService;
import oauthServer.service.RegistrationService;
import oauthServer.service.UserService;
import oauthServer.util.AuthorizeResponse;
import oauthServer.util.HttpClientUtil;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;
import oauthServer.util.TokenResponse;

@Controller
@RequestMapping(path="/ssa")
public class ServerSideAuthController {
	
	private static Logger logger=LogManager.getLogger();

	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService regService;
	@Autowired
	private OAuthService oauthService;
	@Autowired
	private HttpClientUtil httpClientUtil;
	
	public ServerSideAuthController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@RequestMapping(path="/authView")
	@Consumes("application/x-www-form-urlencoded")   
	public ModelAndView toAuthView(HttpServletRequest req,ModelAndView mav){
		try {
			
			    OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);

				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);

				mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				
				RedirectView view=new RedirectView(OAuthConstants.AUTHORIZATION_PAGE);
				
				Map<String, String> attrs=new HashMap<>();
					attrs.put(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
					attrs.put(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
					attrs.put(OAuth.OAUTH_STATE, authzReq.getState());
					attrs.put(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
					attrs.put("state", authzReq.getState());
					
					view.setAttributesMap(attrs);
				mav.setView(view);

				return mav;
				
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());
			
			RedirectView view=new RedirectView(OAuthConstants.AUTHORIZATION_PAGE);
				Map<String, String> attrs=new HashMap<>();
					attrs.put("error", e.getMessage());
				view.setAttributesMap(attrs);
			
			mav.setStatus(HttpStatus.BAD_REQUEST);
			
			mav.setView(view);
			return mav;

		} 

		
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
		OAuthAuthzRequest request=null;
		MultiValueMap<String, String> headers=new HttpHeaders();
		try {
				request=new OAuthAuthzRequest(req);	
				String response_type=request.getResponseType();
				String client_id=request.getClientId();
				Set<String> scopes=request.getScopes();
				String state=request.getState();
				String redirect_uri=request.getRedirectURI();
		
				String user_id=request.getParam("user_id");
				String username=request.getParam("username");
				String password=request.getParam("password");
				
				Registration reg_client=regService.findByClientId(client_id);
				
				String receive_authcode_uri=reg_client.getReceive_authz_code_uri();
				
				User user=userService.findByUsernameAndPassword(username, password);
				
				if(user!=null && user.getId()==Integer.parseInt(user_id)){
					
					// add scope utils later!
					
					String auth_code="FAKE_AUTH_CODE";
					
					AuthorizationCode code=new AuthorizationCode(client_id, user_id, scopes, OAuthConstants.AUTHORIZATION_CODE_EXPIRES_IN, auth_code);
					//save code to redis
					oauthService.addAuthorizationCode(code);
					
					List<Header> http_headers=new ArrayList<>();
		
						Header header=new BasicHeader("state",state);
						http_headers.add(header);
						
					CloseableHttpClient client=HttpClients.custom()
													.setConnectionManager(httpClientUtil.getManager())
													.setDefaultHeaders(http_headers).build();
					
					HttpPost post=new HttpPost(new URI(receive_authcode_uri));
		
						JSONObject jsonObj=new JSONObject(code);
						
						post.setEntity(new StringEntity(jsonObj.toString(), Charset.forName("UTF-8")));
					CloseableHttpResponse resp=client.execute(post);
					
					if(resp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
						
						mav.setStatus(HttpStatus.OK);
						mav.setViewName(redirect_uri);
						
						return mav;
					}else
						throw new OAuthAuthzException("send auth-code error, error-code:"+resp.getStatusLine().getStatusCode()+" error-description:"+resp.getStatusLine().getReasonPhrase());

				}else{
					throw new OAuthAuthzException("user authentication failed!");
				}

		} catch (OAuthSystemException | OAuthAuthzException | IOException | URISyntaxException | OAuthProblemException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				//some error occurs
				mav.addObject("error", e.getClass().getSimpleName());
				mav.setStatus(HttpStatus.BAD_REQUEST);
				mav.setViewName(OAuthConstants.AUTHORIZATION_PAGE);
				mav.addObject("error-description", e.getMessage());
				return mav; 

		}
		
	}

	/**
	 *  requestParameters:
	 *  		grant_type : authorization_code
	 *  		code (required)
	 *  		redirect_uri
	 *  		client_id
	 *  
	 *  		-- state
	 *  		-- client_secrect
	 * @param req
	 * @return
	 */
	@RequestMapping(path="/token")
	@Consumes("application/x-www-form-urlencoded")
	public ResponseEntity useAuthCodeForAccessToken(HttpServletRequest req){
		
			OAuthTokenRequest request=null;
			
			MultiValueMap<String, String> headers=new HttpHeaders();
			
			try {
				request = new OAuthTokenRequest(req);
				String client_id=request.getClientId();
				String state=request.getParam("state");
				String code=request.getCode();
				String grant_type=request.getGrantType();
				String client_secrect=request.getClientSecret();
				Set<String> scopes=request.getScopes();
				String user_id=request.getParam("user_id");
				
				
				
				Registration reg_client=regService.findByClientId(client_id);
				
				if(!grant_type.equalsIgnoreCase(GrantType.AUTHORIZATION_CODE.name()))
					throw new OAuthTokenException("grant type must be AUTHORIZATION_CODE ,incase-sensitive!");
				if(reg_client.getIs_server_auth_enabled()!='Y')
						//trhow exception...
					throw new OAuthTokenException("client-server is disabled!");
				if(!(reg_client!=null && client_id.equals(reg_client.getClient_id()) && client_secrect.equals(reg_client.getClient_secrect())) )
						//throw client validate exception, fata
					throw new OAuthTokenException("client-server validate fail!");
					
				//check code in redis
				AuthorizationCode authCode=oauthService.getAuthorizationCode(code);

				//check client_id ,user_id and scopes
				if(client_id.equals(authCode.getClient_id()) && user_id.equals(authCode.getUser_id())){
					Set<String> realScope=authCode.getScopes();
					for(String scope:scopes){
						if(!realScope.contains(scope)){
							throw new OAuthTokenException("scope not allowed:"+scope);
						}
					}
					
					String accessToken="FAKE_ACCESS_TOKEN";
					String refreshToken="FAKE_REFRESH_TOKEN";
					
					AccessToken aToken=new  AccessToken(client_id, user_id, scopes, OAuthConstants.ACCESS_TOKEN_CODE_EXPIRES_IN, accessToken);
					RefreshToken rToken=new RefreshToken(client_id, user_id, OAuthConstants.REFRESH_TOKEN_EXPIRES_IN, refreshToken, scopes);
					
					List<Token> tokens=new ArrayList<>();
						tokens.add(aToken);
						tokens.add(rToken);
						
						headers.add("state", state);
					return new ResponseEntity<List<Token>>(tokens, headers, HttpStatus.OK);
					
				}else{
					//throw user validate fail!
					throw new OAuthTokenException("user authotication fail!");
				}
			} catch (OAuthSystemException | OAuthTokenException | OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				headers.add("error", e.getClass().getSimpleName());
				headers.add("error-description", e.getMessage());
				return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
			} 

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
	@Consumes("application/json")
	public ResponseEntity<List<Token>> useRefreshTokenForNewTokens(HttpServletRequest req){
		OAuthTokenRequest request;
		HttpHeaders headers=new HttpHeaders();
		
			try {
				request = new OAuthTokenRequest(req);
				//search for refresh-token

				String state=request.getParam("state");
			
				RefreshToken refreshToken=oauthService.getRefreshToken(request.getCode());
					
					String client_id=refreshToken.getClient_id();
					String expires_in=refreshToken.getExpires_in();
					String user_id=refreshToken.getUser_id();
					Set<String> scopes=refreshToken.getScopes();
					
					
					
				if(request.getGrantType().equals(OAuth.OAUTH_REFRESH_TOKEN) && refreshToken!=null){
					//create new token
					String tokenKey=OAuthUtils.generateToken();
					AccessToken newToken=new AccessToken(client_id, user_id, scopes, expires_in, tokenKey);
					oauthService.addAccessToken(newToken);
					
					// delete or expires old refreshToken and access token if exists
					
					//create new refreshToken
					String newRefreshTokenKey=OAuthUtils.generateRefreshToken();
						RefreshToken newRefreshToken=new RefreshToken(client_id, user_id, OAuthConstants.REFRESH_TOKEN_EXPIRES_IN, newRefreshTokenKey, scopes);
						oauthService.addRefreshToken(newRefreshToken);
					//send token and refresh token back to client
					
					List<Token> tokens=new ArrayList<>();
						tokens.add(newToken);
						tokens.add(newRefreshToken);
						
						headers.add("state", state);
						
					return new ResponseEntity<List<Token>>(tokens, headers, HttpStatus.OK);

				}else throw new OAuthRefreshTokenException("refresh token not found!");
				
			} catch (OAuthSystemException | OAuthRefreshTokenException | OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				headers.add("error", e.getClass().getSimpleName());
				headers.add("error-descriptioin", e.getMessage());
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			} 
			
				


		
	}
	
	
}
