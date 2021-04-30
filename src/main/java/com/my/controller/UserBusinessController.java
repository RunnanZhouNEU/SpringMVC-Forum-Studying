package com.my.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my.DAO.CommunityDAO;
import com.my.DAO.PostDAO;
import com.my.DAO.ThreadsDAO;
import com.my.DAO.UserDAO;
import com.my.pojo.Community;
import com.my.pojo.Post;
import com.my.pojo.Threads;
import com.my.pojo.User;
import com.my.service.StatService;

@Controller
public class UserBusinessController {
	@Autowired
	CommunityDAO communityDAO;
	@Autowired
	ThreadsDAO threadsDAO;
	@Autowired
	PostDAO postDAO;
	@Autowired
	StatService statService;
	@Autowired
	UserDAO userDAO;
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public String showWelcomePage(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		List<Community> allCommunities=new ArrayList<>();
		if (request.getParameter("sort")==null) {
			allCommunities=communityDAO.getAllCommunity();
		}else if (request.getParameter("sort").equals("date")){
			allCommunities=communityDAO.getAllCommunityByDate();
		}else if (request.getParameter("sort").equals("alpha")){
			allCommunities=communityDAO.getAllCommunityByAlpha();
		}	
		
		PrettyTime pt = new PrettyTime();
		for (Community comm: allCommunities) {
			String duration = pt.format(comm.getCreated());
			comm.setDuration(duration);
		}
		
		session.setAttribute("communityList", allCommunities);
		
		request.getSession().setAttribute("LastUrl", request.getRequestURI());
		return "welcome";
	}
	
