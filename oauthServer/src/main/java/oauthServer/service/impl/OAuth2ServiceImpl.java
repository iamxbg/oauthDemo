package oauthServer.service.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import oauthServer.service.OAuth2Service;

@Service
public class OAuth2ServiceImpl implements OAuth2Service{

	@Autowired
	private RedisOperations<String, String> ops;
	
	@Override
	public Map<String, String> get(String key) {
		// TODO Auto-generated method stub
		
		HashOperations<String, String, String> hashOps=ops.opsForHash();
		

		return hashOps.entries(key);

	}

	@Override
	public void add(String key, Map<String, String> values) {
		// TODO Auto-generated method stub
		
		HashOperations<String, String, String> hashOps=ops.opsForHash();
		
		hashOps.putAll(key, values);
	}

}
