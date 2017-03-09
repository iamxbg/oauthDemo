package oauthServer.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping(path="/example")
public class ExampleController {

	public ExampleController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(path="/some")
	public ResponseEntity<Map<String, String>> some(){
		Map<String, String> body=new HashMap<>();
			body.put("someOne", "passed!");
		return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
