package oauthServer.util;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenIdUtil {
	
	
	private static Logger logger=LogManager.getLogger();
	
	public OpenIdUtil() {
		// TODO Auto-generated constructor stub
	}

	
	static String encodingServiceId(String serviceId){
		return serviceId;
	}
	
	static String encodingClientId(String clientId){
		return clientId;
	}
	
	static String encodingUserId(String userId){
		return userId;
	}
	
	
	static  String encodingTimeStamp(){
		return String.valueOf(new Date());
	}
	
	public static String generateOpenId(String serviceId,String clientId,String userId){
		String openId= new StringBuilder()
					.append(encodingServiceId(serviceId))
					.append(encodingClientId(clientId))
					.append(encodingUserId(userId))
					.append(encodingTimeStamp()).toString();
		logger.info("generate OpenId:"+openId);
		return openId;
	}
}
