package oauthServer.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import oauthServer.service.TokenValidationService;

@Controller
@RequestMapping(path="/validate_token")
public class TokenValidationController {

	@Autowired
	private TokenValidationService tvService;
	
	public TokenValidationController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(path="/access_token={access_token}")
	public ResponseEntity<Map<String, Object>> validateToken(@PathVariable("access_token") String token
															,HttpServletRequest req){
		
		
		return new ResponseEntity<Map<String,Object>>(tvService.validateAccessToken(token),HttpStatus.OK);
		
	}
}
