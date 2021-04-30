package com.my.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.my.pojo.User;
import com.my.service.LoginService;

@Controller
public class UserLoginController {
	//root problem should be noticed. Manage the context path
//	@ResponseBody //not the name of url, turn it into text
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/userlogin",method=RequestMethod.GET)

	public String showLoginPage() {
		return "userLogin";
	}
	
	@RequestMapping(value="/userlogin",method=RequestMethod.POST)

	public ModelAndView handleLoginRequest(HttpServletRequest request, @RequestParam String name,@RequestParam String password, ModelMap model) {
		HttpSession session = request.getSession();
		User user = service.ValidateUser(name, password);
		if (user==null) {
			model.put("errorMessage", "Invalid Credentials");
			return new ModelAndView("userLogin");
		} 
		
		session.setAttribute("user", user);
		session.setAttribute("isManager", false);
		model.addAttribute("user",user); 
		return new ModelAndView("redirect:welcome");
		
		
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String showSignUpPage() {
		return "signUp";
	}
	
	
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String handleSignUpRequest(@RequestParam String name,@RequestParam String password, @RequestParam String repassword, ModelMap model) throws Exception {
		
		//check null
		if (name==null || password==null || repassword==null) {
			model.put("errorMessage","Field can not be null");
			return "signUp";
		} 
		//check whether password equal to the retype password
		if (!password.equals(repassword)) {	
			model.put("errorMessage","password and re-enter not matched");
			return "signUp";
		}
		//check whether username and password 's character is valid. //only need to check one password because the previous statement ensure that the password and re-enter are the same.
		boolean isName = validateString(name);
		boolean isPassword = validateString(password);
		if (!(isName&&isPassword)) {
			model.put("errorMessage","Username and password can not contains alphabets and numbers");
			return "signUp";
		}
		//check the length of username and password
		boolean nameLength = name.length()>=6;
		boolean passwordLength = password.length()>=6;
		if (!(nameLength && passwordLength)) {
			model.put("errorMessage","Username and password length should be more than or equal to 6");
			return "signUp";
		}
		//check whether the username has been already used.
		
		boolean isValid =  service.isDuplicate(name);		
		if (!isValid) {
			model.put("errorMessage","Username already exist");
			return "signUp";
		}
		
		User user =service.registerUser(name, password);
		
		model.put("user", user);
		return "registerSuccess" ;
		
		
	}
	public boolean validateString(String input) {
		 String regex = "^[a-zA-Z0-9]+$";
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(input);
		 return matcher.matches();
	}
}
