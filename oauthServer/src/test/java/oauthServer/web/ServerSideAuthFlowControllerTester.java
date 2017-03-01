package oauthServer.web;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest.AuthenticationRequestBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;

@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ServerSideAuthFlowControllerTester {

	@Autowired
	private WebApplicationContext webAppContext;
	private MockMvc mockMvc;
	
	public ServerSideAuthFlowControllerTester() {
		// TODO Auto-generated constructor stub
	}
	
	@BeforeClass
	public void setup(){
		System.out.println("--------run test of Server Side Auhtorization Flow (—_>—)");
		mockMvc=MockMvcBuilders.webAppContextSetup(webAppContext).build();
		
	}

	
	@Test
	public void testAuthorizationView(){
		
		
	}
}
