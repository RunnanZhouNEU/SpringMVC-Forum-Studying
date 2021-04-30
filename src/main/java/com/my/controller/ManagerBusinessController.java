package com.my.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.DAO.CommunityDAO;
import com.my.DAO.PostDAO;
import com.my.DAO.ThreadsDAO;
import com.my.DAO.UserDAO;
import com.my.pojo.Manager;
import com.my.pojo.Threads;
import com.my.pojo.User;
import com.my.service.StatService;

@Controller
public class ManagerBusinessController {
	@Autowired
	CommunityDAO communityDAO;
	@Autowired
	ThreadsDAO threadsDAO;
	@Autowired
	PostDAO postDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	StatService statService;
	@RequestMapping(value="/deletecommunity/{id}",method=RequestMethod.POST)
	public String deleteCommunity(HttpServletRequest request, @PathVariable String id,ModelMap model) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		communityDAO.deleteCommunity(actualID);
		model.addAttribute("successMessage", "Community Deleted!");
		return "success";
	}
	
	@RequestMapping(value="/deletethreads/{id}",method=RequestMethod.POST)
	public String deleteThreads(HttpServletRequest request, @PathVariable String id, ModelMap model) throws NumberFormatException, Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		threadsDAO.deleteThreads(actualID);
		model.addAttribute("successMessage", "Thread Deleted!");
		return "success";
	}
	@RequestMapping(value="/deletepost/{id}",method=RequestMethod.POST)

	public String deletePost(HttpServletRequest request, @PathVariable String id, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		
		User user = (User) session.getAttribute("user");		
		Threads thread  = (Threads) session.getAttribute("threads");
		
		postDAO.deletePost(actualID);
		model.addAttribute("successMessage", "Post Deleted!");
		return "success";
	}
	@RequestMapping(value="/exit",method=RequestMethod.GET)

	public String endSession(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		session.invalidate();
		return "front";
	}
	@RequestMapping(value="/management/alluser",method=RequestMethod.GET)

	public String showAllUser(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		Manager manager = (Manager)session.getAttribute("manager");
		boolean isManager = (Boolean)session.getAttribute("isManager");
		if (isManager) {
			Map<Integer,Integer> user_post= statService.getPostNum();
			session.setAttribute("map", user_post);
			List<User> userList =  userDAO.getAllUser();
			session.setAttribute("userList", userList);
			return "userList";
		}else {
			return "error";
		}
		
		
	}
	@RequestMapping(value="/management/report",method=RequestMethod.GET)

	public String showReport(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		
		return "report";
		
	}
	@RequestMapping(value="/management/userpost",method=RequestMethod.GET)
	@ResponseBody
	public String getUserPostNum(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		Map<Integer,Integer> map = statService.getPostNum();
		Map<String,Integer> mapUser = new HashMap<>();
		mapUser.put("Never Post", 0);
		mapUser.put("Post less than 5", 0);
		mapUser.put("Post less than 10", 0);
		mapUser.put("Post 10 or more", 0);
		for (Integer id :map.keySet()) {
			if (map.get(id)==0) {
				mapUser.put("Never Post", mapUser.getOrDefault("Never Post", 0)+1);
			}else if (map.get(id)<5) {
				mapUser.put("Post less than 5", mapUser.getOrDefault("Post less than 5", 0)+1);
			}else if (map.get(id)<10) {
				mapUser.put("Post less than 10", mapUser.getOrDefault("Post less than 10", 0)+1);
			}else if (map.get(id)>=10){
				mapUser.put("Post 10 or more", mapUser.getOrDefault("Post 10 or more", 0)+1);
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(mapUser);
			return json;
		}catch(JsonProcessingException e){
			e.printStackTrace();
			return "error";
		}
		
		
	}
	@RequestMapping(value="/management/communityThread",method=RequestMethod.GET)
	@ResponseBody
	public String getCommunityThread(HttpServletRequest request) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		Map<String,Integer> mapComm = statService.getPostNumPerCommunity();
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(mapComm);
			return json;
		}catch(JsonProcessingException e){
			e.printStackTrace();
			return "error";
		}
		
		
	}
	
	
	
	@RequestMapping(value="/management/award/{id}",method=RequestMethod.POST)

	public String awardUser(HttpServletRequest request, @PathVariable String id, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		int point =0;
		try {
			 point =  Integer.parseInt(request.getParameter("points"));
		}
		catch(Exception e) {
			return "error";
		}
		
		userDAO.awardUser(actualID, point);
		return "success";
	}
	
	@RequestMapping(value="/management/punish/{id}",method=RequestMethod.POST)

	public String punishUser(HttpServletRequest request, @PathVariable String id, ModelMap model) throws Exception {
		HttpSession session  =request.getSession();
		if ((session.getAttribute("user")!=null || session.getAttribute("manager")!=null)==false) {
			return "front";
		}
		if ((session.getAttribute("manager")==null)) {
			return "error";
		}
		int actualID=0;
		try {
			actualID = Integer.parseInt(id);
		}catch(Exception e) {
			return "error";
		}
		int point =0;
		try {
			 point =  Integer.parseInt(request.getParameter("points"));
		}
		catch(Exception e) {
			return "error";
		}


		userDAO.punishUser(actualID, point);
		return "success";
	}
	
}
