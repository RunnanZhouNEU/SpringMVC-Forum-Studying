package com.my.DAO;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.my.pojo.User;

@Component
public class UserDAO extends DAO{
	//add user
	public UserDAO() {
		
	}
	public boolean registerUser(User user) throws Exception {
		
		try {
			beginTranscation();
			getSession().save(user);
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
	public User validate(String username, String password) {
		//error protection
		if (username==null || password==null) {
			return null;
		}
		beginTranscation();
		Query<?> q = getSession().createQuery("from User where username=:name and password =:pass");
		q.setString("name", username);
		q.setString("pass", password);
		//Convenience method to return a single instance that matches the query, or null if the query returns no results.
		User user = (User) q.uniqueResult();
		close();
		return user;
	}
	
	public int updatePassword(int id,String password) {
		try {
			beginTranscation();
			Query q = getSession().createQuery("update User set password=:pass where id= :uid").setInteger("uid", id).setString("pass", password);
			int result = q.executeUpdate();
			close();
			return result;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	}
	
	//get all the users.
	public List<User> getAllUser() throws Exception {
		try {
			beginTranscation();
			Query q = getSession().createQuery("from User");
			List<User> users =  (List<User>)q.list();
			close();
			return users;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	} 
	
	public User getUserbyID(int id) throws Exception{
		try {
			beginTranscation();
			Query q = getSession().createQuery("from User where id= :uid").setInteger("uid", id);
			User user = (User) q.uniqueResult();
			close();
			return user;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	}
	
	public int verifyUser(int id, String email) throws Exception{
		try {
			beginTranscation();
			Query q = getSession().createQuery("update User set verify=true, email=:email where id= :uid").setInteger("uid", id).setString("email", email);
			int result = q.executeUpdate();
			close();
			return result;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	}
	public int awardUser(int id, int point) throws Exception{
		try {
			beginTranscation();
			Query q = getSession().createQuery("update User set credit=credit+:point where id= :uid").setInteger("uid", id).setInteger("point", point).setInteger("uid", id);
			int result = q.executeUpdate();
			close();
			return result;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	}
	public int punishUser(int id, int point) throws Exception{
		try {
			beginTranscation();
			Query q = getSession().createQuery("update User set credit=credit-:point where id= :uid").setInteger("uid", id).setInteger("point", point).setInteger("uid", id);
			int result = q.executeUpdate();
			close();
			return result;
		}catch(Exception e) {
			rollback();
			throw e;
		}
	}
}
