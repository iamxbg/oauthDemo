package oauthServer.dao.impl;

import java.util.List;

import javax.servlet.Registration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.UserDao;
import oauthServer.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sf;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("from User u ").list();
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(User t) {
		// TODO Auto-generated method stub
		return (Integer)sf.getCurrentSession().save(t);
	}

	@Override
	public void delete(User t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User t) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return (User) sf.getCurrentSession().createQuery("from User u where u.username=:username and u.password=:password")
				.setString("username", username)
				.setString("password", password)
				.uniqueResult();
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		Session sess=sf.getCurrentSession();
			User u=sess.get(User.class, id);
			if(u!=null) sess.delete(u);
	}

}
