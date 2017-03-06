package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.ManageSwitcherDao;
import oauthServer.model.ManageSwitcher;

@Repository
public class ManageSwitcherDaoImpl implements ManageSwitcherDao{

	public ManageSwitcherDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private SessionFactory sf;

	@Override
	public ManageSwitcher findByName(String name) {
		// TODO Auto-generated method stub
		return (ManageSwitcher) sf.getCurrentSession().createQuery("from ManageSwitcher ms where ms.name=:name")
		.setString("name", name).uniqueResult();
	}

	@Override
	public void switchStatus(String name) {
		// TODO Auto-generated method stub
		Session session=sf.getCurrentSession();
		ManageSwitcher ms=(ManageSwitcher) session.createQuery("from ManageSwitcher ms where ms.name=:name")
				.setString("name", name).uniqueResult();
		
		if(ms!=null){
			if(ms.getIs_on()=='Y') ms.setIs_on('N');
			else ms.setIs_on('Y');
			session.update(ms);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManageSwitcher> findAll() {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("from ManageSwither").list();
	}

}
