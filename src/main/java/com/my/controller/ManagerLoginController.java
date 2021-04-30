package com.my.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.pojo.Manager;
import com.my.service.LoginService;

@Controller
public class ManagerLoginController {
	@Autowired
	LoginService service;
	@Autowired
	Manager manager;
	
	@RequestMapping(value="/managerlogin",method=RequestMethod.GET)

	public String showLoginPage() {
		return "managerLogin";
	}
	@RequestMapping(value="/managerlogin",method=RequestMethod.POST)

	public String handleLoginRequest(HttpServletRequest request,@RequestParam String name,@RequestParam String password, ModelMap model) {
		if (!service.ValidateManager(name, password)) {
			model.put("errorMessage", "Invalid Credentials");
			return "managerLogin";
		} 
		HttpSession session = request.getSession();
		manager.setUsername(name);
		manager.setPassword(password);
		session.setAttribute("manager",manager);
		session.setAttribute("isManager", true);
		return "redirect:/welcome";
		
		
	}
}
