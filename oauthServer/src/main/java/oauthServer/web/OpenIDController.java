package oauthServer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/openid")
public class OpenIDController {

	public OpenIDController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(path="/me")
	public void getOpenId(HttpServletRequest req){
		
	}
	
}
