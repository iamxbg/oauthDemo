package oauthServer.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;
import oauthServer.dao.TokenDao;
import oauthServer.redis.AccessToken;
import oauthServer.util.OAuthUtils;

@Repository
public class TokenDaoImpl implements TokenDao{

	@Autowired
	private RedisOperations<String, String> oprs;
	

	@Override
	public boolean isTokenExists(String token) {
		// TODO Auto-generated method stub
		return oprs.hasKey(token);
	}


	@Override
	public void saveAccessToken(AccessToken token) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOprs=oprs.opsForHash();
		
		//* set expires_in
		
		Map<String, String> map=new HashMap<>();
			map.put(OAuth.OAUTH_CLIENT_ID, token.getClient_id());
			map.put("user_id",token.getUser_id());
			map.put(OAuth.OAUTH_SCOPE, OAuthUtils.convertScopesToScopeStr(token.getScopes()));
			
			hOprs.putAll(token.getToken(), map);
	}

	@Override
	public AccessToken findByKey(String key) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOprs=oprs.opsForHash();
			Map<String, String> map=hOprs.entries(key);
			
			String client_id=map.get(OAuth.OAUTH_CLIENT_ID);
			String user_id=map.get("user_id");
			String scopesStr=map.get(OAuth.OAUTH_SCOPE);
			Set<String> scopes=OAuthUtils.convertScopeStrToScopes(map.get(OAuth.OAUTH_SCOPE));
			//String expires_in=map.get(OAuth.OAUTH_EXPIRES_IN);
			String expires_in=map.get(OAuth.OAUTH_EXPIRES_IN);

			AccessToken token=new AccessToken(client_id, user_id, scopes, expires_in, key);
			
			return token;
	}
	
	
}
