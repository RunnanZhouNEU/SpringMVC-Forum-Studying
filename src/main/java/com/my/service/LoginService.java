package com.my.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.DAO.ManagerDAO;
import com.my.DAO.UserDAO;
import com.my.pojo.Manager;
import com.my.pojo.User;

@Service
public class LoginService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	ManagerDAO managerDAO;
	@Autowired
	Manager manager;
	@Autowired
	User user;
	//login
	public User ValidateUser(String user, String password) {
		//check whether there is a user object returned.
		User result = userDAO.validate(user, password);
		return result;
	}
	
	public boolean ValidateManager(String user,String password) {
		Manager manager = managerDAO.validate(user, password);
		if (manager==null) {
			return false;
		}
		return true;
	}
	//add user
	public User registerUser(String username, String password) throws Exception {
		try {
			user.setUsername(username);
			user.setPassword(password);
			user.setCreated(new Timestamp(System.currentTimeMillis()));
			user.setEmail("");
			user.setVerify(false);
			user.setCredit(10);
			
			Boolean result = userDAO.registerUser(user);
			//if register fail, return null
			if (!result) {
				return null;
			}
			return user;
		}catch(Exception e) {
			throw e;
		}
		
	}
	public List<Manager> getAllManager() throws Exception{
		try {
			return managerDAO.getAllManager();
		}catch(Exception e) {
			throw e;
		}
	} 
	
	public List<User> getAllUser() throws Exception{
		try {
			return userDAO.getAllUser();
		}catch(Exception e) {
			throw e;
		}
	} 
	
	public boolean isDuplicate (String name) throws Exception {
		List<User> users = this.getAllUser();
		for (User user:users) {
			if (user.getUsername().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
}
