package oauthServer.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.MockMvc.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.RequestResultMatchers.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.oltu.oauth2.common.OAuth;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;
import oauthServer.util.OAuthConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@WebAppConfiguration
public class AuthzRequestTester {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	
	@Before
	public void setUp(){
		this.mvc=MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	
	@Test
	public void testAuthorizationRequestWithUsernameAndPassword() throws Exception{
		
		String uri=OAuthConstants.AUTHORIZTION_URI;
		
		String client_id="";
		String response_type="";
		String redirect_uri="";
		String scope="";
		String state="";
		
		String username="wilson";
		String password="test123";
		
		String err_username="someDude";
		String err_password="lastavista";
		
		HttpHeaders headers=new HttpHeaders();
			headers.set(OAuth.OAUTH_CLIENT_ID, client_id);
			headers.set(OAuth.OAUTH_RESPONSE_TYPE, response_type);
			headers.set(OAuth.OAUTH_REDIRECT_URI, redirect_uri);
			headers.set(OAuth.OAUTH_SCOPE, scope);
			headers.set(OAuth.OAUTH_STATE, state);
		
			
		this.mvc.perform(
				get(uri).headers(headers)
				.content("application/x-www-form-urlencoded")
				).andExpect(status().is(HttpServletResponse.SC_UNAUTHORIZED)); //401
		
		this.mvc.perform(
				get(uri).headers(headers)
				.param("username", username)
				.param("password", password)
				.characterEncoding("utf-8")
				.contextPath("application/x-www-form-urlencoded")
				.characterEncoding("utf-8")
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json/charset=utf8"));
				
		
	}
	
}
