package oauthServer.dao;

import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;

public interface TokenDao {
	
	public void saveAccessToken(AccessToken token);
	
	public AccessToken findByKey(String key);
	
	public boolean isTokenExists(String token);
	
}
