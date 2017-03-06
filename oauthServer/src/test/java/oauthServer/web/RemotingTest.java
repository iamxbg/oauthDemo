package oauthServer.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;
import oauthServer.service.UserService;
import oauthServer.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
public class RemotingTest {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Test
	public void RemotingTest() {
		// TODO Auto-generated constructor stub
		String serviceUrl=userServiceImpl.getServiceUrl();
		System.out.println("serviceUrl:"+serviceUrl);
	}

}
