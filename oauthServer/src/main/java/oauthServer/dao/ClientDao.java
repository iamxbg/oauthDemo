package oauthServer.dao;

import oauthServer.model.Client;

public interface ClientDao {
	
	public Client findById(int id);
	
	public Client findByClient_id(String client_id);

	
	
}
