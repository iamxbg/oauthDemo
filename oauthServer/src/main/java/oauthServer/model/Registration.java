package oauthServer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="registration_t")
public class Registration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String client_id;
	private String client_secrect;
	private String description;
	private Date create_time;
	private String redirection_uri;
	private String receive_token_uri;
	private String receive_authz_code_uri;
	private char is_client_auth_enabled='N';
	private char is_server_auth_enabled='N';
	private char del_flag='N';

	public Registration(String name, String client_id, String client_secrect, String description,
			String redirection_uri,  String receive_token_uri, String receive_authz_code_uri) {
		super();
		this.name = name;
		this.client_id = client_id;
		this.client_secrect = client_secrect;
		this.description = description;
		this.redirection_uri = redirection_uri;
		this.receive_token_uri = receive_token_uri;
		this.receive_authz_code_uri = receive_authz_code_uri;
	}



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



	public Date getCreate_time() {
		return create_time;
	}



	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}



	public String getRedirection_uri() {
		return redirection_uri;
	}



	public void setRedirection_uri(String redirection_uri) {
		this.redirection_uri = redirection_uri;
	}


	public String getReceive_token_uri() {
		return receive_token_uri;
	}



	public void setReceive_token_uri(String receive_token_uri) {
		this.receive_token_uri = receive_token_uri;
	}



	public String getReceive_authz_code_uri() {
		return receive_authz_code_uri;
	}



	public void setReceive_authz_code_uri(String receive_authz_code_uri) {
		this.receive_authz_code_uri = receive_authz_code_uri;
	}






	public char getDel_flag() {
		return del_flag;
	}



	public void setDel_flag(char del_flag) {
		this.del_flag = del_flag;
	}



	public char getIs_client_auth_enabled() {
		return is_client_auth_enabled;
	}



	public void setIs_client_auth_enabled(char is_client_auth_enabled) {
		this.is_client_auth_enabled = is_client_auth_enabled;
	}



	public char getIs_server_auth_enabled() {
		return is_server_auth_enabled;
	}



	public void setIs_server_auth_enabled(char is_server_auth_enabled) {
		this.is_server_auth_enabled = is_server_auth_enabled;
	}








}
