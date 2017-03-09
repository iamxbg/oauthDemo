package oauthServer.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@WebAppConfiguration
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
	public void testGet(){
		ValueOperations<String, String> valOps=opr.opsForValue();
		String test=valOps.get("someKey");
		System.out.println(test);
	}
	
}
