package oauthClient.web;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/oauth")
public class OAuthController {

	private static Logger logger=LogManager.getLogger(OAuthController.class);
	
	private static String TOKEN_ENDPOINT="http://localhost:8082/oauthServer/oauth/token";
	
	@RequestMapping(path="/tag")
	public String receiveTagAndRequireToken(@RequestParam("tag") String tag,HttpRequest req){
		
		logger.log(Level.INFO, "oauthClient@receiveTagAndRequireToken #tag:"+tag);
		
		
		
		
		return null;
		
	}
}
