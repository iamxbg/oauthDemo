package oauthServer.util;

public interface OAuthConstants {
	
	 final String scheme_SSL="https";
	 final String scheme_plain="http";
	 final String hostname="127.0.0.1";
	 final String app_name="oauthServer";
	 final String port="8082";
	 final String ssl_port="8443";
	 final String auth_type_server_path="ssa";
	 final String auth_type_client_path="csa";
	 final String auth_view_uri="authView";
	 final String acesss_token_uri="token";
	 final String authorize_uri="authorize";
	 final String refresh_token_uri="refresh_token";
	 final String auth_page_uri="authorization.jsp";
	 
		public static String AUTHORIZATION_CODE_EXPIRES_IN="3600";
		public static String ACCESS_TOKEN_CODE_EXPIRES_IN="3600";
		public static String ACCESS_TOKEN_TOKEN_EXPIRES_IN="3600";
		public static String REFRESH_TOKEN_EXPIRES_IN="1000000";
		
		public String AUTHORIZATION_PAGE=new StringBuilder(scheme_SSL)
							.append("://").append(hostname)
							.append(":").append(ssl_port)
							.append("/").append(app_name)
							.append("/").append(auth_page_uri).toString();
		
		//-----------------------server-side-authorize							
		
		
		public static String SSA_AUTHORIZTION_VIEW_URI=new StringBuilder(scheme_plain)
				.append("://").append(hostname)
				.append(":").append(port)
				.append("/").append(app_name)
				.append("/").append(auth_type_server_path)
				.append("/").append(auth_view_uri).toString();
		
		public static String SSA_TOKEN_URI=new StringBuilder(scheme_SSL)
								.append("://").append(hostname)
								.append(":").append(ssl_port)
								.append("/").append(app_name)
								.append("/").append(auth_type_server_path)
								.append("/").append(acesss_token_uri).toString();
		
		public static String SSA_AUTHORIZE_URI=new StringBuilder(scheme_SSL)
									.append("://").append(hostname)
									.append("/").append(app_name)
									.append(":").append(ssl_port)
									.append("/").append(auth_type_server_path)
									.append("/").append(authorize_uri).toString();
		
		public static String SSA_REFRESH_TOKEN_URI=new StringBuilder(scheme_SSL)
								.append("://").append(hostname)
								.append("/").append(app_name)
								.append(":").append(ssl_port)
								.append("/").append(auth_type_server_path)
								.append("/").append(refresh_token_uri).toString();
		
		//-----------------------client-side-authorize
		
		public static String CSA_AUTHORIZTION_VIEW_URI=new StringBuilder(scheme_SSL)
				.append("://").append(hostname)
				.append("/").append(app_name)
				.append(":").append(ssl_port)
				.append("/").append(auth_type_client_path)
				.append("/").append(auth_view_uri).toString();
		
		public static String CSA_AUTHORIZE_URI=new StringBuilder(scheme_SSL)
				.append("://").append(hostname)
				.append("/").append(app_name)
				.append(":").append(ssl_port)
				.append("/").append(auth_type_client_path)
				.append("/").append(authorize_uri).toString();


}
