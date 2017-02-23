package oauthClient.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import static oauthClient.util.OAuthUtils.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import oauthClient.util.OAuthClientConstants;
import oauthClient.util.OAuthUtils;



@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
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
	
	
	@RequestMapping(path="/authzResult")
	public ResponseEntity changeToken(HttpServletRequest req){

		logger.log(Level.ERROR, "authZResult**8");
		
		OAuthAuthzResponse response;
		try {
			response = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
			
			//response.getExpiresIn()
			String authzCode=response.getCode();
			
			HttpClient httpClient=new URLConnectionClient();
			
			//needed parameters
			
			/*
			 * grant_type(required)
			 * code(required)
			 * redirect_uri(conditional_required)
			 * client_id(required)
			 */
			
			OAuthClientRequest clientRequest = OAuthClientRequest.tokenLocation(OAuthClientConstants.TOKEN_ENDPOINT)
								.setClientId(OAuthClientConstants.CLIENT_ID)
								.setCode(authzCode)
								.setGrantType(GrantType.AUTHORIZATION_CODE)
								.buildHeaderMessage();
				
				OAuthClient client=new OAuthClient(httpClient);
							
				OAuthJSONAccessTokenResponse tokenResp=client.accessToken(clientRequest, "POST");

				tokenResp.getOAuthToken();
				
				return new ResponseEntity(tokenResp, HttpStatus.OK);


		} catch (OAuthSystemException |OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
			
		}
			
		
		
		
	}
}
