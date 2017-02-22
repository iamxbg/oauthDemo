package oauthServer.service.impl;

import java.util.List;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.validator.AuthorizationCodeValidator;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import oauthServer.dao.OAuthDao;
import oauthServer.redis.AccessToken;
import oauthServer.redis.AuthorizationCode;
import oauthServer.service.OAuthService;
import oauthServer.util.OAuthConstants;
import oauthServer.util.OAuthUtils;

@Service
public class OAuthServiceImpl implements OAuthService{

	@Autowired
	private OAuthDao oauthDao;
	


	@Override
	public String addAuthzCode(String client_id, String user_id, String scope) {
		// TODO Auto-generated method stub

		AuthorizationCode code=new AuthorizationCode(client_id, user_id, scope, OAuthConstants.AUTHORIZATION_CODE_EXPIRE_IN, OAuthUtils.generateAuthorizationCode());
		String value= code.getValue();
		
		oauthDao.saveAuthzrizatioCode(client_id, value);
		
		return value;
	}

	@Override
	public String addAccessToken(String client_id, String user_id, String scope) {
		// TODO Auto-generated method stub
		AccessToken token=new AccessToken(client_id, user_id, scope, OAuthConstants.ACCESS_TOKEN_TOKEN_EXPIRE_IN, OAuthUtils.generateToken());
		String value=token.getValue();
		
		oauthDao.saveAccessToken(client_id, value);
		
		return value;
	}

	@Override
	public boolean isAccessTokenExists(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthzCodeExist(String code) {
		// TODO Auto-generated method stub
		return false;
	}



}
