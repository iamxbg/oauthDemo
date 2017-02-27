package oauthServer.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;

import oauthServer.dao.AuthorizationCodeDao;
import oauthServer.redis.AuthorizationCode;
import oauthServer.util.OAuthUtils;

@Repository
public class AuthorizationCodeDaoImpl implements AuthorizationCodeDao{

	@Autowired
	RedisOperations<String, String> oprs;
	
	public AuthorizationCodeDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveAuthorizationCode(AuthorizationCode code) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOprs=oprs.opsForHash();
			Map<String, String> map=new HashMap<>();
				map.put(OAuth.OAUTH_CLIENT_ID, code.getClient_id());
				map.put(OAuth.OAUTH_EXPIRES_IN, code.getExpires_in());
				map.put("user_id",code.getUser_id());
				String scopeStr=OAuthUtils.convertScopesToScopeStr(code.getScopes());
				map.put(OAuth.OAUTH_SCOPE, scopeStr);
			hOprs.putAll(code.getCode(),map);
			
			
	}

	@Override
	public boolean isAuthorizationCodeExists(String key) {
		// TODO Auto-generated method stub
		return oprs.hasKey(key);
	}

	@Override
	public AuthorizationCode findByKey(String key) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hOprs=oprs.opsForHash();
			Map<String, String> map=hOprs.entries(key);

			Set<String> scopes=OAuthUtils.convertScopeStrToScopes(map.get(OAuth.OAUTH_SCOPE));
			
			AuthorizationCode code=new AuthorizationCode(map.get(OAuth.OAUTH_CLIENT_ID),
														"user_id",
														scopes,
														map.get(OAuth.OAUTH_EXPIRES_IN),
														key);
			
			return code;
	}

}
