package oauthServer.dao;

import java.util.List;

import oauthServer.model.Service;

public interface ServiceDao {

		public List<Service> findByClientId(int cid);
		
		public List<Service> findAll();
		
		public Service findById(int sid);
		
		public Service findByService_id(String service_id);
		
}
