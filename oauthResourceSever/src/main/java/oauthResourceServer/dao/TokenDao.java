package oauthResourceServer.dao;

public interface TokenDao {

	public boolean isExists(String redisServiceName,String token);
}
