package oauthServer.util;

public class OAuthAuthzParams {

		private String client_id;
		private String redirect_uri;
		private String scope;
		private String responseType;
		
		private String user_id;
		private String username;
		private String password;

		
		
		public OAuthAuthzParams() {
			// TODO Auto-generated constructor stub
		}

		public String getClient_id() {
			return client_id;
		}


		public void setClient_id(String client_id) {
			this.client_id = client_id;
		}

		public String getRedirect_uri() {
			return redirect_uri;
		}

		public void setRedirect_uri(String redirect_uri) {
			this.redirect_uri = redirect_uri;
		}


		public String getScope() {
			return scope;
		}


		public void setScope(String scope) {
			this.scope = scope;
		}


		public String getResponseType() {
			return responseType;
		}


		public void setResponseType(String responseType) {
			this.responseType = responseType;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		

		
		//what about JWT
		
		public void validate() throws OAuthParamsException{
			
		}
}
