package oauthClient.web;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(path="/user")
public class UserController {
	
	
	private static String AUTHORIZATION_ENDPOINT="http://localhost:8082/oauthServer/oauth/authorize";
	private static String REDIRECTION_ENDPOINT="http://localhost:8081/oauthClinet/user/Response";

	private static String CLIENT_ID="fake_client_id";
	private static String CLIENT_SECRECT="fake_client_secrect";
	
	
	private static Logger logger=LogManager.getLogger(UserController.class);
	
	/**
	 *  use for client-server ask for 
	 * @return
	 */
	@RequestMapping(path="/redirectToAuthorization")
	public String redirectToAuhtorization(@RequestParam("user_id") String user_id){
		
		logger.log(Level.INFO, "oauthClient@redirectToAuhorization @user_id:"+user_id);
		
		
		
		return REDIRECTION_ENDPOINT; 
	}
}
