package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.RegistrationDao;
import oauthServer.model.Registration;

@Repository
public class RegistrationDaoImpl implements RegistrationDao{
	
	@Autowired
	private SessionFactory sf;

	public RegistrationDaoImpl() {
		// TODO Auto-generated constructor stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> findAll() {
		// TODO Auto-generated method stub
		return  sf.getCurrentSession().createQuery("from Registration").list();
	}

	@Override
	public Registration findById(int id) {
		// TODO Auto-generated method stub
		return (Registration) sf.getCurrentSession().createQuery(" from Registration r where r.id=:id")
				.setInteger("id", id).uniqueResult();
	}

	@Override
	public void add(Registration t) {
		// TODO Auto-generated method stub
		sf.getCurrentSession().save(t);
		
	}

	@Override
	public void delete(Registration t) {
		// TODO Auto-generated method stub
		sf.getCurrentSession().delete(t);
		
	}

	@Override
	public void update(Registration t) {
		// TODO Auto-generated method stub
		sf.getCurrentSession().update(t);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
	
		Session sess=sf.getCurrentSession();
			Registration r=sess.get(Registration.class, id);
			if(r!=null) sess.delete(r);
	}
	

	@Override
	public Registration findByClientId(String client_id) {
		// TODO Auto-generated method stub
		return (Registration) sf.getCurrentSession().createQuery("from Registration r where r.client_id =:client_id")
				.setString("client_id", client_id)
				.uniqueResult();
	}

}
