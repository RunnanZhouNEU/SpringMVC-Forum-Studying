package com.my.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.DAO.CommunityDAO;
import com.my.DAO.PostDAO;
import com.my.DAO.UserDAO;
import com.my.pojo.Post;
import com.my.pojo.User;
@Service
public class StatService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	PostDAO postDAO;
	@Autowired
	CommunityDAO communityDAO;
	
	public Map<Integer,Integer> getPostNum() throws Exception{
		Map<Integer,Integer> map  =new HashMap<>();
		List<User> users  =userDAO.getAllUser();
		for (User user:users) {
			List<Post> posts= postDAO.getPostsBelongToUser(user.getId());
			map.put(user.getId(), posts.size());
		}
		return map;
	}
	
	public Map<String,Integer> getPostNumPerCommunity() throws Exception{
		Map<String,Integer> map  =new HashMap<>();
		map = communityDAO.getThreadNumForCommunity();
		
		return map;
	}
	
	
}
