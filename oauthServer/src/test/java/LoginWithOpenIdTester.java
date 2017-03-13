

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.net.URI;
import java.net.URISyntaxException;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;

@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LoginWithOpenIdTester {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	
	public LoginWithOpenIdTester() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setup(){
		mvc=MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void loginWithOpenId_wrongService_id() throws Exception{
		 String service_id="";
		 String client_id="";
		 String openid="";
		
		 String url=new StringBuilder("http://localhost:8080/oauthServer/openid/loginWithOpenid/")
				 	.append("service_id=").append(service_id).append("&")
				 	.append("client_id=").append(client_id).append("&")
				 	.append("openid=").append(openid).toString();
		 
		 URI uri=new URI(url);
		 MvcResult result=mvc.perform(get(uri)).andDo(print()).andReturn();

	}

}
