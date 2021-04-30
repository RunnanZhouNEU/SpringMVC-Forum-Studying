package com.my.DAO;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.my.pojo.Manager;

@Component
public class ManagerDAO  extends DAO{
public boolean registerUser(Manager manager) throws Exception {
		
		try {
			beginTranscation();
			getSession().save(manager);
			commit();
			close();
			return true;
		}
		catch(Exception e) {
			rollback();
			throw e;
			
		}
		
	}
	
	//login
	public Manager validate(String username, String password) {
		//error protection
		if (username==null || password==null) {
			return null;
		}
		beginTranscation();
		Query<?> q = getSession().createQuery("from Manager where username=:name and password =:pass");
		q.setString("name", username);
		q.setString("pass", password);
		//Convenience method to return a single instance that matches the query, or null if the query returns no results.
		Manager manager = (Manager) q.uniqueResult();
		close();
		return manager;
	}
	
	
	//get all the users.
	public List<Manager> getAllManager() throws Exception {
		try {
			beginTranscation();
			Query q = getSession().createQuery("from Manager");
			List<Manager> managers =  (List<Manager>)q.list();
			close();
			return managers;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	} 
}
