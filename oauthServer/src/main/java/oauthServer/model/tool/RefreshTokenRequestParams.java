package oauthServer.model.tool;
import java.util.Set;

import oauthServer.redis.RefreshToken;
import oauthServer.util.OAuthUtils;

public class RefreshTokenRequestParams {

	private RefreshToken refreshToken;
	
	private String client_id;
	private String expires_in;
	private String user_id;
	private Set<String> scopes;
	
	public RefreshTokenRequestParams(RefreshToken refreshToken, String client_id, String expires_in, String user_id,
			Set<String> scopes) {
		super();
		this.refreshToken = refreshToken;
		this.client_id = client_id;
		this.expires_in = expires_in;
		this.user_id = user_id;
		this.scopes = scopes;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	




	
}
