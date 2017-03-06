package oauthServer.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.UserDao;
import oauthServer.model.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sf;
	
	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return (User) sf.getCurrentSession().createQuery("from User u where u.id=:id")
				.setInteger("id", id).uniqueResult();
	}

	@Override
	public User findByOpenId(String openId) {
		// TODO Auto-generated method stub
		return (User) sf.getCurrentSession().createQuery("from User u where u.openid=:openid")
				.setString("openid", openId).uniqueResult();
	}

	@Override
	public User findByUid(int uid) {
		// TODO Auto-generated method stub
		return (User) sf.getCurrentSession().createQuery("from User u where u.uid=:uid")
				.setInteger("uid", uid).uniqueResult();
	}


}
