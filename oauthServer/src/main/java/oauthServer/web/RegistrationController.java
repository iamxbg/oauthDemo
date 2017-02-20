package oauthServer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.Registration;
import oauthServer.service.RegistrationService;


@Controller
@RequestMapping(path="/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService rService;
	
	private static Logger logger=Logger.getLogger(RegistrationController.class);
	
	@RequestMapping(path="")
	public ModelAndView findAll(ModelAndView mav){
		
		//logger.log(Level.INFO, "getRegistrationView");
		
		logger.log(Priority.INFO, "sdf");
		
		List<Registration> rList=rService.findAll();
		
		logger.log(org.apache.log4j.Level.INFO, "count:"+rList.size());
		
		mav.addObject("appList", rList);
		mav.setViewName("/registration.jsp");
		
		return mav;
		//return new ResponseEntity<List<Registration>>(rList, HttpStatus.OK);
		
	}
	
	@RequestMapping(path="/add")
	public ModelAndView add(@RequestParam String name,
			@RequestParam String client_id,
			@RequestParam String client_secrect,
			@RequestParam String description,
			@RequestParam String redirection_uri,
			@RequestParam String response_type,
			ModelAndView mav){
		
			logger.log(org.apache.log4j.Level.INFO, "+do registration");

			Registration r=new Registration();
				r.setClient_id(client_id);
				r.setClient_secrect(client_secrect);
				r.setDescription(description);
				r.setName(name);
				r.setRedirection_uri(redirection_uri);
				r.setResponse_type(response_type);

			rService.add(r);
			
			List<Registration> rList=rService.findAll();
			
			//mav.addObject(rList);
			mav.setViewName("/registration");
			
			return mav;
			//return new ResponseEntity<List<Registration>>(rList, HttpStatus.OK);
	}
	
	
	@RequestMapping(path="/del/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id,ModelAndView mav){
		
		logger.log(Level.INFO, "delete registraion---"+id);
		
		rService.delete(id);
		
		//List<Registration> rList=rService.findAll();
		
		mav.setViewName("/registration");
		//mav.addObject("appList", rList);
		
		return mav;
		//return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ModelAndView update(@RequestBody Registration r,HttpServletRequest req,ModelAndView mav){
		
		
		rService.update(r);
		
		Registration updated=rService.findById(r.getId());
		
		List<Registration> rList=rService.findAll();
		
		//mav.addObject("appList", rList);
		mav.setViewName("/registration");
		
		return mav;
		
		//return new ResponseEntity<Registration>(updated, HttpStatus.OK);
	}

}
