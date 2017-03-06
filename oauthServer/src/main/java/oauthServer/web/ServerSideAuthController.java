package oauthServer.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
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
import static oauthServer.util.OAuthConstants.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxconn.model.FamilyAccount;
import com.foxconn.model.FamilyMembers;
import com.foxconn.service.AccountService;

import oauthServer.exception.OAuthAuthzException;
import oauthServer.exception.OAuthRefreshTokenException;
import oauthServer.exception.OAuthTokenException;
import oauthServer.model.Client;
import oauthServer.model.Service;
import oauthServer.model.User;
import oauthServer.service.ClientService;
import oauthServer.service.OAuthService;
import oauthServer.service.ServiceService;
import oauthServer.service.UserService;
import oauthServer.util.AuthorizeResponse;
import oauthServer.util.HttpClientUtil;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;
import oauthServer.util.ServiceType;
import oauthServer.util.TokenResponse;

@Controller
@RequestMapping(path="/ssa")
public class ServerSideAuthController {
	
	private static Logger logger=LogManager.getLogger();

	@Autowired
	private OAuthService oauthService;
	@Autowired
	private HttpClientUtil httpClientUtil;
	@Autowired
	private AccountService accService;
	@Autowired
	private ServiceService serService;
	@Autowired
	private ClientService cliService;
	
	public ServerSideAuthController() {
		// TODO Auto-generated constructor stub
	}


