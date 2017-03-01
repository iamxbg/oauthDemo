package oauthServer.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;

import oauthServer.dao.RefreshTokenDao;
import oauthServer.redis.RefreshToken;
import oauthServer.util.OAuthUtils;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {
	
	@Autowired
	RedisOperations<String, String> oprs;

	public RefreshTokenDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveRefreshToken(RefreshToken token) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOps=oprs.opsForHash();
			
			
			Map<String, String> map=new HashMap<>();
			map.put(OAuth.OAUTH_CLIENT_ID, token.getClient_id());
			map.put("user_id",token.getUser_id());
			map.put(OAuth.OAUTH_EXPIRES_IN, token.getExpires_in());
			
			hOps.putAll(token.getToken(), map);
	}


	@Override
	public boolean isRefreshTokenExists(String token) {
		// TODO Auto-generated method stub
		return oprs.hasKey(token);
	}

	@Override
	public RefreshToken findByKey(String key) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOprs=oprs.opsForHash();
			Map<String, String> map=hOprs.entries(key);
			
			String client_id=map.get(OAuth.OAUTH_CLIENT_ID);
			String user_id=map.get("user_id");
			String expires_in=map.get(OAuth.OAUTH_EXPIRES_IN);
			
			Set<String> scopes=OAuthUtils.convertScopeStrToScopes(map.get(OAuth.OAUTH_SCOPE));

			RefreshToken token=new RefreshToken(client_id, user_id, expires_in, key, scopes);
			
			return token;
	}
	
}
