package com.my.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.pojo.Community;
import com.my.pojo.Post;
import com.my.pojo.Threads;


@Component
public class CommunityDAO extends DAO{
@Autowired
ThreadsDAO threadsDAO;
@Autowired
PostDAO postDAO;
public boolean registerCommunity(Community community) throws Exception {
	
		
		try {
			beginTranscation();
			getSession().save(community);
			commit();
			close();
			return true;
		}
		catch(Exception e) {
			rollback();
			throw e;
			
		}
		
	}
public List<Community> getAllCommunity() throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Community");
		List<Community> communities =  (List<Community>)q.list();
		close();
		return communities;
	}catch(Exception e) {
		rollback();
		throw e;
	}
} 

public Map<String,Integer> getThreadNumForCommunity() throws Exception {
	try {
		Map<String,Integer> map =  new HashMap<>();
		beginTranscation();
		Query q = getSession().createQuery("from Community");
		List<Community> communities =  (List<Community>)q.list();
		close();
		for (Community comm: communities) {
			
			int num = threadsDAO.getThreadsBelongToCommunity(comm.getId()).size();
			map.put(comm.getName(), num);
		}
		
		
		return map;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public List<Community> getAllCommunityByDate() throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Community c order by c.created desc");
		List<Community> communities =  (List<Community>)q.list();
		close();
		return communities;
	}catch(Exception e) {
		rollback();
		throw e;
	}
} 
public List<Community> getAllCommunityByAlpha() throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Community c order by c.name ");
		List<Community> communities =  (List<Community>)q.list();
		close();
		return communities;
	}catch(Exception e) {
		rollback();
		throw e;
	}
} 
public Community getCommunityByID(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Community where id=:comid").setInteger("comid", id);
		Community com = (Community)q.uniqueResult();
		close();
		return com;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public boolean deleteCommunity(int id) throws Exception{
	try {
		
		//find all the threads belong to this community
		List<Threads> threads= threadsDAO.getThreadsBelongToCommunity(id);
		//find all the posts belong to this threads
		for (Threads t: threads) {
			
			List<Post> posts = postDAO.getPostsBelongToThread(t.getId());
			for (Post p : posts) {
				postDAO.deletePost(p.getId());
			}
		}
		for (Threads t:threads) {
			threadsDAO.deleteThreads(t.getId());
		}
		beginTranscation();
		Session session = getSession();
		Query q = session.createQuery("delete from Community where id=:comid").setInteger("comid", id);
		q.executeUpdate();
		close();
		return true;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
}
