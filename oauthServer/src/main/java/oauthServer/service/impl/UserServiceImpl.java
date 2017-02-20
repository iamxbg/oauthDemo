package oauthServer.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.UserDao;
import oauthServer.model.User;
import oauthServer.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public void add(User u) {
		// TODO Auto-generated method stub
		userDao.add(u);
	}
	
	public List<User> findAll(){
		return userDao.findAll();
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.findByUsernameAndPassword(username, password);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		 userDao.deleteById(id);;
	}

	@Override
	public void update(User u) {
		// TODO Auto-generated method stub
		userDao.update(u);
	}
	
	


}
