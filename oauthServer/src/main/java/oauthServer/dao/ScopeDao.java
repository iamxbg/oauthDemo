package oauthServer.dao;

import java.util.List;

import oauthServer.model.Scope;
import oauthServer.model.ServiceClientId;

public interface ScopeDao {
	
	public List<Scope> getScopesByClientId(int cid);
	
	public List<Scope> getScopesBydServiceId(int serviceId);
	
	public Scope findById(int id);
	
}
