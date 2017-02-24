package oauthServer.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	

	/**
	 *  test pass
	 * @throws Exception
	 */
	@Test
	public void testAuthorizationRequestWithUsernameAndPassword_Success() throws Exception{
		
		 String uri=OAuthConstants.AUTHORIZTION_URI;
		 String client_id="chunyuyishen";
		 String response_type=OAuth.OAUTH_CODE;
		 String redirect_uri="http://localhost:8081/oauthClient/oauth/receiveAuthzCode";
		 String scope="faker";
		 String state="123";
		 String user_id="1";
		
		 String username="wilson";
		 String password="123456";

		
		
		HttpHeaders headers=new HttpHeaders();
			headers.set(OAuth.OAUTH_CLIENT_ID, client_id);
			headers.set(OAuth.OAUTH_RESPONSE_TYPE, response_type);
			headers.set(OAuth.OAUTH_REDIRECT_URI, redirect_uri);
			headers.set(OAuth.OAUTH_SCOPE, scope);
			headers.set(OAuth.OAUTH_STATE, state);

		this.mvc.perform(
				get(uri).headers(headers)
					.characterEncoding("utf-8")
					.contentType("application/x-www-form-urlencoded")
					.param("user_id", user_id)
					.param("username", username)
					.param("password", password)
				)
				.andExpect(status().isOk());
				//.andExpect(content().contentType("application/x-www-form-urlencoded;charset=utf-8"));

	}
	
	/**
	 * test fail wrong username and password
	 * @throws Exception
	 */
	
	@Test
	public void testAuthorizationRequestWithUsernameAndPassword_Fail() throws Exception{
		
		 String uri=OAuthConstants.AUTHORIZTION_URI;
		 String client_id="chunyuyishen";
		 String response_type=OAuth.OAUTH_CODE;
		 String redirect_uri="http://localhost:8081/oauthClient/fake";
		 String scope="faker";
		 String state="123";
		 String user_id="1";
		
		 String username="wilson_dude";
		 String password="123456";

		
		
		HttpHeaders headers=new HttpHeaders();
			headers.set(OAuth.OAUTH_CLIENT_ID, client_id);
			headers.set(OAuth.OAUTH_RESPONSE_TYPE, response_type);
			headers.set(OAuth.OAUTH_REDIRECT_URI, redirect_uri);
			headers.set(OAuth.OAUTH_SCOPE, scope);
			headers.set(OAuth.OAUTH_STATE, state);

		this.mvc.perform(
				get(uri).headers(headers)
					.characterEncoding("utf-8")
					.contentType("application/x-www-form-urlencoded")
					.param("user_id", user_id)
					.param("username", username)
					.param("password", password)
				)
				.andExpect(status().isOk());
		
	}
	
}
