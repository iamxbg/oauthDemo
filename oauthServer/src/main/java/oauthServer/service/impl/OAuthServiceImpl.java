package oauthServer.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.validator.AuthorizationCodeValidator;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import oauthServer.dao.AuthorizationCodeDao;
import oauthServer.dao.RefreshTokenDao;
import oauthServer.dao.TokenDao;
import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;
import oauthServer.redis.RefreshToken;
import oauthServer.service.OAuthService;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;

@Service
public class OAuthServiceImpl implements OAuthService{

	@Autowired
	private TokenDao tokenDao;
	@Autowired
	private AuthorizationCodeDao codeDao;
	@Autowired
	private RefreshTokenDao rtDao;
	

	@Override
	public boolean isAccessTokenExists(String token) {
		// TODO Auto-generated method stub
		return tokenDao.isTokenExists(token);
	}


	@Override
	public boolean isRefreshTokenExists(String key) {
		// TODO Auto-generated method stub
		return rtDao.isRefreshTokenExists(key);
	}


	@Override
	public void addAuthorizationCode(AuthorizationCode code) {
		// TODO Auto-generated method stub
		 codeDao.saveAuthorizationCode(code);;
	}

	@Override
	public void addAccessToken(AccessToken token) {
		// TODO Auto-generated method stub
		tokenDao.saveAccessToken(token);
	}


	@Override
	public void addRefreshToken(RefreshToken token) {
		// TODO Auto-generated method stub
		rtDao.saveRefreshToken(token);
	}
	

	@Override
	public boolean isAuthorizationCodeExist(String key) {
		// TODO Auto-generated method stub
		return codeDao.isAuthorizationCodeExists(key);
	}

	@Override
	public AccessToken getAccessToken(String key) {
		// TODO Auto-generated method stub
		return tokenDao.findByKey(key);
	}

	@Override
	public AuthorizationCode getAuthorizationCode(String key) {
		// TODO Auto-generated method stub
		return codeDao.findByKey(key);
	}

	@Override
	public RefreshToken getRefreshToken(String key) {
		// TODO Auto-generated method stub
		return rtDao.findByKey(key);
	}




	

	

	


}
