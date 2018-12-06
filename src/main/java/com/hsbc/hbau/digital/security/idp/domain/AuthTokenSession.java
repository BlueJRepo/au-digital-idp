package com.hsbc.hbau.digital.security.idp.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("authTokenSessions")
public class AuthTokenSession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6805872070146306193L;
	
	@Id
	private String authToken;	
	private long creationTimeStamp;
	private long firstEnquiryTimeStamp;
	private TokenStatus tokenStatus;
	
	@TimeToLive
	private int timeOutInSeconds;
	
	public int getTimeOutInSeconds() {
		return timeOutInSeconds;
	}

	public void setTimeOutInSeconds(int timeOutInSeconds) {
		this.timeOutInSeconds = timeOutInSeconds;
	}

	public AuthTokenSession(){}
	
	public AuthTokenSession(String authToken, long creationTimeStamp, 
			long firstEnquiryTimeStamp, 
							TokenStatus tokenStatus, int timeOutInSeconds){
		this.authToken = authToken;
		this.creationTimeStamp = creationTimeStamp;
		this.firstEnquiryTimeStamp = firstEnquiryTimeStamp;
	    this.tokenStatus = tokenStatus;
	    this.timeOutInSeconds = timeOutInSeconds;
	    
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(long creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	public long getFirstEnquiryTimeStamp() {
		return firstEnquiryTimeStamp;
	}

	public void setFirstEnquiryTimeStamp(long firstEnquiryTimeStamp) {
		this.firstEnquiryTimeStamp = firstEnquiryTimeStamp;
	}

	public TokenStatus getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(TokenStatus tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
	

}