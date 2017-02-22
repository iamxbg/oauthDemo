package oauthResourceServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages={"oauthResourceServer"}
,excludeFilters={@Filter(type=FilterType.ANNOTATION,value={EnableWebMvc.class})})
public class RootConfig {
	
	private static String REDIS_HOSTNAME="10.244.134.189";
	private static String REDIS_PASSWORD="";
		
		
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxIdle(10);
			config.setMinIdle(5);
			
		JedisConnectionFactory cf=new JedisConnectionFactory(config);
			cf.setTimeout(3000);
			cf.setHostName(REDIS_HOSTNAME);
			cf.setPassword(REDIS_PASSWORD);
		return cf;
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(){
		RedisTemplate< String, String> template=new StringRedisTemplate(jedisConnectionFactory());
		return template;
	}
	
	
}
