package oauthServer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oauthServer.model.User;
import oauthServer.service.UserService;

@Controller
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/add")
	public ModelAndView addUser(@RequestParam String username
			,@RequestParam String password
			,@RequestParam String real_name
			,ModelAndView mav){
		
		User u=new  User();
			u.setPassword(password);
			u.setReal_name(real_name);
			u.setUsername(username);
			
		userService.addUser(u);
		
		List<User> userList=userService.findAll();
		mav.addObject("userList", userList);
		mav.setViewName("/user.jsp");
		
		return mav;
	}
	
	@RequestMapping(path="/findAll")
	public ModelAndView findAll(ModelAndView mav){
		List<User> userList=userService.findAll();
		mav.addObject("userList", userList);
		mav.setViewName("/user.jsp");
		
		return mav;
		
	}
}
