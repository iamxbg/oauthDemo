package oauthServer.web;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyStoreBuilderParameters;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.conn.routing.RouteInfo.LayerType;
import org.apache.http.conn.routing.RouteInfo.TunnelType;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.ls.LSInput;

import oauthServer.config.RootConfig;

@ContextConfiguration(classes={RootConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpsTestor {
	
	private static Logger logger=LogManager.getLogger();

	public HttpsTestor() {
		// TODO Auto-generated constructor stub
	}
	
	public  SSLContext context=null;
	@Before
	public void setContext() {
		KeyStore store;
		try {
			store = KeyStore.getInstance("JKS");
			 context=SSLContexts.custom()
						.loadTrustMaterial(store, new TrustSelfSignedStrategy()).build();
			String protocol= context.getProtocol();
			SSLParameters ps=		context.getSupportedSSLParameters();
			String[] prs=ps.getProtocols();
			for(String prr:prs) System.out.println("prr:"+prr);
			boolean na=ps.getNeedClientAuth(); System.out.println("na:"+na);
			
			List<SNIServerName> sniList=ps.getServerNames();
			if(sniList!=null)
				for(SNIServerName sni:sniList){
					System.out.println("sni:"+sni);
				}
			String alg=ps.getEndpointIdentificationAlgorithm();
			System.out.println("alg:"+alg);
			System.out.println("pro:"+protocol);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();logger.error(e.getMessage());
			
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	}
	
	//@Test
	public void testSendHttpsRequest(){
		
		Assert.assertNotNull(context);
		System.out.println("syso");
		
		
	}
	
	//@Test
	public void testRouteInfo(){
		HttpHost target=new HttpHost("10.244.134.189", 433665351);
		HttpRoute route=new HttpRoute(target);
			TunnelType tt=route.getTunnelType();
			System.out.println("tt:"+tt.name()); 
		
			
			int count=route.getHopCount();
			System.out.println("count:"+count);
			// use 10.244.134.189 or 10.244.131.37 all return count=1,
			// may be some where is wrong ,or it's write
			//? can read source code , and test it with proxy utils!
			
			
	}
	
	@Test
	public void testHttpClient() throws IOException, URISyntaxException{
		
		ConnectionSocketFactory plain_csf=new PlainConnectionSocketFactory();
		
		
		
		LayeredConnectionSocketFactory ssl_csf=new SSLConnectionSocketFactory(context, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		Registry<ConnectionSocketFactory> r=RegistryBuilder.<ConnectionSocketFactory>create()
							.register("http", plain_csf)
							.register("https", ssl_csf)
							.build();
		
		PoolingHttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager(r);
		
		CloseableHttpClient client=HttpClients.custom()
						.setConnectionManager(connManager)
						.build();
		
		URI uri=new URI("https://10.244.134.189:8443/");
		HttpUriRequest request=new HttpGet(uri);
		CloseableHttpResponse resp=null;
		try {
			resp = client.execute(request);
			System.out.println("protocol::"+resp.getProtocolVersion().getProtocol());
			HttpEntity entity=resp.getEntity();
			String result=EntityUtils.toString(entity);
			System.out.println("result:"+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

				if(resp!=null)resp.close();

		}
			
			
	}

	
}
