package oauthServer.dao;

import java.util.List;

import oauthServer.model.User;

public interface UserDao {

	
	public User findById(int id);
	
	public User findByOpenId(String openId);
	
	public User findByUid(int uid);
	
}
