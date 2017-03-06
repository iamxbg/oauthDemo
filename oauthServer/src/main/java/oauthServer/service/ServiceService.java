package oauthServer.service;

import java.util.List;

import oauthServer.model.Service;

public interface ServiceService {
	public List<Service> findByClientId(int cid);
	
	public List<Service> findAll();
	
	public Service findById(int sid);
	
	
	public Service findByService_id(String service_id);
	
	
	
}
