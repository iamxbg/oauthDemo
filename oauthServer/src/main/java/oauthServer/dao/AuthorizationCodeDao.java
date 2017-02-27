package oauthServer.dao;

import oauthServer.redis.AuthorizationCode;

public interface AuthorizationCodeDao {
	
	public void saveAuthorizationCode(AuthorizationCode code);
	
	public boolean isAuthorizationCodeExists(String key);
	
	public AuthorizationCode findByKey(String key);
}
