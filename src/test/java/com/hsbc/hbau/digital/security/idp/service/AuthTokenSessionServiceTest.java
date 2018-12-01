package com.hsbc.hbau.digital.security.idp.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hsbc.hbau.digital.security.idp.ApplicationConfiguration;
import com.hsbc.hbau.digital.security.idp.dao.AuthTokenSessionRepository;
import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;
import com.hsbc.hbau.digital.security.idp.domain.TokenStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class AuthTokenSessionServiceTest {
	
	@Autowired
	private AuthTokenSessionService authTokenSessionService;
	
	@Autowired
	private AuthTokenSessionRepository authTokenSessionRepository;
	
	private String authToken;
	private int DEFAULT_TIME_TO_LIVE = 5; 
	
	@Before
	public void setUp() {
		authToken = "authTokenUnitTest";
	}
	
	@Test
	public void test_start_a_token_session() {
		AuthTokenSession authTokenSession = authTokenSessionService.startTokenSession(authToken, DEFAULT_TIME_TO_LIVE);
		Optional<AuthTokenSession> option = authTokenSessionRepository.findById(authToken);
		assertTrue(option.isPresent());
		if(option.isPresent()) {
			AuthTokenSession authTokenSessionFound = option.get();
			assertTrue(authTokenSession.getAuthToken().equals(authTokenSessionFound.getAuthToken()));
		}
	}
	
	@Test
	public void test_authToken_time_to_live() throws InterruptedException {
		AuthTokenSession authTokenSession = authTokenSessionService.startTokenSession(authToken, DEFAULT_TIME_TO_LIVE);
		Optional<AuthTokenSession> option = authTokenSessionRepository.findById(authToken);
		assertTrue(option.isPresent());
		
		//wait for timeOutInSeconds + 1		
		Thread.sleep((DEFAULT_TIME_TO_LIVE+1)*1000);
		option = authTokenSessionRepository.findById(authToken);
		assertTrue(!option.isPresent());

	}
	
	@Test
	public void test_invalidate_authToken() {
		AuthTokenSession authTokenSession = authTokenSessionService.startTokenSession(authToken, DEFAULT_TIME_TO_LIVE);
		authTokenSessionService.inValidate(authToken);
		
		Optional<AuthTokenSession> option = authTokenSessionRepository.findById(authToken);
		AuthTokenSession authTokenSessionFound = option.get();
		assertTrue(authTokenSessionFound.getTokenStatus().equals(TokenStatus.INVALIDATED));
		
	}
	
	@Test
	public void test_isValid_authToken() {
		AuthTokenSession authTokenSession = authTokenSessionService.startTokenSession(authToken, DEFAULT_TIME_TO_LIVE);
		Optional<AuthTokenSession> option = authTokenSessionRepository.findById(authToken);
		AuthTokenSession authTokenSessionFound = option.get();
		assertTrue(authTokenSessionFound.getFirstEnquiryTimeStamp() == 0);
		
		authTokenSessionService.isValid(authToken);
		option = authTokenSessionRepository.findById(authToken);
		authTokenSessionFound = option.get();
		assertTrue(authTokenSessionFound.getFirstEnquiryTimeStamp() > 1000);
	}
}
