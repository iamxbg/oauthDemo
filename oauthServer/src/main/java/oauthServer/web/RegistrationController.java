package oauthServer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.Registration;
import oauthServer.service.RegistrationService;


@Controller
@RequestMapping(path="/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService rService;
	
	
	private static Logger logger=LogManager.getLogger(RegistrationController.class);
	
	@RequestMapping(path="/view")
	public ModelAndView findAll(ModelAndView mav){
		
		List<Registration> rList=rService.findAll();
		
		mav.addObject("appList", rList);
		mav.setViewName("/registration.jsp");
		
		return mav;

	}
	
	@RequestMapping(path="/add")
	public ResponseEntity<Registration> add(@RequestBody Registration r,HttpServletRequest req){

			Registration reg=new Registration(r.getName(),
					r.getClient_id(),
					r.getClient_secrect(),
					r.getDescription(), 
					r.getRedirection_uri(),
					r.getReceive_token_uri(),
					r.getReceive_authz_code_uri());

			int id=rService.add(reg);
			
			Registration saved=rService.findById(id);
			
			return new ResponseEntity<Registration>(saved, HttpStatus.OK);

	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/disable/{id}")
	public ResponseEntity disable(@PathVariable("id") int id){

		Registration r=rService.findById(id);
			r.setDel_flag('Y');
			rService.update(r);

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/enable/{id}")
	public ResponseEntity enable(@PathVariable("id") int id){
		Registration r=rService.findById(id);
		r.setDel_flag('N');
		rService.update(r);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(path="/update")
	public ResponseEntity<Registration> update(@RequestBody Registration r,HttpServletRequest req,ModelAndView mav){
		
		rService.update(r);
		Registration updated=rService.findById(r.getId());

		return new ResponseEntity<Registration>(updated, HttpStatus.OK);


	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/openClientAuthorize?id={id}")
	public ResponseEntity openClientRegistration(@PathVariable("id") int id,HttpServletRequest req){
		Registration r=rService.findById(id);
			r.setIs_client_auth_enabled('Y');
		rService.update(r);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/openServerAuthorize?id={id}")
	public ResponseEntity openServerRegistration(@PathVariable("id") int id,HttpServletRequest req){
		Registration r=rService.findById(id);
			r.setIs_server_auth_enabled('Y');
		rService.update(r);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/closeClientAuthorize?id={id}")
	public ResponseEntity closeClientRegistration(@PathVariable("id") int id,HttpServletRequest req){
		Registration r=rService.findById(id);
			r.setIs_client_auth_enabled('N');
		rService.update(r);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(path="/closeServerAuthorize?id={id}")
	public ResponseEntity closeServerRegistration(@PathVariable("id") int id,HttpServletRequest req){
		Registration r=rService.findById(id);
			r.setIs_server_auth_enabled('N');
		rService.update(r);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
