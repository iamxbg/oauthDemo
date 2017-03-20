package oauthServer.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OAuthUtils {
	
	private static Logger logger=LogManager.getLogger();
	
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
	
	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
	
	
	public static String  generateTicket(int service_id_int,int client_id_int,int user_id_int,TicketType type ){
		
		String prefix=null;
		
		switch (type) {
			case ACCESS_TOEKN:
				prefix=REDIS_KEY_ACCESS_TOKEN;
				break;
			case REFRESH_TOEKN:
				prefix=REDIS_FIELD_REFRESH_TOKEN;
				break;
			case AUTHORIZATION_CODE:
				prefix=REDIS_KEY_AUTHORIZATION_CODE;
				break;
			case OPEN_ID:
				prefix=REDIS_KEY_OPENID;
				break;
			default:
				break;
		}
		
		return new StringBuilder(prefix).append(":").append(service_id_int)
				.append(":").append(client_id_int)
				.append(":").append(user_id_int)
				.append("#").append(generateEncryptHash())
				.toString();
		
	}
	
	/**
	 *  Need improvement
	 * @return
	 */
	static String  generateEncryptHash(){
		return UUID.randomUUID().toString();
	}
	
}
