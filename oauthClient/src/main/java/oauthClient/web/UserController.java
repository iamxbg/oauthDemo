package oauthClient.web;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oauthClient.util.OAuthClientConstants;

@RequestMapping(path="/user")
public class UserController {
	
	
	private static Logger logger=LogManager.getLogger(UserController.class);
	
	/**
	 *  use for client-server ask for 
	 * @return
	 */

}
