package oauthResourceServer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import oauthResourceServer.dao.TokenDao;
import oauthResourceServer.service.TokenService;

public class TokenServiceImpl implements TokenService {
	
	private String redisServiceName ="faker";

	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public boolean isTokenExists(String token) {
		return tokenDao.isExists(redisServiceName, token);
		// TODO Auto-generated method stub
	}
}
