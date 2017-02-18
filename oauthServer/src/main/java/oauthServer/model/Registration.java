package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_regisration_list")
public class Registration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String app_name;
	private String client_id;
	private String client_secrect;
	private String authorization_endpoint;
	private String token_endpoint;
	private String redirection_endpoint;
	
	

	public Registration() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getClient_id() {
		return client_id;
	}



	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}



	public String getClient_secrect() {
		return client_secrect;
	}



	public void setClient_secrect(String client_secrect) {
		this.client_secrect = client_secrect;
	}



	public String getAuthorization_endpoint() {
		return authorization_endpoint;
	}



	public void setAuthorization_endpoint(String authorization_endpoint) {
		this.authorization_endpoint = authorization_endpoint;
	}



	public String getToken_endpoint() {
		return token_endpoint;
	}



	public void setToken_endpoint(String token_endpoint) {
		this.token_endpoint = token_endpoint;
	}



	public String getRedirection_endpoint() {
		return redirection_endpoint;
	}



	public void setRedirection_endpoint(String redirection_endpoint) {
		this.redirection_endpoint = redirection_endpoint;
	}



	public Registration(int id, String app_name, String client_id, String client_secrect, String authorization_endpoint,
			String token_endpoint, String redirection_endpoint) {
		super();
		this.id = id;
		this.app_name = app_name;
		this.client_id = client_id;
		this.client_secrect = client_secrect;
		this.authorization_endpoint = authorization_endpoint;
		this.token_endpoint = token_endpoint;
		this.redirection_endpoint = redirection_endpoint;
	}



	public String getApp_name() {
		return app_name;
	}



	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}







}
