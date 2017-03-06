package oauthServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ScopeDao;
import oauthServer.model.Scope;

@Service
@Transactional
public class ScopeServiceImpl implements oauthServer.service.ScopeService {

	@Autowired
	private ScopeDao scopeDao;
	
	public ScopeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Scope> getScopesByClientId(int cid) {
		// TODO Auto-generated method stub
		return scopeDao.getScopesByClientId(cid);
	}

	@Override
	public List<Scope> getScopesBydServiceId(int serviceId) {
		// TODO Auto-generated method stub
		return scopeDao.getScopesBydServiceId(serviceId);
	}

	@Override
	public Scope findById(int id) {
		// TODO Auto-generated method stub
		return scopeDao.findById(id);
	}

}
