package com.my.DAO;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.my.pojo.Post;

@Component
public class PostDAO extends DAO{
public boolean registerPost(Post post) throws Exception {
		
		try {
			beginTranscation();
			getSession().save(post);
			commit();
			close();
			return true;
		}
		catch(Exception e) {
			rollback();
			throw e;
			
		}
		
	}
public List<Post> getAllPost() throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post");
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
} 
public List<Post> getPostsBelongToThread(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post p where p.threads=:id").setInteger("id", id);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}

public List<Post> getPostsBelongToThreadByPagination(int id, int pageNum) throws Exception {
	try {
		beginTranscation();
		 Query q = getSession().createQuery("from Post p where p.threads=:id").setInteger("id", id)
				.setFirstResult((pageNum-1)*5)
				.setMaxResults(5);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}

public long getPostsBelongToThreadCount(int id) {
	try {
		beginTranscation();
		int pageSize=10;
		Query q = getSession().createQuery("select count(p.id) from Post p where p.threads=:id").setInteger("id", id);
		long countResult = (long)q.uniqueResult();
		long page = 0;
		if (countResult%5==0) {
			page=countResult/5;
		}else {
			page=countResult/5+1;
		}
		close();
		return page;
	}catch(Exception e) {
		rollback();
		throw e;
	}
	
}

public long getPostsBelongToUserCount(int id) {
	try {
		beginTranscation();
		
		Query q = getSession().createQuery("select count(p.id) from Post p where p.userAccountID=:id").setInteger("id", id);
		long countResult = (long)q.uniqueResult();
		long page = 0;
		if (countResult%5==0) {
			page=countResult/5;
		}else {
			page=countResult/5+1;
		}
		close();
		return page;
	}catch(Exception e) {
		rollback();
		throw e;
	}
	
}

public List<Post> getPostsBelongToThreadByVote(int id, int pageNum) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post p where p.threads=:id order by p.vote desc").setInteger("id", id)
				.setFirstResult((pageNum-1)*5)
				.setMaxResults(5);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}

public List<Post> getPostsBelongToThreadByDate(int id, int pageNum) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post p where p.threads=:id order by p.created desc").setInteger("id", id)
				.setFirstResult((pageNum-1)*5)
				.setMaxResults(5);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}

public List<Post> getPostsBelongToUser(int id, int pageNum) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post p where p.userAccountID=:id order by p.created desc").setInteger("id", id)
				.setFirstResult((pageNum-1)*5).setMaxResults(5);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
public List<Post> getPostsBelongToUser(int id) throws Exception {
	try {
		beginTranscation();
		Query q = getSession().createQuery("from Post p where p.userAccountID=:id").setInteger("id", id);
		List<Post> posts =  (List<Post>)q.list();
		close();
		return posts;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
//upvote a post
public int upvote(int id) throws Exception{
	try {
		beginTranscation();
		Query q = getSession().createQuery("update Post p  set p.vote=p.vote+1 where id=:pid").setInteger("pid", id);
		int count = q.executeUpdate();
		close();
		return count;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
//downvote a post
public int downvote(int id) throws Exception{
	try {
		beginTranscation();
		Query q = getSession().createQuery("update Post p  set p.vote=p.vote-1 where id=:pid").setInteger("pid", id);
		int count = q.executeUpdate();
		close();
		return count;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}

public int deletePost(int id) throws Exception{
	try {
		beginTranscation();
		Query q = getSession().createQuery("delete Post p where id=:pid").setInteger("pid", id);
		int count=q.executeUpdate();
		close();
		return count;
	}catch(Exception e) {
		rollback();
		throw e;
	}
}
}
