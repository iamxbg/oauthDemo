package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_registration_list")
public class Registration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String client_id;
	private String client_secrect;
	private String authorization_uri;
	private String token_uri;
	private String redirection_uri;
	
	

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



	public String getAuthorization_uri() {
		return authorization_uri;
	}



	public void setAuthorization_uri(String authorization_uri) {
		this.authorization_uri = authorization_uri;
	}



	public String getToken_uri() {
		return token_uri;
	}



	public void setToken_uri(String token_uri) {
		this.token_uri = token_uri;
	}



	public String getRedirection_uri() {
		return redirection_uri;
	}



	public void setRedirection_uri(String redirection_uri) {
		this.redirection_uri = redirection_uri;
	}

}
