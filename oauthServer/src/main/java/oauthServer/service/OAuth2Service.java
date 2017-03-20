package oauthServer.service;
import java.util.Map;

public interface OAuth2Service {

	public Map<String,String> get(String key); 
		
	public void add(String key,Map<String, String> values);
	
}
