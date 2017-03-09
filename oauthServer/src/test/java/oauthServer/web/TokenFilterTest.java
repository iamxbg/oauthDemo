package oauthServer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static oauthServer.util.OAuthConstants.*;
import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;

@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TokenFilterTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mvc;
	
	public TokenFilterTest() {
		// TODO Auto-generated constructor stub
		
	}
	
	@Before
	public void setup(){
		this.mvc=MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testSendRequest() throws Exception{
		MvcResult result=mvc.perform(get("/example/some")).andDo(print()).andReturn();
		
		assertNotNull(result);
		System.out.println(result.getResponse().getHeader(ERROR_DESCRIPTION));
		
	}

}
