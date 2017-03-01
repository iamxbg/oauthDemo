package oauthServer.model.tool;
import org.apache.oltu.oauth2.common.OAuth;

public class AuthViewRequestParams {
	
	private String client_id;
	private String redirect_uri;
	private String state;
	private String response_type;  //code or token


	public AuthViewRequestParams(String client_id, String redirect_uri, String state, String response_type) {
		super();
		this.client_id = client_id;
		this.redirect_uri = redirect_uri;
		this.state = state;
		this.response_type = response_type;
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


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getResponse_type() {
		return response_type;
	}


	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	
	

}
