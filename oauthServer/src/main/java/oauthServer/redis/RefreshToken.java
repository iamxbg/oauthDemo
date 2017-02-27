package oauthServer.redis;

import java.util.Set;

public class RefreshToken {
	
	private String client_id;
	private String user_id;
	private String expire_in;
	private String token;
	private Set<String> scopes;

	public RefreshToken() {
		// TODO Auto-generated constructor stub
	}


	public RefreshToken(String client_id, String user_id, String expire_in, String token, Set<String> scopes) {
		super();
		this.client_id = client_id;
		this.user_id = user_id;
		this.expire_in = expire_in;
		this.token = token;
		this.scopes = scopes;
	}


	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getExpire_in() {
		return expire_in;
	}

	public void setExpire_in(String expire_in) {
		this.expire_in = expire_in;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public Set<String> getScopes() {
		return scopes;
	}


	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	
	
	

}
