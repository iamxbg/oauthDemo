package oauthServer.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;
import oauthServer.util.OAuthConstants;
import static oauthServer.util.OAuthConstants.*;

@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ServerSideAuthControllerTester {

	
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	
	private Logger logger=LogManager.getLogger();
	
	@Before
	public void setUp(){
		this.mvc=MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	public ServerSideAuthControllerTester() {
		// TODO Auto-generated constructor stub
	}
	
	//@Test
	public void testForAuthView() throws Exception{

		//redirect view has no model and view! is null!
		logger.info("GET:"+SSA_AUTHORIZTION_VIEW_URI);
		mvc.perform(get(SSA_AUTHORIZTION_VIEW_URI)
					).andExpect(status().isOk());
		
	}
	/**
	response_type : must be code
	 * 				client_id:  must exists
	 * 				redirect_uri: 	if all pass , redirect user to redirectView finally.
	 * 				scope: 	(optional)
	 * 				state: (recommended)  
	 * 
	 * 				custom-defined:(user_id,username,password)
	 * @throws Exception 
	 * */
	@Test
	public void testAuthorize() throws Exception{
		logger.info("POST:"+SSA_AUTHORIZE_URI);
		
		String client_id="test_client_id";
		String redirect_uri="test_redirect_uri";
		String scope="test_scope";
		String state="test_state";
		
		String user_id="test_user_id";
		String username="test_username";
		String password="test_password";
		
		mvc.perform(post(SSA_AUTHORIZE_URI)
				.param("client_id", client_id)
				.param("redirect_uri", redirect_uri)
				.param("scope", scope)
				.param("state", state)
				.param("user_id", user_id)
				.param("username", username)
				.param("password", password))
			.andExpect(redirectedUrl(redirect_uri));
	}
	
	


}
