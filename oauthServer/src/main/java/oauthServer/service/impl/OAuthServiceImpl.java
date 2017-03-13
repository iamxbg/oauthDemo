package oauthServer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import oauthServer.service.OAuthService;
import static oauthServer.util.OAuthConstants.*;
import oauthServer.util.OAuthUtils;

@Service
public class OAuthServiceImpl implements OAuthService {
	
	
	
	public OAuthServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private RedisOperations oprs;

//	@Autowired
//	private RedisOperations<String, Map<String, String>> oprs;
	
	@Override
	public String addAuthorizationCode(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_AUTHORIZATION_CODE)
			.append(":").append(service_id)
			.append(":").append(client_id)
			.append(":").append(user_id)
			.toString();
		String codeVal=OAuthUtils.generateUUID();
		
		HashOperations<String,String,String> valOps=oprs.opsForHash();
		
			Map<String, String> m=new HashMap<>();
				m.put(REDIS_FIELD_CODE, codeVal);
			
			valOps.putAll(key, m);
			oprs.expire(key, AUTHORIZATION_CODE_EXPIRES_IN, TimeUnit.SECONDS);
			
			return new StringBuilder(key).append(":").append(codeVal).toString();
	}

	@Override
	public String addAccessToken_code(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_ACCESS_TOKEN)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
		
			String tokenVal=OAuthUtils.generateUUID();
			String refreshVal=OAuthUtils.generateUUID();
					
			HashOperations<String,String,String> valOps=oprs.opsForHash();
			
			Map<String, String> m=new HashMap<>();
			
			//scopes=.
			
			m.put(REDIS_FIELD_REFRESH_TOKEN,refreshVal );
			m.put(REDIS_FIELD_TOKEN, tokenVal);
			m.put(REDIS_FIELD_EXPIRES_AT, new Long(new Date().getTime()+ACCESS_TOKEN_CODE_EXPIRES_IN).toString());
		//	m.put(REDIS_FIELD_SCOPES, value);
			
				valOps.putAll(key, m);
				oprs.expire(key,ACCESS_TOKEN_CODE_EXPIRES_IN,TimeUnit.SECONDS);

				return new StringBuilder(key).append(":").append(tokenVal).toString();
	}
	
	@Override
	public String addAccessToken_token(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_ACCESS_TOKEN)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
			String tokenValue=OAuthUtils.generateUUID();
			
			HashOperations<String,String,String> valOps=oprs.opsForHash();
				//valOps.set(key, value,ACCESS_TOKEN_TOKEN_EXPIRES_IN,TimeUnit.SECONDS);
			
			Map<String, String> m=new HashMap<>(); 

				String expires_at=new Long(new Date().getTime()+ACCESS_TOKEN_TOKEN_EXPIRES_IN).toString();
			
				m.put(REDIS_FIELD_EXPIRES_AT, expires_at);
				m.put(REDIS_FIELD_TOKEN, tokenValue);
			//	m.put(REDIS_FIELD_SCOPES, value);

			valOps.putAll(key, m);
			
			return new StringBuilder(key).append(":").append(tokenValue).toString();
	}
	



	@Override
	public Map<String, String> getAccessToken(String scuKey) {
		// TODO Auto-generated method stub
		
		String key=new StringBuilder(REDIS_KEY_ACCESS_TOKEN)
				.append(":").append(scuKey).toString();
					
		HashOperations<String,String, String> valOps=oprs.opsForHash();
		return valOps.entries(key);

	}

	@Override
	public Map<String, String> getAuthorizationCode(String scuKey) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_AUTHORIZATION_CODE)
				.append(":").append(scuKey).toString();
					
		HashOperations<String,String,String> valOps=oprs.opsForHash();
		return valOps.entries(scuKey);
	}





	@Override
	public Map<String, String> getOpenIdAuthToken(String scuKey) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_OPENID_AUTHORIZATION_TOKEN)
				.append(":").append(scuKey).toString();
					
		HashOperations<String,String,String> valOps=oprs.opsForHash();
		return valOps.entries(key);
	}

	@Override
	public String addOpenIdAuthToken(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_OPENID_AUTHORIZATION_TOKEN)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
			String value=OAuthUtils.generateUUID();
			
			HashOperations<String,String,String> valOps=oprs.opsForHash();
			
			String authTOken="FAKE_AUTH_TOKEN";
			
			Map<String, String> m=new HashMap<>();
				m.put(REDIS_FIELD_CODE, authTOken);
				valOps.putAll(key, m);
				
				
			oprs.expire(key, OPENID_TOKEN_EXPIRES_IN, TimeUnit.SECONDS);

				return new StringBuilder(key).append(":").append(value).toString();
	}

	@Override
	public String addOpenId(int service_id, int client_id, int user_id) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_OPENID)
				.append(":").append(service_id)
				.append(":").append(client_id)
				.append(":").append(user_id)
				.toString();
		
			String value=OAuthUtils.generateUUID();
			
			HashOperations<String,String,String> valOps=oprs.opsForHash();
			
				//valOps.set(key, value,OAuthConstants.OPENID_TOKEN_EXPIRES_IN,TimeUnit.SECONDS);
			
				String openid="FAKE_OPEN_ID";
			
				Map<String, String>  m=new HashMap<>();
					m.put(REDIS_KEY_OPENID, openid);
			
				valOps.putAll(key, m);
				oprs.expire(key, OPENID_EXPIRES_IN, TimeUnit.SECONDS);
				//++++++++++++++++++++++++++++
				return new StringBuilder(key).append(":").append(openid).toString();
	}

	@Override
	public Map<String, String> getOpenId(String scuKey) {
		// TODO Auto-generated method stub
		String key=new StringBuilder(REDIS_KEY_OPENID)
				.append(":").append(scuKey).toString();
					
		HashOperations<String,String,String> valOps=oprs.opsForHash();
		
		return valOps.entries(key);
	}

	@Override
	public Map<String, String> getUserInfoByOpenId(String openid) {
		// TODO Auto-generated method stub
		
		HashOperations<String, String, String> hashOps=oprs.opsForHash();
		
			String openIdKeyPart=new StringBuilder(REDIS_KEY_OPENID)
							.append(":").append(openid).toString();
			
			Map<String, String> userInfo=hashOps.entries(openIdKeyPart);
			
			return userInfo;

		
	}
		



}
