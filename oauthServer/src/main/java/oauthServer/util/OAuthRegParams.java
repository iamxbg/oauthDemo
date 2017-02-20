package oauthServer.util;

public class OAuthRegParams {
	
	private String client_id;
	private String name="";
	private String url="";
	private String description="";
	private String registrationEndpoint="";
	
	
	public OAuthRegParams() {
		// TODO Auto-generated constructor stub
	}


	public String getClient_id() {
		return client_id;
	}


	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRegistrationEndpoint() {
		return registrationEndpoint;
	}


	public void setRegistrationEndpoint(String registrationEndpoint) {
		this.registrationEndpoint = registrationEndpoint;
	}
	
	
	
	
}
