package com.my.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="Threads")
public class Threads {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public int getUserAccountID() {
		return userAccountID;
	}
	public void setUserAccountID(int userAccountID) {
		this.userAccountID = userAccountID;
	}
	public List<Post> getPostList() {
		return postList;
	}
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public int getManagerAccountID() {
		return managerAccountID;
	}
	public void setManagerAccountID(int managerAccountID) {
		this.managerAccountID = managerAccountID;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true,nullable=false)
	int id;
	@Column(name="subject")
	String subject;
	@Column(name="created")
	Timestamp created;
	@Column(name="userAccountID")
	int userAccountID;
	@Column(name="managerAccountID")
	int managerAccountID;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="threads")
	List<Post> postList;
	@ManyToOne
	@JoinColumn(name="community_id")
	Community community;
	@Column(name="vote")
	int vote;
	@Transient
	String duration;

	
}
