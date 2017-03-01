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
	
	@Test
	public void testForAuthView() throws Exception{

		//redirect view has no model and view! is null!
		logger.info("GET:"+SSA_AUTHORIZTION_VIEW_URI);
		mvc.perform(get(SSA_AUTHORIZTION_VIEW_URI)
					).andExpect(status().isOk());
		
	}
	
//	@Test
//	public void testAuthorize(){
//		logger.info("POST:"+SSA_AUTHORIZE_URI);
//		mvc.perform(post(SSA_AUTHORIZE_URI)
//					.
//				)
//		
//	}

}
