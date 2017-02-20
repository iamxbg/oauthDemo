package oauthServer.dao;

import java.util.List;

import oauthServer.model.Registration;

public interface RegistrationDao extends CommonDao<Registration>{
	
	public Registration findByClientId(String client_id);
}
