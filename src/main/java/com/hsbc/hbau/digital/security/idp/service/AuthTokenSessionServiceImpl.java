package com.hsbc.hbau.digital.security.idp.service;

import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsbc.hbau.digital.security.idp.dao.AuthTokenSessionRepository;
import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;
import com.hsbc.hbau.digital.security.idp.domain.TokenStatus;

@Service("authTokenSessionService")
public class AuthTokenSessionServiceImpl implements AuthTokenSessionService{

	@Autowired 
	private AuthTokenSessionRepository authTokenSessionRepository;
	
	@Override
	@Transactional
	public TokenStatus isValid(String authToken) {
		Optional<AuthTokenSession>  optional = authTokenSessionRepository.findById(authToken);
		if(optional.isPresent()) {
			AuthTokenSession authTokenSession = optional.get();
			if(authTokenSession.getFirstEnquiryTimeStamp() == 0) {
				DateTime now = new DateTime();
				authTokenSession.setFirstEnquiryTimeStamp(now.getMillis());
				authTokenSessionRepository.save(authTokenSession);
			}
			authTokenSession.getTokenStatus();
		}
		return null;
	}

	@Override
	public void inValidate(String authToken) {
		Optional<AuthTokenSession>  optional = authTokenSessionRepository.findById(authToken);
		if(optional.isPresent()) {
			AuthTokenSession authTokenSession = optional.get();
			authTokenSession.setTokenStatus(TokenStatus.INVALIDATED);
			authTokenSessionRepository.save(authTokenSession);
		}
	}

	@Override
	public AuthTokenSession startTokenSession(String authToken, int timeOutInSeconds) {
		
		AuthTokenSession authTokenSession = new AuthTokenSession();
		authTokenSession.setAuthToken(authToken);
		authTokenSession.setFirstEnquiryTimeStamp(0);
		DateTime now = new DateTime();
		authTokenSession.setCreationTimeStamp(now.getMillis());
		authTokenSession.setTimeOutInSeconds(timeOutInSeconds);
		authTokenSessionRepository.save(authTokenSession);
		return authTokenSession;
	}

}
