package oauthClient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import oauthClient.config.RootConfig;

@ContextConfiguration(classes={RootConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ResourceRequestTester {
	
	private Logger logger=LogManager.getLogger(ResourceRequestTester.class);
	
	private static String TEST_URI="";
	private static String CLIENT_ID="";
	private static String CLIENT_SERCRECT="";
	
	@Test
	public void testAskForResource(){
		
		RequestConfig config=RequestConfig.custom()
					.setConnectTimeout(3000)
					.setAuthenticationEnabled(true)
					.build();
		
		HttpClient client=HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.build();
		
		HttpUriRequest request=new HttpGet(TEST_URI);
		try {
			HttpResponse resp=client.execute(request);
			StatusLine statusL=resp.getStatusLine();
				int sc=statusL.getStatusCode();
				String rp=statusL.getReasonPhrase();
				logger.log( Level.INFO,"status::"+sc+" resonP:"+rp);
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
