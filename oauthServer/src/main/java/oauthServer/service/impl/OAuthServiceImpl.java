package oauthServer.service.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import oauthServer.service.OAuthService;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;

@Service
public class OAuthServiceImpl implements OAuthService {
	
	public  static String REFRESH_TOKEN_KEY_LABEL="rftkn";
	public  static String ACCESS_TOKEN_KEY_LABEL="tkn";
	public  static String AUTHZ_CODE_KEY_LABEL="ac";
	public  static String SCOPE_KEY_LABEL="scp";
	public  static String OPENID_TOKEN_KEY_LABEL="oit"; 
	
	public OAuthServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private RedisOperations<String, String> oprs;
	
	@Override
	public String addAuthorizationCode(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(AUTHZ_CODE_KEY_LABEL)
			.append(":").append(service_id)
			.append(":").append(client_id)
			.append(":").append(user_id)
			.toString();
		String value=OAuthUtils.generateUUID();
		
		ValueOperations<String,String> valOps=oprs.opsForValue();
			valOps.set(key, value,OAuthConstants.AUTHORIZATION_CODE_EXPIRES_IN,TimeUnit.SECONDS);
			
			return value;
	}

	@Override
	public String addAccessToken_code(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(ACCESS_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
			String value=OAuthUtils.generateUUID();
			
			ValueOperations<String,String> valOps=oprs.opsForValue();
				valOps.set(key, value,OAuthConstants.ACCESS_TOKEN_CODE_EXPIRES_IN,TimeUnit.SECONDS);

				return value;
	}
	
	@Override
	public String addAccessToken_token(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(ACCESS_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
			String value=OAuthUtils.generateUUID();
			
			ValueOperations<String,String> valOps=oprs.opsForValue();
				valOps.set(key, value,OAuthConstants.ACCESS_TOKEN_TOKEN_EXPIRES_IN,TimeUnit.SECONDS);
	
				return value;
	}
	

	@Override
	public String addRefreshToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REFRESH_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
			String value=OAuthUtils.generateUUID();
			
			ValueOperations<String,String> valOps=oprs.opsForValue();
				valOps.set(key, value,OAuthConstants.REFRESH_TOKEN_EXPIRES_IN,TimeUnit.SECONDS);
				
				return value;
	}

	@Override
	public String getAccessToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		
		String key=new StringBuilder(ACCESS_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
					
		ValueOperations<String,String> valOps=oprs.opsForValue();
		return valOps.get(key);

	}

	@Override
	public String getAuthorizationCode(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(AUTHZ_CODE_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
					
		ValueOperations<String,String> valOps=oprs.opsForValue();
		return valOps.get(key);
	}

	@Override
	public String getRefreshToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REFRESH_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
					
		ValueOperations<String,String> valOps=oprs.opsForValue();
		return valOps.get(key);
	}

	@Override
	public Set<String> getScopes(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(SCOPE_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
		SetOperations<String, String> setOps=oprs.opsForSet();
		return setOps.members(key);

	}

	@Override
	public void setScope(int service_id, int client_id, int user_id, Set<String> scopes) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(SCOPE_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
		SetOperations<String, String> setOps=oprs.opsForSet();
			for(String scp:scopes){
				setOps.add(key, scp);
			}
			
			
		}

	@Override
	public String getOpenIdAuthToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(OPENID_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
					
		ValueOperations<String,String> valOps=oprs.opsForValue();
		return valOps.get(key);
	}

	@Override
	public String addOpenIdAuthToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REFRESH_TOKEN_KEY_LABEL)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
			String value=OAuthUtils.generateUUID();
			
			ValueOperations<String,String> valOps=oprs.opsForValue();
				valOps.set(key, value,OAuthConstants.OPENID_TOKEN_EXPIRES_IN,TimeUnit.SECONDS);
				
				return value;
	}
		



}
