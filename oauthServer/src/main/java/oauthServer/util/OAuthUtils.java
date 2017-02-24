package oauthServer.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.oltu.oauth2.common.OAuth;

public class OAuthUtils {

		
	public static String generateAuthorizationCode(){
		//return UUID.fromString(new SimpleDateFormat("yyyy-MM-dd ss:dd:hh").format(new Date())).toString();
		return "FAKE_AUTHZ_CODE";
	}

	// get the token's sturcture...
	public static String generateToken(){
//		return new StringBuilder(UUID.randomUUID().toString())
//				.toString();
		return "FAKE_TOKEN";
	}
	
	
	public static String convertScopesToScopeStr(Set<String> scopes){
		StringBuilder scopeBuilder=new StringBuilder();
		for(String s:scopes){
			if(scopeBuilder.length()!=0) scopeBuilder.append(";");
			scopeBuilder.append(s);
		}
				
		return scopeBuilder.toString();
	}
	
}
