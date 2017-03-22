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
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import oauthServer.model.Client;
import oauthServer.model.Service;
import oauthServer.model.User;
import oauthServer.service.ClientService;
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
	private HttpClientUtil httpClientUtil;
	
	public ServerSideAuthController() {
		// TODO Auto-generated constructor stub
	}


	/***
	 *  needed params:
	 *  	repsonse_type(required , must be code)
	 *  	client_id(required)
	 *  	redirect_uri(optional)
	 *  	scopes(Optional)
	 *  	state(recommended)
	 *  
	 *  GET /authorize?
			response_type=code&
			client_id=s6BhdRkqt3&state=xyz&
			redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
			Host: server.example.com
	 * @return
	 */
	
	//@Consumes("applicaiton/x-www-form-urlencoded")
	@RequestMapping(path="/authorizationView/response_type=code"
			+ "&client_id={client_id}"
			+"&service_id={service_id}"
			+"&user_id={user_id}"
			+ "&state={state}"
			+ "&redirect_uri={redirect_uri}",method=RequestMethod.GET)
	public ModelAndView authorizationRequest(
			@PathVariable("client_id") String client_id,
			@PathVariable("service_id") String service_id,
			@PathVariable("user_id") String user_id,
			@PathVariable("state") String state,
			@PathVariable("redirect_uri") String redirect_uri,
			ModelAndView mav){
		

			logger.info("authorizationView");
		
			mav.addObject("client_id", "someDude");
			mav.addObject("service_id", service_id);
			mav.addObject("user_id", user_id);
			mav.addObject("state", state);
			mav.addObject("redirect_uri", redirect_uri);
		mav.setView(new JstlView("/ssaAuthView.jsp"));
		
		
		return mav;
	}


	
	/**
	 * get params in form, with account and password
	 * 	
	 * @param req
	 * @param mav
	 * @return
	 */
	@RequestMapping(path="/authorize",method={RequestMethod.POST})
	@Consumes("application/x-www-form-urlencoded")   
	@Produces("application/x-www-form-urlencoded")
	public ModelAndView authorize(
			@RequestParam("client_id") String client_id,
			@RequestParam("service_id") String service_id,
			@RequestParam("user_id") String user_id,
			@RequestParam("state") String state,
			@RequestParam("redirect_uri") String redirect_uri,
			@RequestParam("account") String account,
			@RequestParam("password") String password
			,ModelAndView mav){
		
		logger.info("authorize");
		
		//check paramets
		
		//if success
		
		//redirect_uri="http://www.baidu.com";
		mav.setView(new RedirectView(redirect_uri, false));
		
		return mav;
		
	}
	
	@RequestMapping(path="/authenticationView/service_id={service_id}&client_id={client_id}&state={state}&redirect_uri={redirect_uri}" ,method=RequestMethod.GET)
	public ModelAndView authenticationView(@PathVariable("client_id") String client_id
			,@PathVariable("service_id") String service_id
			,@PathVariable("state") String state
			,@PathVariable("redirect_uri") String redirect_uri
			,ModelAndView mav){
		
		logger.info("@AuthenticationView@");
		
		mav.setView(new JstlView("/ssaAuthenticationView.jsp"));
		//mav.addObject("client_id", client_id);
		//mav.addObject("service_id", service_id);
		//mav.addObject("state", state);
		
		
		return mav;
	}
	
	@RequestMapping(path="/authenticate",method=RequestMethod.POST)
	public ModelAndView anthenticate(@RequestParam("account") String account,
								@RequestParam("password") String password,
								@RequestParam("service_id") String service_id,
								@RequestParam("client_id") String client_id,
								@RequestParam("state") String state,
								@RequestParam("redirect_uri") String redirect_uri,
								ModelAndView mav) throws URISyntaxException, JsonProcessingException{
		logger.info("@Authenticate");
		
		PoolingHttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager();
		
		HttpClient client=HttpClients.custom()
							.setConnectionManager(connManager).build();
		
		HttpPost post=new HttpPost(new URI("http://localhost:8081/TF02/oauth/authenticate"));
		
		Map<String, String> map=new HashMap<>();
			map.put("account", account);
			map.put("password", password);
		
		ObjectMapper mapper=new ObjectMapper();
			
		String value=mapper.writeValueAsString(map);
		
		HttpEntity entity=new StringEntity(value, Charset.forName("utf-8"));
			post.setEntity(entity);
		
		
			ResponseHandler<String> handler=new BasicResponseHandler();
			
			
			
		try {
			String respString=client.execute(post, handler);
			
			Map<String, String> resultMap=mapper.readValue(respString, HashMap.class);
			
			logger.info("result:"+resultMap.get("result"));
			logger.info("reason:"+resultMap.get("reason"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//boolean authenticatePass=true;
		
		boolean authenticatePass=true;
		
		if(authenticatePass){
			
			mav.setView(new RedirectView("http://www.processon.com", false));
		}else{
			mav.setView(new JstlView("/ssaAuthenticationView.jsp"));
			mav.addObject("client_id", client_id);
			mav.addObject("service_id",service_id);
			mav.addObject("redirect_uri", redirect_uri);
			mav.addObject("state", state);
			
		}
		
		return mav;
	}
	
	@RequestMapping(path="/token/grant_type=authorization_code"
			+ "&client_id={client_id}"
			+ "&redirect_uri={redirect_uri}"
			+" &code={code}")
	@Produces("application/json")
	public ResponseEntity token(@PathVariable("client_id") String client_id,
			@PathVariable("redirect_uri") String redirect_uri,
			@PathVariable("code") String code){
		
		logger.info("token");
		
		return null;
		
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
//	public CloseableHttpResponse sendAuthzCodeToClient(String auth_code,String receive_authcode_uri,String state) throws URISyntaxException, ClientProtocolException, IOException{
//		// add scope utils later!
//		
//		List<Header> http_headers=new ArrayList<>();
//
//			Header header=new BasicHeader(STATE,state);
//			http_headers.add(header);
//			
//		CloseableHttpClient client=HttpClients.custom()
//										.setConnectionManager(httpClientUtil.getManager())
//										.setDefaultHeaders(http_headers).build();
//		
//		HttpPost post=new HttpPost(new URI(receive_authcode_uri));
//			
//			post.setHeader("content-type", "application/json;charset=utf-8");
//			
//			Map<String, String> result=new HashMap<>();
//				result.put(CODE, auth_code);
//				result.put(STATE, state);
//
//			
//			JSONObject jsonObj=new JSONObject(result);
//			
//			post.setEntity(new StringEntity(jsonObj.toString(), Charset.forName("UTF-8")));
//		
//			return client.execute(post);
//
//	}

//	@RequestMapping(path="/token")
//	@Consumes("application/x-www-form-urlencoded")
//	public ResponseEntity useAuthCodeForAccessToken(HttpServletRequest req){
//		
//			OAuthTokenRequest request=null;
//			
//			MultiValueMap<String, String> headers=new HttpHeaders();
//			
//			try {
//				request = new OAuthTokenRequest(req);
//				String client_id=request.getClientId();
//				String state=request.getParam(STATE);
//				String code=request.getCode();
//				String grant_type=request.getGrantType();
//				String client_secrect=request.getClientSecret();
//				Set<String> scopes=request.getScopes();
//				String user_id=request.getParam(USER_ID);
//				String service_id=request.getParam(SERVICE_ID);
//				
//				
//				
//				Client client=cliService.findByClient_id(client_id);
//				Service service=serService.findByService_id(service_id);
//				
//				int service_id_int=service.getId();
//				int client_id_int=client.getId();
//				int user_id_int=Integer.parseInt(user_id);
//				
//	
//				
//				if(!grant_type.equalsIgnoreCase(GrantType.AUTHORIZATION_CODE.name()))
//					throw new OAuthTokenException(ERROR_WRONG_GRANT_TYPE);
//				if(!client.getClient_secrect().equals(client_secrect))		
//					throw new OAuthTokenException(ERROR_CLIENT_AUTH_FAIL);
//					
//				//check code in redis
//				String ac=oauthService.getAuthorizationCode(service_id_int, client_id_int, user_id_int);
//				if(ac.equals(code)){
//					String tkn=oauthService.addAccessToken_code(service_id_int, client_id_int, user_id_int);
//					String rftkn=oauthService.addRefreshToken(service_id_int, client_id_int, user_id_int);
//					
//					Map<String, String> result=new HashMap<>();
//						result.put(ACCESS_TOKEN, tkn);
//						result.put(REFRESH_TOKEN, rftkn);
//						result.put(STATE, state);
//					return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
//				}else{
//					//may be expired
//					throw new OAuthTokenException(ERROR_AUTHORIZE_FAIL);
//				}
//
//
//			} catch (OAuthSystemException | OAuthTokenException | OAuthProblemException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				headers.add(ERROR, e.getClass().getSimpleName());
//				headers.add(ERROR_DESCRIPTION, e.getMessage());
//				return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
//			} 
//
//	}
//
//	@RequestMapping(path="/refreshToken")
//	@Consumes("application/json")
//	public ResponseEntity<Map<String, String>> useRefreshTokenForNewTokens(HttpServletRequest req){
//		OAuthTokenRequest request;
//		HttpHeaders headers=new HttpHeaders();
//		
//			try {
//				request = new OAuthTokenRequest(req);
//				//search for refresh-token
//
//				String state=request.getParam(STATE);
//			
//				String refreshToken=request.getRefreshToken();
//				
//				String service_id=request.getParam(SERVICE_ID);
//				
//					
//					String client_id=request.getClientId();
//					String user_id=request.getParam(USER_ID);
//					
//					Client client=cliService.findByClient_id(client_id);
//					Service service=serService.findByService_id(service_id);
//					
//					int service_id_int=service.getId();
//					int client_id_int=client.getId();
//					int user_id_int=Integer.parseInt(user_id);
//					
//					String rftkn=oauthService.getRefreshToken(service_id_int, client_id_int, user_id_int);
//					
//					if(rftkn==null)
//							throw new OAuthRefreshTokenException(ERROR_REFRESH_TOKEN_NOT_FOUND);
//					if(!rftkn.equals(refreshToken))
//							throw new OAuthRefreshTokenException(ERROR_AUTHORIZE_FAIL);
//					
//					String newTkn=oauthService.addAccessToken_code(service_id_int, client_id_int, user_id_int);
//					String newRftkn=oauthService.addRefreshToken(service_id_int, service_id_int, user_id_int);
//					
//					
//					Map<String, String> result=new HashMap<>();
//						result.put(ACCESS_TOKEN, newTkn);
//						result.put(REFRESH_TOKEN, newRftkn);
//						result.put(STATE, state);
//						return new ResponseEntity<Map<String,String>>(result,HttpStatus.OK);
//				
//			} catch (OAuthSystemException | OAuthRefreshTokenException | OAuthProblemException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				headers.add(ERROR, e.getClass().getSimpleName());
//				headers.add(ERROR_DESCRIPTION, e.getMessage());
//				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
//			} 
//			
//				
//
//
//		
//	}
	
	
}
