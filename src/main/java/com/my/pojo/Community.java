package com.my.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Entity
@Table(name="Community")
@Component
public class Community {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Threads> getThreadList() {
		return threadList;
	}
	public void setThreadList(List<Threads> threadList) {
		this.threadList = threadList;
	}
	public int getManagerAccountID() {
		return managerAccountID;
	}
	public void setManagerAccountID(int managerAccountID) {
		this.managerAccountID = managerAccountID;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserAccountID() {
		return userAccountID;
	}
	public void setUserAccountID(int userAccountID) {
		this.userAccountID = userAccountID;
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
	@Column(name="name")
	String name;
	@Column(name="description")
	String description;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL, mappedBy="community")
	List<Threads> threadList;
	@Column(name="managerAccountID")
	int managerAccountID;
	@Column(name="userAccountID")
	int userAccountID;
	
	@Column(name="created")
	Timestamp created;
	@Transient
	String duration;
}
