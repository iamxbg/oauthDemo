package oauthServer.service;

import java.util.Map;

public interface TokenValidationService {

	public static String IS_SUCCESS="is_success";
	public static String ERROR_DESCRIPTION="error_description";
	
	/**
	 * this access token is user submit, the whole, not vlaue part!
	 * @param access_token
	 * @return
	 */
	public Map<String, Object> validateAccessToken(String access_token);
	
}
