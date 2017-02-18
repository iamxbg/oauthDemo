package oauthServer.service;

import java.util.List;

import oauthServer.model.Registration;

public interface OAuthService {
	
	public static String AUTHORIZATION_URI="fake_authorization_uri";
	public static String TOKEN_URI="fake_token_uri";
	
	public List<Registration> getRegistrationList();
	
	public void doRegistration(Registration r);
	
	
	public String findRedirectLocationUriByClient_id(String client_id);
	
}
