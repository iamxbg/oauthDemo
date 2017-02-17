package oauthServer.dao;

import java.util.List;

public interface CommonDao <T>{
	
	public List<T> findAll();
	
	public T findById(int id);
	
	public void add(T t);
	
	public void delete(T t);
	
	public void update(T t);
	
	
}
