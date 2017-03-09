package oauthServer.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OAuthUtils {

	public static String convertScopesToScopeStr(Set<String> scopes){
		StringBuilder scopeBuilder=new StringBuilder();
		for(String s:scopes){
			if(scopeBuilder.length()!=0) scopeBuilder.append(";");
			scopeBuilder.append(s);
		}
				
		return scopeBuilder.toString();
	}
	
	public static Set<String> convertScopeStrToScopes(String scopeStr){
		
		Set<String> set=new HashSet<>();
			
		String[] scopeAry=scopeStr.split(",");
		
		for(String scope:scopeAry){
			if("".equals(scope)) set.add(scope);
		}

		return set;
	}
	
	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
	
	

	
}
