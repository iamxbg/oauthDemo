package oauthServer.service;

import java.util.List;

import oauthServer.model.Registration;

public interface RegistrationService {
	
	public List<Registration> findAll();
	
	public void add(Registration r);
	
	public void delete(int id);

	public void update(Registration r);

	public Registration findByClientId(String client_id);
	
	
	public Registration findById(int id);
}
