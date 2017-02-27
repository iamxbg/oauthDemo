package oauthServer.dao;

import java.util.List;

public interface CommonDao <T>{
	
	public List<T> findAll();
	
	public T findById(int id);
	
	public int add(T t);
	
	public void delete(T t);
	
	public void update(T t);
	
	
	public void deleteById(int id);
	
}
