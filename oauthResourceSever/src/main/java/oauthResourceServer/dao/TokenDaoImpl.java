package oauthResourceServer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;

public class TokenDaoImpl implements TokenDao {

	@Autowired
	private RedisOperations<String, String> oprs;

	@Override
	public boolean isExists(String redisServiceName, String token) {
		// TODO Auto-generated method stub
		SetOperations<String, String> setOprs=oprs.opsForSet();
			if(setOprs.isMember(redisServiceName, token)) return true;
		return false;
	}

}
