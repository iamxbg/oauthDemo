package oauthClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TokenRequestTestor {

	private String user_id;
	private String client_id;
	private String client_secrect;
	private String authorizationCode;
	private String state;
	
	
	@Test	
	public void askForToken(){
		
	}
}
