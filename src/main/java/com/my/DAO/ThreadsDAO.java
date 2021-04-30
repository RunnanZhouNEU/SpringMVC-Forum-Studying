package com.my.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.pojo.Post;
import com.my.pojo.Threads;


@Component
public class ThreadsDAO  extends DAO{
	@Autowired
	PostDAO postDAO;
public boolean registerThreads(Threads threads) throws Exception {
			
		try {
			beginTranscation();
			getSession().save(threads);
			commit();
			close();
			return true;
		}
		catch(Exception e) {
			rollback();
			throw e;
			
		}
		
	}
public List<Threads> getAllThreads() throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Threads");
		List<Threads> threads =  (List<Threads>)q.list();
		close();
		return threads;
	}catch(Exception e) {
		rollback();
		throw e;
	}
} 
public List<Threads> getThreadsBelongToCommunity(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Threads t where t.community=:id").setInteger("id", id);
		List<Threads> threads =  (List<Threads>)q.list();
		close();
		return threads;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public List<Threads> getThreadsBelongToCommunityByDate(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Threads t where t.community=:id order by t.created desc").setInteger("id", id);
		List<Threads> threads =  (List<Threads>)q.list();
		close();
		return threads;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public List<Threads> getThreadsBelongToCommunityByAlpha(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Threads t where t.community=:id order by t.subject").setInteger("id", id);
		List<Threads> threads =  (List<Threads>)q.list();
		close();
		return threads;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public Threads getThreadsByID(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Threads where id=:thid").setInteger("thid", id);
		Threads threads = (Threads)q.uniqueResult();
		close();
		return threads;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
//upvote a thread
public int upvote(int id) throws Exception{
	try {
		beginTranscation();
		Query q = getSession().createQuery("update Threads t  set t.vote=t.vote+1 where id=:thid").setInteger("thid", id);
		int count = q.executeUpdate();
		close();
		return count;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
//downvote a thread
public int downvote(int id) throws Exception{
	try {
		beginTranscation();
		Query q = getSession().createQuery("update Threads t  set t.vote=t.vote-1 where id=:thid").setInteger("thid", id);
		int count = q.executeUpdate();
		close();
		return count;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public boolean deleteThreads(int id) throws Exception{
	try {
		List<Post> posts = postDAO.getPostsBelongToThread(id);
		for (Post p : posts) {
			postDAO.deletePost(p.getId());
		}
		beginTranscation();
		Session session  = getSession();
		Query q = session.createQuery("delete from Threads where id=:tid").setInteger("tid", id);
		q.executeUpdate();
		close();
		return true;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
}
