package oauthServer.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import oauthServer.dao.ServiceDao;
import oauthServer.model.Service;
import oauthServer.service.ServiceService;

@Transactional
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{

	@Autowired
	private ServiceDao sDao;
	
	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return sDao.findAll();
	}

}
