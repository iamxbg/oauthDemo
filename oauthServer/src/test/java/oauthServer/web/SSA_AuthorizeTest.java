package oauthServer.web;

import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.common.OAuth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;

@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SSA_AuthorizeTest {

	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mvc;
	
	private static Logger logger=LogManager.getLogger();
	
	@Before
	public void setup(){
		mvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void SSA_AuthorizeTest() throws Exception {
		// TODO Auto-generated constructor stub
		
		String client_id="chuanyuyishen";
		String response_type=OAuth.OAUTH_CODE;
		String scopesStr="scp1,scp2";
		String state="23430fjeolnknn";
		String redirect_uri="someredirect_uri";
		
		String user_id="2";
		String username="wilson";
		String password="123456";
		
		MediaType mediaType=MediaType.APPLICATION_FORM_URLENCODED;
		MediaType acceptType=MediaType.APPLICATION_FORM_URLENCODED;
		String characterEncoding="utf-8";
		
		logger.info("test user authorize fail!");
		MvcResult result=mvc.perform(
				post("/ssa/authorize")
					.param(OAuth.OAUTH_CLIENT_ID,client_id)
					.param(OAuth.OAUTH_RESPONSE_TYPE,response_type)
					.param(OAuth.OAUTH_SCOPE,scopesStr)
					.param(OAuth.OAUTH_STATE,state)
					.param(OAuth.OAUTH_REDIRECT_URI, redirect_uri)
					.param("user_id", user_id)
					.param("username", username)
					.param("password", password)
					.contentType(mediaType)
					.accept(acceptType)
					.characterEncoding(characterEncoding)
				).andDo(print()).andExpect(status().isOk()).andReturn();
		
		
	}

}