	@RequestMapping(value="/community/{id}",method=RequestMethod.GET)
	public String showCommunityPage(HttpServletRequest request, @PathVariable String id) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		request.getSession().setAttribute("LastUrl", request.getRequestURI());
		List<Threads> threads =  new ArrayList<>();
		if (request.getParameter("sort")==null) {
			threads=threadsDAO.getThreadsBelongToCommunity(actualID);
		}else if (request.getParameter("sort").equals("date")){
			threads=threadsDAO.getThreadsBelongToCommunityByDate(actualID);
		}else if (request.getParameter("sort").equals("alpha")){
			threads=threadsDAO.getThreadsBelongToCommunityByAlpha(actualID);
		}	
		PrettyTime pt = new PrettyTime();
		for (Threads thread: threads) {
			String duration = pt.format(thread.getCreated());
			thread.setDuration(duration);
		}
		
		
		
		
		request.getSession().setAttribute("threadsList", threads);
		Community community = communityDAO.getCommunityByID(actualID);
		request.getSession().setAttribute("community", community);
		return "community";
	}
	
	
	@RequestMapping(value="/threads/{id}",method=RequestMethod.GET)
	public String showThreadsPage(HttpServletRequest request, @PathVariable String id) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		request.getSession().setAttribute("LastUrl", request.getRequestURI()+"?"+request.getQueryString());
		System.out.println(request.getQueryString());
		System.out.println(request.getRequestURI());
		List<Post> postList =  new ArrayList<>();
		long count=postDAO.getPostsBelongToThreadCount(actualID);
		
		if (request.getParameter("page")==null) {
			return "error";
		}else {
			int pageNum= Integer.parseInt(request.getParameter("page"));
			
			if (pageNum>count || pageNum<1) {
				if (count!=0) {
					return "error";
				}
				
			}
			if (request.getParameter("sort")==null) {
				postList= postDAO.getPostsBelongToThreadByPagination(actualID, pageNum);
			}else if (request.getParameter("sort").equals("date")){
				postList=postDAO.getPostsBelongToThreadByDate(actualID,pageNum);
			}else if (request.getParameter("sort").equals("vote")){
				postList=postDAO.getPostsBelongToThreadByVote(actualID,pageNum);
			}	
		}
		
		PrettyTime pt = new PrettyTime();
		for (Post post: postList) {
			String duration = pt.format(post.getCreated());
			post.setDuration(duration);
		}
		
		
		
		
		
		session.setAttribute("maxPage", count);
		request.getSession().setAttribute("postList", postList);//postlist
		Threads threads = threadsDAO.getThreadsByID(actualID);
		request.getSession().setAttribute("threads", threads);
		return "threads";
	}
	
	@RequestMapping(value="/userprofile",method=RequestMethod.GET)
	public String showUserPublicProfile(HttpServletRequest request) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		User user = (User)request.getSession().getAttribute("user");
		int id = user.getId();
		
		return "redirect:/publicprofile/"+id;
	}
	
	@RequestMapping(value="/publicprofile/{id}",method=RequestMethod.GET)
	public String showUserProfilePage(HttpServletRequest request, @PathVariable String id) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		User user = userDAO.getUserbyID(actualID);
		//check if the input is valid
		if (user==null) {
			return "error";
		}
		PrettyTime p = new PrettyTime();
		
		String duration = p.format(new Date(user.getCreated().getTime()));
		//month is zero based
		
		 
		user.setDuration(duration);
		
		
		
		session.setAttribute("userResult", user);
		
		
		return "userProfile";
	}
	
	@RequestMapping(value="/update/password",method=RequestMethod.GET)
	public String showUpdatePassword(HttpServletRequest request) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		
		return "updatePassword";
	}
	
	
	
	@RequestMapping(value="/update/password/{id}",method=RequestMethod.POST)
	public String updatePassword(HttpServletRequest request, @PathVariable String id, ModelMap model) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		User user = userDAO.getUserbyID(actualID);
		//check if the input is valid
		if (user==null) {
			return "error";
		}
		String password= request.getParameter("password");
		boolean passwordLength = password.length()>=6;
		if (!passwordLength) {
			model.addAttribute("message","Invalid password!");
			return "userProfile";
		}
		userDAO.updatePassword(user.getId(), password);

		model.addAttribute("message","Password Update Success!");
		
		return "userProfile";
	}
	
	
	
	@RequestMapping(value="/verify",method=RequestMethod.GET)
	public String showVerifyPage(HttpServletRequest request) {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		return "userVerify";
	}
	@RequestMapping(value="/verify",method=RequestMethod.POST)
	public String verifyUser(HttpServletRequest request, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		String email  =(String)request.getParameter("userEmail");
		session.setAttribute("userEmail", email);
		//generate random code from here
		String code =String.valueOf( new Random().nextInt(900000) + 100000);
		session.setAttribute("generatedCode", code);
		//send email to the user
		//config the java mail sender
		JavaMailSenderImpl javaMailSenderImpl = getMailConfig();
		User user=  (User)session.getAttribute("user");
		String username = user.getUsername();
		sendEmail(email,code,username,javaMailSenderImpl);
		 return "verifyCode";
	}
	
	public JavaMailSenderImpl getMailConfig() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		//set this with your own gmail account
		javaMailSenderImpl.setUsername("");
		javaMailSenderImpl.setPassword("");
		javaMailSenderImpl.setPort(587);
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		javaMailSenderImpl.setJavaMailProperties(mailProperties);
		return javaMailSenderImpl;
	}
	public int sendEmail(String email, String code,String username, JavaMailSenderImpl javaMailSenderImpl) {
		try {
			SimpleMailMessage newEmail = new SimpleMailMessage();
			newEmail.setTo(email);
			newEmail.setSubject("Email Verification");
			newEmail.setText("Hi "+username+",\n"+"Your code is "+code+"\n"+"Thank you!");
			javaMailSenderImpl.send(newEmail);
			return 1;
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
		
	}
	
	@RequestMapping(value="/verifycode",method=RequestMethod.POST)
	public String verifyCode(HttpServletRequest request, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		String code = request.getParameter("verifyCode");
		String generatedCode = (String)session.getAttribute("generatedCode");
		if (generatedCode.equals(code)) {
			String email = (String)session.getAttribute("userEmail");
			User user = (User)request.getSession().getAttribute("user");
			userDAO.verifyUser(user.getId(), email);
			session.setAttribute("message", "Verify Success!");
			return "redirect:/publicprofile/"+user.getId();
		}else {
			model.addAttribute("errorMessage", "Wrong Code!");
			return "verifyCode";
		}
		
		
		 
	}
	
	@RequestMapping(value = "upvote/post/{id}",method=RequestMethod.POST)
	public String upvotePost (HttpServletRequest request, @PathVariable String id) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		postDAO.upvote(actualID);
		return "success";
	}
	@RequestMapping(value = "downvote/post/{id}",method=RequestMethod.POST)
	public String downvotePost (HttpServletRequest request, @PathVariable String id) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		
		int result = postDAO.downvote(actualID);
		
		return "success";
	}
	
	
	
	@RequestMapping(value="/recentpost",method=RequestMethod.GET)
	public String showRecentPost(HttpServletRequest request) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		
		
		List<Post> postList =  new ArrayList<>();
		User user = (User)session.getAttribute("user");
		
		long count=postDAO.getPostsBelongToUserCount(user.getId());
		
		if (request.getParameter("page")==null) {
			return "error";
		}else {
			int pageNum= Integer.parseInt(request.getParameter("page"));
			if (pageNum>count || pageNum<1) {
				return "error";
			}
			postList = postDAO.getPostsBelongToUser(user.getId(), pageNum);
		}
		
		PrettyTime pt = new PrettyTime();
		for (Post post: postList) {
			String duration = pt.format(post.getCreated());
			post.setDuration(duration);
		}
		
		session.setAttribute("maxPage", count);
		request.getSession().setAttribute("postList", postList);//postlist
		return "userRecentPost";
	}
	
	@RequestMapping(value="/checkpost/{id}",method=RequestMethod.GET)
	public String showRecentPost(HttpServletRequest request, @PathVariable String id) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		
		List<Post> postList =  new ArrayList<>();
		
		long count=postDAO.getPostsBelongToUserCount(actualID);
		
		if (request.getParameter("page")==null) {
			return "error";
		}else {
			int pageNum= Integer.parseInt(request.getParameter("page"));
			if (pageNum>count || pageNum<1) {
				if (count!=0) {
					return "error";
				}
				
			}
			postList = postDAO.getPostsBelongToUser(actualID, pageNum);
		}
		
		PrettyTime pt = new PrettyTime();
		for (Post post: postList) {
			String duration = pt.format(post.getCreated());
			post.setDuration(duration);
		}
		
		session.setAttribute("maxPage", count);
		request.getSession().setAttribute("postList", postList);//postlist
		return "managerCheckPost";
	}
	
}
