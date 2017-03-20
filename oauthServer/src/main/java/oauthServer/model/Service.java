package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_t")
public class Service {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String service_id;
	private String name;
	private String description;
	private String profile;
	private String authentication_url;
	private int del_flag=0;
	
	public Service() {
		// TODO Auto-generated constructor stub
	}

	public Service(String service_id, String name, String description, String profile, String authentication_url) {
		super();
		this.service_id = service_id;
		this.name = name;
		this.description = description;
		this.profile = profile;
		this.authentication_url = authentication_url;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

	public String getAuthentication_url() {
		return authentication_url;
	}

	public void setAuthentication_url(String authentication_url) {
		this.authentication_url = authentication_url;
	}

}
