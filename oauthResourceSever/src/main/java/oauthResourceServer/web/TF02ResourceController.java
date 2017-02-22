package oauthResourceServer.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oauthResourceServer.service.TokenService;

@RequestMapping(path="/open")
public class TF02ResourceController {

	@Autowired
	private TokenService tokenService;
		
	@SuppressWarnings("rawtypes")
	public ResponseEntity tf02Test(HttpServletRequest req,HttpServletResponse resp){
		
		try {
			OAuthAccessResourceRequest request=new OAuthAccessResourceRequest(req, new TokenType[]{TokenType.BEARER});
		
					String accessToken=request.getAccessToken();
					
					if(accessToken!=null){
						if(tokenService.isTokenExists(accessToken)){
							// Invoke workhorse	
							return new ResponseEntity<>(HttpStatus.OK);
						}
					}else{
						//token is not exist or expired
						Map<String, String> msg=new HashMap<>();
							msg.put("errMsg", "token不存在或已經過期!");
						return new ResponseEntity<>(msg,HttpStatus.NON_AUTHORITATIVE_INFORMATION);
					}

		} catch (OAuthSystemException | OAuthProblemException e) {
			// TODO Auto-generated catch block
			Map<String, String> msg=new HashMap<>();
				msg.put("errMsg", e.getMessage());
			return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
