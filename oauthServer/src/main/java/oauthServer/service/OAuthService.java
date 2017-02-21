package oauthServer.service;

import java.util.List;

public interface OAuthService {

	//authorization-code format
	// key: client_id:user_id:timestampInMilliseconds
	// val: HASH_CHARACTERS
	
	
	public String addAuthzCode(String client_id,String user_id,String scopes);
	
	public String addAccessToken(String client_id,String user_id,String scopes);
	
	public boolean isAccessTokenExists(String token);
	
	public boolean isAuthzCodeExist(String code);
	
}
