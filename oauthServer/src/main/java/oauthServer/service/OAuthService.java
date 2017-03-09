package oauthServer.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;


public interface OAuthService {

	//key labels
	public  static String REDIS_KEY_ACCESS_TOKEN="at";
	public  static String REDIS_KEY_AUTHORIZATION_CODE="ac";
	public  static String REDIS_KEY_OPENID="oi"; 
	public  static String REDIS_KEY_OPENID_AUTHORIZATION_TOKEN="oit";
	// fields labels
	public static String REDIS_FIELD_EXPIRES_AT="exat";
	public static String REDIS_FIELD_TOKEN="tkn";
	public static String REDIS_FIELD_SCOPES="scp";
	public static String REDIS_FIELD_REFRESH_TOKEN="rtk";
	public static String REDIS_FIELD_OPENID="openid";
	public static String REDIS_FIELD_CODE="cd";
	
	
	public String addAuthorizationCode(int service_id_int,int client_id_int,int user_id_int);
	
	public String addAccessToken_code(int service_id_int,int client_id_int,int user_id_int);
	
	public String addAccessToken_token(int service_id_int,int client_id_int,int user_id_int);
	
	public String addOpenIdAuthToken(int service_id,int client_id,int user_id);
	
	public String addOpenId(int service_id,int client_id,int user_id);

	public Map<String, String> getAccessToken(String scukey);
	
	public Map<String, String> getAuthorizationCode(String  scuKey);

	public Map<String, String> getOpenIdAuthToken(String scuKey);
	
	
	public Map<String, String> getOpenId(String scuKey);
	
	
	
}
