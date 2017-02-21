package oauthServer.redis;

import java.util.List;

public class AccessToken {

		private String client_id;
		private String user_id;
		private String scope;
		private String expire_in;
		private String token;
		
		public AccessToken() {
			// TODO Auto-generated constructor stub
		}

		public AccessToken(String client_id, String user_id, String scope, String expire_in, String token) {
			super();
			this.client_id = client_id;
			this.user_id = user_id;
			this.scope = scope;
			this.expire_in = expire_in;
			this.token = token;
		}

		public String getClient_id() {
			return client_id;
		}

		public void setClient_id(String client_id) {
			this.client_id = client_id;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public String getExpire_in() {
			return expire_in;
		}

		public void setExpire_in(String expire_in) {
			this.expire_in = expire_in;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getValue(){
			return new StringBuilder(client_id)
						.append(":").append(user_id)
						.append(":").append(scope)
						.append(":").append(token).toString();
		}
		
}
