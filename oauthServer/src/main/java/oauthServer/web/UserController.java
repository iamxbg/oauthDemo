package oauthServer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ModelAndView add(@RequestParam String username
			,@RequestParam String password
			,@RequestParam String name
			,ModelAndView mav){
		
		User u=new  User();
			u.setPassword(password);
			u.setName(name);
			u.setUsername(username);
			
		userService.add(u);
		
		List<User> userList=userService.findAll();
		mav.addObject("userList", userList);
		mav.setViewName("/user");
		
		return mav;
	}
	
	@RequestMapping(path="")
	public ModelAndView findAll(ModelAndView mav){
		List<User> userList=userService.findAll();
		mav.addObject("userList", userList);
		mav.setViewName("/user.jsp");
		
		return mav;
		
	}
	
	@RequestMapping(path="/update")
	public ModelAndView update(@RequestBody User u,ModelAndView mav){
		userService.update(u);
		
		mav.setViewName("/user");
		
		return mav;
		
	}
	
	@RequestMapping(path="/del/{id}")
	public ModelAndView delete(@PathVariable("id") int id,ModelAndView mav){
		
		userService.delete(id);
		
		mav.setViewName("/user");
		
		return mav;
	}
}
