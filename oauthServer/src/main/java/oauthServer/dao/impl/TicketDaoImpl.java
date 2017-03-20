package oauthServer.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;

import oauthServer.dao.TicketDao;

public class TicketDaoImpl implements TicketDao {
	
	@Autowired
	private RedisOperations<String, Map<String, String>> ops;

	@Override
	public void addTicket(String key, Map<String, String> map) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hashOps=ops.opsForHash();
			
		hashOps.putAll(key, map);

	}

	@Override
	public Map<String, String> getContent(String key) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hashOps=ops.opsForHash();
		return hashOps.entries(key);
	
	}

}
