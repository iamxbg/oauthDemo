package oauthServer.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import oauthServer.model.User;
import oauthServer.service.UserService;
import oauthServer.util.OAuthConstants;

@Controller
@RequestMapping("/csa")
public class ClientSideAuthController {
	
	private Logger logger=LogManager.getLogger(ClientSideAuthController.class);

	@Autowired
	private UserService userService;
	
	public ClientSideAuthController() {
		// TODO Auto-generated constructor stub
	
	}
	
	//toAuthView
	/**
	 *  request parameters:
	 *  	response_type: token
	 *  	client_id(required)
	 *  	redirect_uri(optional)
	 *  	scope(optional)
	 *  	state(recommanded)
	 *  	
	 *  	e.g.:
	 *  		GET /authorize?
				response_type=token&
				client_id=s6BhdRkqt3&
				state=xyz&
				redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
				Host: server.example.com
	 * @param req
	 * @param mav
	 * @return
	 */
	@GET
	@RequestMapping(path="/authView")
	public ModelAndView toAuthView(HttpServletRequest req,ModelAndView mav){
		
		try {
			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
				
			
				mav.addObject(OAuth.OAUTH_CLIENT_ID, authzReq.getClientId());
				mav.addObject(OAuth.OAUTH_REDIRECT_URI, authzReq.getRedirectURI());
				mav.addObject(OAuth.OAUTH_STATE, authzReq.getState());
				mav.addObject(OAuth.OAUTH_RESPONSE_TYPE, OAuth.OAUTH_CODE);
				mav.setView(new RedirectView(OAuthConstants.AUTHORIZATION_PAGE));
				
				return mav;
		} catch (OAuthSystemException|OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.ERROR, e.getMessage());
			mav.addObject("error", e.getClass().getSimpleName());
			mav.addObject("error-description", e.getMessage());
			mav.setView(new RedirectView(OAuthConstants.AUTHORIZATION_PAGE));
			return mav;
		} 
		
	}
	
	
	//do auth work,return token
	/**
	 *  request parameters:
	 *  	response_type: token
	 *  	client_id(required)
	 *  	redirect_uri(optional)
	 *  	scope(optional)
	 *  	state(recommanded)
	 *  	
	 *  	e.g.:
	 *  		GET /authorize?
				response_type=token&
				client_id=s6BhdRkqt3&
				state=xyz&
				redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
				Host: server.example.com
				
		response:
				success:
					HTTP/1.1 302 Found
						Location: http://example.com/callback#
						access_token=2YotnFZFEjr1zCsicMWpAA&
						state=xyz&
						token_type=bearer&
						expires_in=3600
				fail:
					error
					error_description(optional)
					error_uri(optional)
	*/
	

	@Consumes("application/x-www-form-urlencoded")
	@RequestMapping(path="/authorize")
	public ModelAndView authorize(HttpServletRequest req,ModelAndView mav){
		
		OAuthAuthzRequest request;
		try {
			request = new OAuthAuthzRequest(req);

			String redirect_uri=request.getRedirectURI();

			String state=request.getState();
			
			String username=request.getParam("username");
			String password=request.getParam("password");
			
			User u=userService.findByUsernameAndPassword(username, password);
			
			//if validate pass, redirect user view with token
			if(u!=null){
				String access_token="FAKE_ACCESS_TOKEN";
				
				mav.addObject(OAuth.OAUTH_ACCESS_TOKEN, access_token);
				mav.addObject(OAuth.OAUTH_TOKEN_TYPE, TokenType.BEARER.name());
				mav.addObject(OAuth.OAUTH_EXPIRES_IN, OAuthConstants.ACCESS_TOKEN_TOKEN_EXPIRES_IN);
				mav.addObject(OAuth.OAUTH_STATE, state);
				mav.setView(new RedirectView(redirect_uri, false));
				
				return mav;
			}else{
				logger.error("user authorization not pass");
				mav.addObject("error","user authorization not passed!");
				mav.setView(new RedirectView(OAuthConstants.AUTHORIZATION_PAGE, false));
				return mav;
			}
			
			
		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			mav.addObject("error",e.getClass().getSimpleName());
			mav.addObject("error-description", e.getMessage());
			mav.setView(new RedirectView(OAuthConstants.AUTHORIZATION_PAGE, false));
			return mav;
		} 
			
			
		
	}
	

}
