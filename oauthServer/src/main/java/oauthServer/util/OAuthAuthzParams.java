package oauthServer.util;

public class OAuthAuthzParams {

		private String client_id;
		private String client_secrect;
		private String redirect_uri;
		private String authzEndpoint;
		private String refreshToken;
		private String scope;
		private String responseType;
		private String idToken;
		private String idTokenValid;
		
		
		public OAuthAuthzParams() {
			// TODO Auto-generated constructor stub
		}


		public String getClient_id() {
			return client_id;
		}


		public void setClient_id(String client_id) {
			this.client_id = client_id;
		}


		public String getClient_secrect() {
			return client_secrect;
		}


		public void setClient_secrect(String client_secrect) {
			this.client_secrect = client_secrect;
		}


		public String getRedirect_uri() {
			return redirect_uri;
		}


		public void setRedirect_uri(String redirect_uri) {
			this.redirect_uri = redirect_uri;
		}


		public String getAuthzEndpoint() {
			return authzEndpoint;
		}


		public void setAuthzEndpoint(String authzEndpoint) {
			this.authzEndpoint = authzEndpoint;
		}


		public String getRefreshToken() {
			return refreshToken;
		}


		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
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


		public String getIdToken() {
			return idToken;
		}


		public void setIdToken(String idToken) {
			this.idToken = idToken;
		}


		public String getIdTokenValid() {
			return idTokenValid;
		}


		public void setIdTokenValid(String idTokenValid) {
			this.idTokenValid = idTokenValid;
		}
		
		
		
		
		//what about JWT
}
