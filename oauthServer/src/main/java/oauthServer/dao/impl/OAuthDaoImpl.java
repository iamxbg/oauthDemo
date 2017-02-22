package oauthServer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import oauthServer.dao.OAuthDao;

@Repository
public class OAuthDaoImpl implements OAuthDao{

	@Autowired
	private RedisOperations<String, String> oprs;
	
	@Override
	public void saveAuthzrizatioCode(String client_id,String authzCode) {
		// TODO Auto-generated method stub
		SetOperations<String, String> setOprs=oprs.opsForSet();
		setOprs.add(client_id, authzCode);
	
		
	}

	@Override
	public void saveAccessToken(String client_id,String accessToken) {
		// TODO Auto-generated method stub
		SetOperations<String, String> setOprs=oprs.opsForSet();
		setOprs.add(client_id, accessToken);
		
	}
	
	
}
