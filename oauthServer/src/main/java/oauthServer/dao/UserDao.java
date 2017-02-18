package oauthServer.dao;

import oauthServer.model.User;

public interface UserDao extends CommonDao<User> {
	
	
	public User findByUsernameAndPassword(String username,String password);
}