	@GET
	@RequestMapping(path="/authView")
	@Consumes("application/x-www-form-urlencoded")   
	public ModelAndView toAuthView(HttpServletRequest req,ModelAndView mav){
		try {
			
			    OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);

				mav.addObject(CLIENT_ID, authzReq.getClientId());
				mav.addObject(REDIRECT_URI, authzReq.getRedirectURI());
				mav.addObject(STATE, authzReq.getState());
				mav.addObject(RESPONSE_TYPE, CODE);

				Map<String, String> attrs=new HashMap<>();
					attrs.put(CLIENT_ID, authzReq.getClientId());
					attrs.put(REDIRECT_URI, authzReq.getRedirectURI());
					attrs.put(STATE, authzReq.getState());
					attrs.put(RESPONSE_TYPE, CODE);
					attrs.put(STATE, authzReq.getState());
				
				JstlView view=new JstlView(OAuthConstants.AUTHORIZATION_PAGE);

					view.setAttributesMap(attrs);
				mav.setView(view);

				return mav;
				
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			mav.addObject(ERROR_DESCRIPTION, e.getMessage());
			mav.setStatus(HttpStatus.BAD_REQUEST);
			
			mav.setView(new JstlView("/authorize.jsp"));
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
	
	@RequestMapping(path="/authorize",method={RequestMethod.POST})
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
		
				String user_id=request.getParam(USER_ID);
				String password=request.getParam(PASSWORD);
				String service_id=request.getParam(SERVICE_ID);
				
				Client client=cliService.findByClient_id(client_id);
					if(client==null) throw new OAuthAuthzException(ERROR_CLIENT_NOT_EXISTS);
				Service service=serService.findByService_id(service_id);
					if(service==null) throw new OAuthAuthzException(ERROR_SERVICE_NOT_EXISTS);
					
				int service_id_int=service.getId();
				int client_id_int=client.getId();
				int user_id_int=Integer.parseInt(user_id);
				
				boolean authorize=true;
				
				if(ServiceType.TF02.equals(service_id)){
					String tel=request.getParam(TEL);
					FamilyAccount fa=accService.getFamilyAccountByTelAndPassword(tel, password);
					List<FamilyMembers> fmList=accService.findMembersInfoByFamilyId(fa.getId());
					for(FamilyMembers fm:fmList){
						if(Integer.parseInt(fm.getMemberId())==user_id_int){
							authorize=true;
							break;
						}
					}
					
				}
				
				if(authorize){
					String auth_code=oauthService.addAuthorizationCode(service_id_int, client_id_int, user_id_int);
					
					CloseableHttpResponse clientResp=sendAuthzCodeToClient(auth_code, client.getReceive_authcode_uri(), state);
					
					if(clientResp.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
						
						mav.setStatus(HttpStatus.OK);
						mav.setViewName("redirect:"+redirect_uri);
						
						return mav;
					}else
						throw new OAuthAuthzException(ERROR_SEND_AUTHCODE_FAIL+":"+clientResp.getStatusLine().getReasonPhrase());
					
				}else{
					throw new OAuthAuthzException(ERROR_AUTHORIZE_FAIL);
				}


		} catch (OAuthSystemException | OAuthAuthzException | IOException | URISyntaxException | OAuthProblemException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block

				mav.addObject(ERROR, e.getClass().getSimpleName());
				mav.addObject(ERROR_DESCRIPTION, e.getMessage());
				mav.setStatus(HttpStatus.BAD_REQUEST);

				JstlView view=new JstlView("/authorize.jsp");

				mav.setView(view);
	
				return mav; 

		}
		
	}
	
	
	/**
	 *  需呀返回auth_code,state
	 * @param auth_code
	 * @param receive_authcode_uri
	 * @param state
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse sendAuthzCodeToClient(String auth_code,String receive_authcode_uri,String state) throws URISyntaxException, ClientProtocolException, IOException{
		// add scope utils later!
		
		List<Header> http_headers=new ArrayList<>();

			Header header=new BasicHeader(STATE,state);
			http_headers.add(header);
			
		CloseableHttpClient client=HttpClients.custom()
										.setConnectionManager(httpClientUtil.getManager())
										.setDefaultHeaders(http_headers).build();
		
		HttpPost post=new HttpPost(new URI(receive_authcode_uri));
			
			post.setHeader("content-type", "application/json;charset=utf-8");
			
			Map<String, String> result=new HashMap<>();
				result.put(CODE, auth_code);
				result.put(STATE, state);

			
			JSONObject jsonObj=new JSONObject(result);
			
			post.setEntity(new StringEntity(jsonObj.toString(), Charset.forName("UTF-8")));
		
			return client.execute(post);

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
				String state=request.getParam(STATE);
				String code=request.getCode();
				String grant_type=request.getGrantType();
				String client_secrect=request.getClientSecret();
				Set<String> scopes=request.getScopes();
				String user_id=request.getParam(USER_ID);
				String service_id=request.getParam(SERVICE_ID);
				
				
				
				Client client=cliService.findByClient_id(client_id);
				Service service=serService.findByService_id(service_id);
				
				int service_id_int=service.getId();
				int client_id_int=client.getId();
				int user_id_int=Integer.parseInt(user_id);
				
	
				
				if(!grant_type.equalsIgnoreCase(GrantType.AUTHORIZATION_CODE.name()))
					throw new OAuthTokenException(ERROR_WRONG_GRANT_TYPE);
				if(!client.getClient_secrect().equals(client_secrect))		
					throw new OAuthTokenException(ERROR_CLIENT_AUTH_FAIL);
					
				//check code in redis
				String ac=oauthService.getAuthorizationCode(service_id_int, client_id_int, user_id_int);
				if(ac.equals(code)){
					String tkn=oauthService.addAccessToken_code(service_id_int, client_id_int, user_id_int);
					String rftkn=oauthService.addRefreshToken(service_id_int, client_id_int, user_id_int);
					
					Map<String, String> result=new HashMap<>();
						result.put(ACCESS_TOKEN, tkn);
						result.put(REFRESH_TOKEN, rftkn);
						result.put(STATE, state);
					return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
				}else{
					//may be expired
					throw new OAuthTokenException(ERROR_AUTHORIZE_FAIL);
				}


			} catch (OAuthSystemException | OAuthTokenException | OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				headers.add(ERROR, e.getClass().getSimpleName());
				headers.add(ERROR_DESCRIPTION, e.getMessage());
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
	public ResponseEntity<Map<String, String>> useRefreshTokenForNewTokens(HttpServletRequest req){
		OAuthTokenRequest request;
		HttpHeaders headers=new HttpHeaders();
		
			try {
				request = new OAuthTokenRequest(req);
				//search for refresh-token

				String state=request.getParam(STATE);
			
				String refreshToken=request.getRefreshToken();
				
				String service_id=request.getParam(SERVICE_ID);
				
					
					String client_id=request.getClientId();
					String user_id=request.getParam(USER_ID);
					
					Client client=cliService.findByClient_id(client_id);
					Service service=serService.findByService_id(service_id);
					
					int service_id_int=service.getId();
					int client_id_int=client.getId();
					int user_id_int=Integer.parseInt(user_id);
					
					String rftkn=oauthService.getRefreshToken(service_id_int, client_id_int, user_id_int);
					
					if(rftkn==null)
							throw new OAuthRefreshTokenException(ERROR_REFRESH_TOKEN_NOT_FOUND);
					if(!rftkn.equals(refreshToken))
							throw new OAuthRefreshTokenException(ERROR_AUTHORIZE_FAIL);
					
					String newTkn=oauthService.addAccessToken_code(service_id_int, client_id_int, user_id_int);
					String newRftkn=oauthService.addRefreshToken(service_id_int, service_id_int, user_id_int);
					
					
					Map<String, String> result=new HashMap<>();
						result.put(ACCESS_TOKEN, newTkn);
						result.put(REFRESH_TOKEN, newRftkn);
						result.put(STATE, state);
						return new ResponseEntity<Map<String,String>>(result,HttpStatus.OK);
				
			} catch (OAuthSystemException | OAuthRefreshTokenException | OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				headers.add(ERROR, e.getClass().getSimpleName());
				headers.add(ERROR_DESCRIPTION, e.getMessage());
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			} 
			
				


		
	}
	
	
}
