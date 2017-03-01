package oauthServer.model.tool;
import java.util.Set;

import oauthServer.util.OAuthUtils;

/**
request: 						
	 * 			parameters
	 * 				response_type : must be code
	 * 				client_id:  must exists
	 * 				redirect_uri: 	if all pass , redirect user to redirectView finally.
	 * 				scope: 	(optional)
	 * 				state: (recommended)  
	 * 
	 * 				custom-defined:(user_id,username,password)
*/

public class AuthRequestParams {
		
	private String response_type;
	private String client_id;
	private Set<String> scopes;	
	private String state;
	private String redirect_uri;
	
	private String user_id;
	private String username;
	private String password;

	public AuthRequestParams(String response_type, String client_id, Set<String> scopes, String state,
			String redirect_uri, String user_id, String username, String password) {
		super();
		this.response_type = response_type;
		this.client_id = client_id;
		this.scopes = scopes;
		this.state = state;
		this.redirect_uri = redirect_uri;
		this.user_id = user_id;
		this.username = username;
		this.password = password;
	}



	public String getResponse_type() {
		return response_type;
	}



	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}



	public String getClient_id() {
		return client_id;
	}



	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}



	public Set<String> getScopes() {
		return scopes;
	}



	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getRedirect_uri() {
		return redirect_uri;
	}



	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}



	public String getUser_id() {
		return user_id;
	}



	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

}
