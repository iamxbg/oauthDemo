package oauthServer.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;

import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;
import oauthServer.redis.RefreshToken;

public interface OAuthService {

	public void addAuthorizationCode(AuthorizationCode code);
	
	public void addAccessToken(AccessToken token);
	
	public void addRefreshToken(RefreshToken tokne);
	
	
	public boolean isAccessTokenExists(String key);
	
	public boolean isAuthorizationCodeExist(String key);
	
	public boolean isRefreshTokenExists(String key);
	
	public AccessToken getAccessToken(String key);
	
	public AuthorizationCode getAuthorizationCode(String key);
	
	public RefreshToken getRefreshToken(String key);

	
}
