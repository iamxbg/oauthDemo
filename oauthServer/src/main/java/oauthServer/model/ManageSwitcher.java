package oauthServer.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="manage_switcher_t")
public class ManageSwitcher {

	public ManageSwitcher() {
		// TODO Auto-generated constructor stub
	}

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int id;
	private String name;
	private char is_on;
	
	
	public ManageSwitcher(String name, char is_on) {
		super();
		this.name = name;
		this.is_on = is_on;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getIs_on() {
		return is_on;
	}
	public void setIs_on(char is_on) {
		this.is_on = is_on;
	}

	
	
}
