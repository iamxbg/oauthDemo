package oauthServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ServiceDao;
import oauthServer.model.Service;
import oauthServer.service.ServiceService;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	private ServiceDao serviceDao;

	public ServiceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Service> findByClientId(int cid) {
		// TODO Auto-generated method stub
		return serviceDao.findByClientId(cid);
	}

	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return serviceDao.findAll();
	}

	@Override
	public Service findById(int sid) {
		// TODO Auto-generated method stub
		return serviceDao.findById(sid);
	}

	@Override
	public Service findByService_id(String service_id) {
		// TODO Auto-generated method stub
		return serviceDao.findByService_id(service_id);
	}

}
