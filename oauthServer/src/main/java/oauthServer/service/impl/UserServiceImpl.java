package oauthServer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.model.User;
import oauthServer.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserService userService;
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return userService.findById(id);
	}

	@Override
	public User findByOpenId(String openId) {
		// TODO Auto-generated method stub
		return userService.findByOpenId(openId);
	}

	@Override
	public User findByUid(int uid) {
		// TODO Auto-generated method stub
		return userService.findByUid(uid);
	}

}
