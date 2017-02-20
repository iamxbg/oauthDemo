package oauthServer.service;

import java.util.List;

import oauthServer.model.User;

public interface UserService {

	public void add(User u);
	
	public User findByUsernameAndPassword(String username,String password);
	
	public List<User> findAll();
	
	public void delete(int id);
	
	public void update(User u);
}
