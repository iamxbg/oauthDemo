package oauthClient.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.apache.http.client.utils.HttpClientUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.httpclient4.HttpClient4;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import static oauthClient.util.OAuthUtils.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oauthClient.util.OAuthClientConstants;
import oauthClient.util.OAuthUtils;



@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
	/**
	 *  authorization_request
	 *  	parameters should be  in request:
	 *  		response_type : <code for server-side>
	 *  		client_id(required) 
	 *  		redirect_uri(option) 
	 *  		scope:(optional)
	 *  		state:(recommended)
	 *  	e.g.:
	 *  			GET /authorize?
						response_type=code&
						client_id=s6BhdRkqt3&state=xyz&
						redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
						Host: server.example.com
	 *  				
	 *  	
	 * @param mav
	 * @param req
	 * @return
	 */
	@RequestMapping(path="/toAuthzView")
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseEntity<Map<String, String>> redirectToAuhtorization(ModelAndView mav,HttpServletRequest req){
		
		Map<String, String> params=new HashMap<>();
			params.put(OAuth.OAUTH_CLIENT_ID, OAuthClientConstants.CLIENT_ID);
			params.put(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
			params.put(OAuth.OAUTH_STATE, OAuthUtils.generateState());
			params.put(OAuth.OAUTH_SCOPE, OAuthUtils.getScopeString());
			params.put("authz_endpoint", OAuthClientConstants.AUTHZ_ENDPOINT);
		
		return new ResponseEntity<Map<String, String>>(params,HttpStatus.OK);

	}

	/**
	 *  requestParamters:
	 *  		code
	 *  		state 
	 * @param map
	 * @param req
	 * @return
	 */
	@POST
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf8")
	@RequestMapping(path="/receiveCode")
	public ResponseEntity receiveCode(@RequestBody Map<String, Object> map,HttpServletRequest req){
		
		logger.info("client@receiveCode map:"+map.toString());

			
			String code1=(String)map.get("code");
			String state=(String)map.get("state");
			
			logger.info("Logger! code:"+code1);
			ArrayList<String> scopes=(ArrayList<String>) map.get("scopes");
			logger.info("scope-0:"+scopes.get(0));

				logger.info("code:"+code1);
				logger.info("state:"+scopes);
				

				
				String client_id="chunyuyishen";
				String redirect_uri="https://localhost:8443/oauthServer/ssa/token";
				
				Map<String, String> requestParams=new HashMap<>();
					requestParams.put(OAuth.OAUTH_GRANT_TYPE, GrantType.AUTHORIZATION_CODE.name());
					requestParams.put(OAuth.OAUTH_CODE, code1);
					requestParams.put(OAuth.OAUTH_REDIRECT_URI, redirect_uri);
					requestParams.put(OAuth.OAUTH_CLIENT_ID, client_id);
					requestParams.put(OAuth.OAUTH_CLIENT_SECRET, "123456");
					
				return new ResponseEntity(requestParams, HttpStatus.OK);



	}
	
	
	@RequestMapping(path="/receiveToken")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	@POST
	public ResponseEntity<Map<String, String>> receiveToken(@RequestBody Map<String, String>  body){
		String access_token=body.get("access_token");
		String token_type=body.get("token_type");
		String expires_in=body.get("expires_in");
		String refresh_token=body.get("refresh_token");
		
		logger.info("access_token:"+access_token);
		logger.info("token_type:"+token_type);
		logger.info("expires_in:"+expires_in);
		logger.info("refresh_token:"+refresh_token);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GET
	@RequestMapping("/redirectView")
	public ModelAndView redirectUriHandler(ModelAndView mav){
		mav.setViewName("/redirectView.jsp");
		return mav;
	}
	
	

}
