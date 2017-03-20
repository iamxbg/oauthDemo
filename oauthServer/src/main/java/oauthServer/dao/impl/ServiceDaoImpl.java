package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.ServiceDao;
import oauthServer.model.Service;

@Repository
public class ServiceDaoImpl implements ServiceDao{

	@Autowired
	private SessionFactory sf;
	
	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("from Service s").list();
	}

}
