package com.my.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.DAO.CommunityDAO;
import com.my.DAO.PostDAO;
import com.my.DAO.ThreadsDAO;
import com.my.pojo.Community;
import com.my.pojo.Manager;
import com.my.pojo.Post;
import com.my.pojo.Threads;
import com.my.pojo.User;

@Controller
public class CreateController {
	
	@Autowired
	Community community;
	@Autowired
	Threads threads;
	@Autowired
	Post post;
	@Autowired
	CommunityDAO communityDAO;
	@Autowired
	ThreadsDAO threadsDAO;
	@Autowired
	PostDAO postDAO;
	
	@RequestMapping(value="/createcommunity",method=RequestMethod.GET)

	public String showLoginPage(HttpServletRequest request) {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		return "createCommunity";
	}
	@RequestMapping(value="/createcommunity",method=RequestMethod.POST)

	public String createCommunity(HttpServletRequest request, @RequestParam String communityName,@RequestParam String communityDescription, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		User user = (User) request.getSession().getAttribute("user");
		Manager manager =  (Manager) request.getSession().getAttribute("manager");
		community.setCreated(new Timestamp(System.currentTimeMillis()));
		community.setDescription(communityDescription);
		//check community name is duplicate or not
		if (communityName.replace(" ","").length()<1) {
			model.addAttribute("errorMessage", "Invalid Community Name");
			return "createCommunity";
		}
		if (communityDescription.replace(" ", "").length()<10) {
			model.addAttribute("errorMessage", "Description should more than 10 characters");
			return "createCommunity";
		}
		community.setName(communityName);
		if (user!=null) {
			community.setUserAccountID(user.getId());
		}else if (manager!=null) {
			community.setManagerAccountID(manager.getId());
		}
		
		community.setThreadList(new ArrayList<Threads>());
		
		communityDAO.registerCommunity(community);
		model.addAttribute("successMessage", "Community Created!");
		return "success";
	}
	@RequestMapping(value="/createthreads",method=RequestMethod.GET)

	public String showCreateThreadsPage(HttpServletRequest request) {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		return "createThreads";
	}
	@RequestMapping(value="/createthreads",method=RequestMethod.POST)

	public String createThreads(HttpServletRequest request, @RequestParam String threadsSubject, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		User user = (User) session.getAttribute("user");		
		Manager manager =  (Manager) request.getSession().getAttribute("manager");
		Community community = (Community) session.getAttribute("community"); 
		threads.setCommunity(community);
		if (user!=null) {
			threads.setUserAccountID(user.getId());
		}else if (manager!=null) {
			threads.setManagerAccountID(manager.getId());
		}
		if (threadsSubject.replace(" ", "").length()<5) {
			model.addAttribute("errorMessage", "Subject should more than 5 characters");
			return "createThreads";
		}
		threads.setSubject(threadsSubject);
		threads.setCreated(new Timestamp(System.currentTimeMillis()));
		threads.setPostList(new ArrayList<Post>());
		threadsDAO.registerThreads(threads);
		
		model.addAttribute("successMessage", "Thread Created!");
		return "success";
	}
	
	
	@RequestMapping(value="/createpost",method=RequestMethod.GET)

	public String createPost(HttpServletRequest request) {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		return "createPost";
	}
	@RequestMapping(value="/createpost",method=RequestMethod.POST)

	public String createPost(HttpServletRequest request, @RequestParam String content, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		User user = (User) session.getAttribute("user");		
		Manager manager =  (Manager) request.getSession().getAttribute("manager");
		Threads thread  = (Threads) session.getAttribute("threads");
		if (content.replace(" ", "").length()<5) {
			model.addAttribute("errorMessage", "Content should more than 5 characters");
			return "createPost";
		}
		post.setContent(content);
		post.setCreated(new Timestamp(System.currentTimeMillis()));
		post.setThreads(thread);
		if (user!=null) {
			post.setUserAccountID(user.getId());
			post.setPosterName(user.getUsername());
		}else if (manager!=null) {
			post.setManagerAccountID(manager.getId());
			post.setPosterName(manager.getUsername());
		}
		
		postDAO.registerPost(post);
		model.addAttribute("successMessage", "Post Created!");
		return "success";
	}
}
