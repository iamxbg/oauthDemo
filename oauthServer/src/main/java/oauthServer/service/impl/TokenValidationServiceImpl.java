package oauthServer.service.impl;

import static oauthServer.service.OAuthService.REDIS_FIELD_EXPIRES_AT;
import static oauthServer.service.OAuthService.REDIS_FIELD_TOKEN;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oauthServer.service.OAuthService;
import oauthServer.service.TokenValidationService;

@Service
public class TokenValidationServiceImpl implements TokenValidationService {
	
	private static Logger logger=LogManager.getLogger();
	
	@Autowired
	private OAuthService oauthService;

	public TokenValidationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> validateAccessToken(String access_token) {
		// TODO Auto-generated method stub

		//String access_token=req.getHeader("access_token");

		logger.info("access_token:"+access_token);
		
		Map<String, Object> map=new HashMap<>();
		
		if(access_token!=null && !"".equals(access_token)){
			
			int index=access_token.indexOf('#');
			if(index==-1){
				map.put(ERROR_DESCRIPTION, "wrong token format!");
				map.put(IS_SUCCESS, false);
				return map;
			}
			String key_part=access_token.substring(0, index);
			String token_part=access_token.substring(index+1);
			
			Map<String, String> accessToken=oauthService.getAccessToken(key_part);

			if(accessToken!=null){
				
					//check if access token is expires

					long expiresAt=Long.parseLong(accessToken.get(REDIS_FIELD_EXPIRES_AT));
					Date expires_date=new Date(expiresAt);
					if(new Date().after(expires_date)){
						// if access_token is expired, please use refresh_token to get a new token
						//resp.getWriter().write);
						
						
							map.put(IS_SUCCESS, false);
							map.put(ERROR_DESCRIPTION, "WARNING ! tokn expired! Please use refresh token to get new one");

					}else{
						// check authCode
						if(accessToken.get(REDIS_FIELD_TOKEN).equals(token_part)){
							//success , go deeper
							map.put(IS_SUCCESS, true);
						}else{
							// not equal , FATAL!
	
							//pw.write( );
							//pw.write("	");
							//.write("	");
							
							map.put(ERROR_DESCRIPTION, "FATAL ERROR ! token content valid fail! May be algorithm changed, please test with re_authorize.If still get this error , please contact resource provider for help!  —_>— ");
							map.put(IS_SUCCESS, false);
						}
					}

			}else{
				//token not exist! may be refresh token expires too!
				//resp.getWriter().write( );
				map.put(ERROR_DESCRIPTION, "WARNING ! token not found,check if is refresh_token  expired too ?!");
				map.put(IS_SUCCESS, false) ;
			}
		}else{
			//resp.getWriter().write( );
			map.put(IS_SUCCESS, false);
			map.put(ERROR_DESCRIPTION, "ERROR ! neek contain \"access_token\" in request headers!");
		}
		
		return map;
		
	}



	

}
