package oauthServer.dao;

import java.util.Map;

public interface TicketDao {
	
	public void addTicket(String key,Map<String, String> map);
	
	public Map<String, String> getContent(String key);
}
