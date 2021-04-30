package com.my.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="Post")
public class Post {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public int getThreadID() {
		return threadID;
	}
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	public int getUserAccountID() {
		return userAccountID;
	}
	public void setUserAccountID(int userAccountID) {
		this.userAccountID = userAccountID;
	}
	public Threads getThreads() {
		return threads;
	}
	public void setThreads(Threads threads) {
		this.threads = threads;
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
	public String getPosterName() {
		return posterName;
	}
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true,nullable=false)
	int id;
	@Column(name="content")
	String content;
	@Column(name="created")
	Timestamp created;
	@Column(name="threadID")
	int threadID;
	@Column(name="userAccountID")
	int userAccountID;
	@Column(name="posterName")
	String posterName;

	@Column(name="managerAccountID")
	int managerAccountID;
	@Column(name="vote")
	int vote;
	@ManyToOne
	@JoinColumn(name="threads_id")
	Threads threads;
	@Transient
	String duration;
}
