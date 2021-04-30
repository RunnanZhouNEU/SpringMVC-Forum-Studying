package com.my.pojo;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
@Entity
@Table(name="User")
@Component
public class User {
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isVerify() {
		return verify;
	}
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTime() {
		PrettyTime p = new PrettyTime();
		String result = p.approximateDuration(created).toString();
		
		return result;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique = true, nullable=false)
	int id;
	@Column(name="username")
	String username;
	@Column(name="password")
	String password;
	@Column(name="created")
	Timestamp created;
	@Column(name="credit")
	int credit;
	@Column(name="verify")
	boolean verify;
	@Column(name="email")
	String email;
	@Transient
	String duration;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", created=" + created + "]";
	}
	
}
