package oauthServer.web;
//package oauthServer.web.openid;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.charset.Charset;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.http.Header;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.HttpRequestHandler;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
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
//import oauthServer.util.HttpClientUtil;
//import oauthServer.util.OAuthUtils;
//import static oauthServer.service.OAuthService.*;
//
//@Controller
//@RequestMapping(path="/openid")
//public class OpenIDController {
//	
//	private static Logger logger=LogManager.getLogger();
//	
//	@Autowired
//	private HttpClientUtil httpClientUtil;
//	@Autowired
//	private OAuthService oauthService;
//	@Autowired
//	private AccountService accService;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private ServiceService serService;
//	@Autowired
//	private ClientService cliService;
//
//
//	public OpenIDController() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	@RequestMapping(path="/authView",method=RequestMethod.GET)
//	public ModelAndView openIdAuthView(HttpServletRequest req,ModelAndView mav){
//		
//		mav.setView(new RedirectView("OPENID_AUTH_VIEW_PAGE"));
//		
//		return mav;
//	}
//	
//	@RequestMapping(path="/authorize",method=RequestMethod.POST)
//	public ModelAndView accountAuthorize(@RequestParam("tel") String tel
//											,@RequestParam("password") String password
//											,@RequestParam("service_id") String service_id
//											,@RequestParam("client_id") String client_id
//											,@RequestParam(name="user_id" ,required=false) Integer user_id
//											,HttpServletRequest req){
//		
//		
//	
//		logger.info("/authorize: username: "+tel+" password:"+password);
//		
//		try{
//			boolean authPass=false;
//			if("tf02".equals(service_id)){
//			
//				FamilyAccount fa=accService.getFamilyAccountByTelAndPassword(tel, password);
//				if(fa!=null){
//					 List<FamilyMembers> fms=accService.findMembersInfoByFamilyId(fa.getId());
//					 if(user_id!=null){
//						for(FamilyMembers fm:fms) {
//							if(Integer.valueOf(fm.getMemberId())==user_id){
//								authPass=true;
//							}
//						}
//					 }
//				}else authPass=true;
//
//				
//			}
//			
//			if(authPass){
//				// generate access_token
//				//send it to client
//				//if client received and get status 200,then redirect user to some view!
//				String access_token=OAuthUtils.generateUUID();
//				
//				CloseableHttpClient client=HttpClients.custom()
//								.setConnectionManager(httpClientUtil.getManager())
//								.build();
//				
//				String receiveAuthCode="FAKE_AUTH_CODE_URIs";
//				
//				HttpGet get=new HttpGet(new URI(receiveAuthCode));
//				
//				CloseableHttpResponse response=client.execute(get);
//				if(response.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){
//					//success send authCode, redirect to somewhere
//				}else{
//					// send error infomation to authView
//				}
//				
//			}else{
//				return null;
//			}
//			
//		}catch(Exception e){
//			
//		}
//		
//		//remote calling account Service
//		
//		return  null;
//	}
//
//	/**
//	 * use code to get openId
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(path="/openId/code={code}&state={state}",method=RequestMethod.GET)
//	public ResponseEntity<Map<String, String>> useTokenForOpenId(@PathVariable("code") String code,
//								@PathVariable("state") String state,HttpServletRequest req){
//		
//			logger.info("code:"+code+" state:"+state);
//			//first , check if code is valid
//			
//			Map<String, String> map=new HashMap<>();
//			
//			
//				
//				String scuKey=code.substring(code.indexOf('#')+1, code.lastIndexOf('#'));
//				
//				Map<String, String> result=oauthService.getOpenIdAuthToken(scuKey);
//				if(result!=null){
//					String authCode=code.substring(code.lastIndexOf('#')+1);
//					
//					
//					if(result.get(REDIS_FIELD_CODE).equals(authCode)){
//						// validate success
//						
//						//check if openId is exists, and generation is OK
//						
//						Map<String, String> openIdMap=oauthService.getOpenId(scuKey);
//						String openId=null;
//						if(openIdMap==null){
//							//generate and save in database
//							String[] subKeyAry=scuKey.split(":");
//							openId=oauthService.addOpenId(Integer.parseInt(subKeyAry[0]),
//									Integer.parseInt(subKeyAry[1]),
//									Integer.parseInt(subKeyAry[2]));
//							
//						}else{
//							openId=openIdMap.get(REDIS_KEY_OPENID);
//						}
//						
//						//if openId is not exists, generate and send to client
//						
//						// else if openId exists, send to user
//						
//					}else{
//						//validate failed, fatal!!!
//					}
//				}else{
//					//not found , may be auth code is expired 
//				}
//
//			return null;
//		
//	}
//
//	
//	/**
//	 *  需呀返回auth_code,state
//	 * @param auth_code
//	 * @param receive_authcode_uri
//	 * @param state
//	 * @return
//	 * @throws URISyntaxException
//	 * @throws ClientProtocolException
//	 * @throws IOException
//	 */
//	public CloseableHttpResponse sendAuthzCodeToClient(String auth_code,String receive_authcode_uri,String state) throws URISyntaxException, ClientProtocolException, IOException{
//		// add scope utils later!
//		
//		List<Header> http_headers=new ArrayList<>();
//
//			Header header=new BasicHeader(STATE,state);
//			http_headers.add(header);
//			
//		CloseableHttpClient client=HttpClients.custom()
//										.setConnectionManager(httpClientUtil.getManager())
//										.setDefaultHeaders(http_headers).build();
//		
//		HttpPost post=new HttpPost(new URI(receive_authcode_uri));
//			
//			post.setHeader("content-type", "application/json;charset=utf-8");
//			
//			Map<String, String> result=new HashMap<>();
//				result.put(CODE, auth_code);
//				result.put(STATE, state);
//
//			
//			JSONObject jsonObj=new JSONObject(result);
//			
//			post.setEntity(new StringEntity(jsonObj.toString(), Charset.forName("UTF-8")));
//		
//			return client.execute(post);
//
//	}
//	
//	/**
//	 *  User login with openId
//	 * @param service_id
//	 * @param client_id
//	 * @param openid
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(path="/login/serice_id={service_id}&client_id={client_id}&openid={openid}")
//	public ResponseEntity loginWithOpenid(@PathVariable("service_id") String service_id,
//										@PathVariable("client_id") String client_id,
//										@PathVariable("openid") String openid,
//										HttpServletRequest req){
//		logger.info("service_id:"+service_id+" client_id:"+client_id+" openid:"+openid);
//		
//		
//		Map<String, String> userInfo=oauthService.getUserInfoByOpenId(openid);
//		if(userInfo!=null){
//			return new ResponseEntity<>(userInfo, HttpStatus.OK);
//		}else{
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//			
//		
//		
//	}
//	
//
//}
