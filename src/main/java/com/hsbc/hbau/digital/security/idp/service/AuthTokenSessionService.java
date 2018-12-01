package com.hsbc.hbau.digital.security.idp.service;

import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;
import com.hsbc.hbau.digital.security.idp.domain.TokenStatus;

public interface AuthTokenSessionService {
	
	TokenStatus isValid(String authToken);
	void inValidate(String authToken);
	AuthTokenSession startTokenSession(String authToken, int timeOutInSeconds);
	
}
