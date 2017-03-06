package oauthServer.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;


public interface OAuthService {

	public String addAuthorizationCode(int service_id,int client_id,int user_id);
	
	public String addAccessToken_code(int service_id,int client_id,int user_id);
	
	public String addAccessToken_token(int service_id,int client_id,int user_id);
	
	public String addRefreshToken(int service_id,int client_id,int user_id);
	
	public String getAccessToken(int service_id,int client_id,int user_id);
	
	public String getAuthorizationCode(int service_id,int client_id,int user_id);
	
	public String getRefreshToken(int service_id,int client_id,int user_id);

	public Set<String> getScopes(int service_id,int client_id,int user_id);
	
	public void setScope(int service_id,int client_id,int user_id,Set<String> scopes);
	
	
	
	public String getOpenIdAuthToken(int service_id,int client_id,int user_id);
	public String addOpenIdAuthToken(int service_id,int client_id,int user_id);
	
	
}
