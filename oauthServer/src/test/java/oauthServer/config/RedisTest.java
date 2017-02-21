package oauthServer.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class})
public class RedisTest {

	@Autowired
	private JedisConnectionFactory cf;
	@Autowired
	private RedisOperations<String, String> opr;
	
	@Test
	public void testNotNull(){
		Assert.assertNotNull(cf);
		Assert.assertNotNull(opr);
	}
	
	@Test
	public void testConnection(){
		RedisConnection conn=cf.getConnection();
		Assert.assertNotNull(conn);
	
		
	}
	
	
	@Test
	public void testTemplate(){
		SetOperations<String, String> setOprs=opr.opsForSet();
			
		setOprs.add("springSet", "val1","val2","val3");
		
	}
	

}
