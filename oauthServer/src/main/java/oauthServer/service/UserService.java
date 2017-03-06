package oauthServer.service;

import oauthServer.model.User;

public interface UserService {

	
	public User findById(int id);
	
	public User findByOpenId(String openId);
	
	public User findByUid(int uid);
}
