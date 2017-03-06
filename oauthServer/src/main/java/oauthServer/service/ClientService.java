package oauthServer.service;

import oauthServer.model.Client;

public interface ClientService {

	public Client findById(int id);
	
	public Client findByClient_id(String client_id);

}
