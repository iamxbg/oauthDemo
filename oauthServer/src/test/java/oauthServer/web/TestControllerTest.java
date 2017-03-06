package oauthServer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.net.SyslogAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.databind.ObjectMapper;

import  static org.springframework.test.web.servlet.result.ViewResultMatchers.*;

import java.util.Date;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;
import oauthServer.model.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
public class TestControllerTest {
	
	  MockMvc mvc;
	@Autowired
	 WebApplicationContext ctx;
	
	private Logger logger=LogManager.getLogger();
	
	@Before
	public  void setup(){
		mvc=MockMvcBuilders.webAppContextSetup(ctx).build();
		MockitoAnnotations.initMocks(this);

	}

	@Ignore
	@Test
	public void testPostWithParams() throws Exception{
		
		MvcResult result=mvc.perform(post("/test/testPostWithParams").param("username", "dude"))
				.andExpect(status().isOk())
				.andReturn();
		
		HttpServletResponse resp=result.getResponse();
		Assert.assertNotNull(resp);
		System.out.println("status:"+resp.getStatus());
		
		ModelAndView mav=result.getModelAndView();
		Assert.assertNotNull(mav);
		logger.info("viewname:"+mav.getViewName());
	}
	
	@Ignore
	@Test
	public void testJsonWithResponseEntity() throws Exception{
		
		User user=new User("o_username", "o_password", "o_name");
		user.setCreate_time(new Date());
		
		ObjectMapper mapper=new ObjectMapper();
			String userStr=mapper.writeValueAsString(user);
		
			logger.info("userStr:"+userStr);
			
		MvcResult result=mvc.perform(post("/test/testJsonWithResponseEntity")
				.contentType(MediaType.APPLICATION_JSON)
				//.accept(MediaType.APPLICATION_JSON)
				.content(userStr))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		
		
	}
	
	@Test
	public void testGetStringView() throws Exception{
		
		MvcResult result=mvc.perform(get("/test/testGetStringView/id=testId"))
				.andDo(print())
				.andExpect(status().isOk()).andReturn();
				
	}

}
