package oauthServer.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.Registration;
import oauthServer.service.OAuthService;


@Controller
@RequestMapping(path="/admin")
public class AdminController {

	@Autowired
	private OAuthService rService;
	
	private static Logger logger=org.apache.log4j.LogManager.getLogger(AdminController.class);
	
	@RequestMapping(path="/getRegistrationView")
	public ModelAndView getRegistrationListView(ModelAndView mav){
		
		//logger.log(Level.INFO, "getRegistrationView");
		
		logger.log(Priority.INFO, "sdf");
		
		List<Registration> rList=rService.getRegistrationList();
		
		logger.log(org.apache.log4j.Level.INFO, "count:"+rList.size());
		
		mav.addObject("appList", rList);
		mav.setViewName("/app_registration.jsp");
		return mav;
		
	}
	
	@RequestMapping(path="/doRegistration")
	public ModelAndView doRegistration(@RequestParam String app_name,
			@RequestParam String client_id,
			@RequestParam String client_secrect,
			@RequestParam String redirection_endpoint
			,ModelAndView mav){
		logger.log(org.apache.log4j.Level.INFO, "+do registration");
		
		
		Registration r=new Registration();
			r.setApp_name(app_name);
			r.setClient_id(client_id);
			r.setClient_secrect(client_secrect);
			r.setRedirection_endpoint(redirection_endpoint);
		
		r.setAuthorization_endpoint(OAuthService.AUTHORIZATION_URI);
		r.setToken_endpoint(OAuthService.TOKEN_URI);
		
		rService.doRegistration(r);
		
		List<Registration> rList=rService.getRegistrationList();
		mav.addObject("appList",rList);
		mav.setViewName("/app_registration.jsp");
		return mav;
	}

}
