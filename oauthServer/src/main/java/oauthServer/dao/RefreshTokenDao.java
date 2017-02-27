package oauthServer.dao;

import oauthServer.redis.RefreshToken;

public interface RefreshTokenDao {

		
		public void saveRefreshToken(RefreshToken token);
		
		public RefreshToken findByKey(String key);
		
		public boolean isRefreshTokenExists(String token);
		


}
