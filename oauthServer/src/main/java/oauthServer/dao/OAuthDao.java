package oauthServer.dao;

public interface OAuthDao {
	
	public void saveAuthzrizatioCode(String client_id,String authzCode);
	
	public void saveAccessToken(String client_id,String accessToken);
}
