package com.hsbc.hbau.digital.security.idp.domain;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("users")
public class User implements Serializable{

	private Long id;
	private String userName;
	private String secret;
	private String applicationId;
	private String dob;
	
	public User(){}
	
	public User(Long id, String userName, String secret, String applicationId, String dob){
		this.id = id;
		this.userName = userName;
		this.secret = secret;
		this.applicationId = applicationId;
		this.dob = dob;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, username=%s, secret=%s, applicationId=%s, dob=%s]", id, userName, secret, applicationId, dob);
	}

	
}