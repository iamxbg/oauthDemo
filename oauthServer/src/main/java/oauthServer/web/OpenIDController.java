//package oauthServer.web;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import com.foxconn.model.FamilyAccount;
//import com.foxconn.model.FamilyMembers;
//import com.foxconn.model.MembersInfo;
//import com.foxconn.service.AccountService;
//import static oauthServer.util.OAuthConstants.*;
//import oauthServer.exception.OAuthAuthzException;
//import oauthServer.exception.OpenIdException;
//import oauthServer.model.Client;
//import oauthServer.model.Service;
//import oauthServer.model.User;
//import oauthServer.service.ClientService;
//import oauthServer.service.OAuthService;
//import oauthServer.service.ServiceService;
//import oauthServer.service.UserService;
//import oauthServer.service.app.TF02ResourceService;
//import oauthServer.util.OAuthUtils;
//
//@Controller
//@RequestMapping(path="/openid")
//public class OpenIDController implements TF02ResourceService{
//	
//	private static Logger logger=LogManager.getLogger();
//	
//
//	@Autowired
//	private OAuthService oauthService;
//
//	@Autowired
//	private AccountService accService;
//	
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private ServiceService serService;
//	@Autowired
//	private ClientService cliService;
//
//	public OpenIDController() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@RequestMapping(path="/me/openid={openId}")
//	public ModelAndView loginWithOpenId(@PathVariable("openId") String openId,HttpServletRequest req){
//		//validate use openId to connect
//		
//		logger.info("openId:"+openId);
//		
//		
//		return null;
//	}
//	
//	@RequestMapping(path="/authorize")
//	public ModelAndView authorizeLoginWithOpenId(@RequestParam("username") String username
//				,@RequestParam("password") String password
//				,@RequestParam("auth_type") String auth_type){
//		
//		logger.info("openId_authorize");
//		
//		//validate
//		
//		//if success return with token, state
//		
//		//else return error information
//		return null;
//	}
//	
//	
//	@RequestMapping(path="/token")
//	public ResponseEntity<Map<String, String>> useTokenForOpenId(@RequestParam("access_token") String access_token
//																,@RequestParam("service_id") String service_id
//																,@RequestParam("client_id") String client_id
//																,@RequestParam("user_id") String user_id
//																,HttpServletRequest req){
//		
//		try{
//
//		Service service=serService.findByService_id(service_id);
//		Client client=cliService.findByClient_id(client_id);
//		
//		if(service==null) throw new OAuthAuthzException(ERROR_SERVICE_NOT_EXISTS);
//		if(client==null) throw new OAuthAuthzException(ERROR_CLIENT_NOT_EXISTS);
//		
//		int service_id_int=service.getId();
//		int client_id_int=client.getId();
//		int user_id_int=Integer.parseInt(req.getParameter(USER_ID));
//
//		//validate token in redis
//		
//		String openidToken=oauthService.getOpenIdAuthToken(service_id_int, client_id_int, user_id_int);
//		
//		
//				if(openidToken==null){
//					throw new OpenIdException(ERROR_ExPIRED);
//				}else if(!openidToken.equals(access_token)){
//					throw new OpenIdException(ERROR_AUTHORIZE_FAIL);
//				}else{
//					
//					User user=userService.findByUid(user_id_int);
//					if(user!=null) {
//						if(user.getOpenid()==null){
//							
//						}else{
//							
//						}
//						
//					}else{
//						
//					}
//					String openId=UUID.randomUUID().toString();
//					
//					Map<String, String> result=new HashMap<>();
//						result.put("openId", openId);
//	
//					return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
//				}	
//				
//		}catch(OpenIdException | OAuthAuthzException e){
//			Map<String, String> result=new HashMap<>();
//				result.put(ERROR_DESCRIPTION,e.getMessage());
//				
//			return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
//		}
//		
//	}
//
//
//	@Override
//	public FamilyAccount findFamilyAccoutByTelAndPassword(String tel, String password) throws NoSuchAlgorithmException {
//		// TODO Auto-generated method stub
//		return accService.getFamilyAccountByTelAndPassword(tel, password);
//	}
//
//	@Override
//	public List<FamilyMembers> findMemebersInfoByFamilyId(int familyId) {
//		// TODO Auto-generated method stub
//		return accService.findMembersInfoByFamilyId(familyId);
//	}
//
//	@Override
//	public MembersInfo findMembersInfoById(int id) {
//		// TODO Auto-generated method stub
//		return  accService.getMemberInfoById(id);
//	}
//
//	
//
//}
