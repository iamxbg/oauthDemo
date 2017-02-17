package oauthServer.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.Registration;
import oauthServer.service.RegistrationService;


@Controller
@RequestMapping(path="/admin")
public class AdminController {

	@Autowired
	private RegistrationService rService;
	
	private static Logger logger=org.apache.log4j.LogManager.getLogger(AdminController.class);
	
	@RequestMapping(path="/getRegistrationView")
	public ModelAndView getRegistrationListView(ModelAndView mav){
		
		//logger.log(Level.INFO, "getRegistrationView");
		
		logger.log(Priority.INFO, "sdf");
		
		List<Registration> rList=rService.getRegistrationList();
		
		mav.addObject("appList", rList);
		mav.setViewName("/app_registration.jsp");
		return mav;
		
	}

}
