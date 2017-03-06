package oauthServer.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import oauthServer.model.ManageSwitcher;

public interface ManageSwitcherDao {

	
	public ManageSwitcher findByName(String name);
	
	public void switchStatus(String name);
	
	public List<ManageSwitcher> findAll();
}
