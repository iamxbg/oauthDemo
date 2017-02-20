package oauthClient.util;

public interface OAuthClientConstants {
		
	public static String CLIENT_ID="chunyuyishen";
	public static String CLIENT_SECRECT="123456";
	public static String TOKEN_ENDPOINT="http://localhost:8082/oauthServer/oauth/token";
	public static String AUTHZ_ENDPOINT="http://localhost:8082/oauthServer/oauth/authorizeView";
	public static String REDIRECT_URI="http://localhost:8081/oauthClient/oauth/authzResult";
}
