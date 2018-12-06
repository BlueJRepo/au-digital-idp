package com.hsbc.hbau.digital.security.idp.invoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;
import com.hsbc.hbau.digital.security.idp.service.AuthTokenSessionService;


@RestController
@EnableAutoConfiguration
public class CustomerAPIController implements CustomerAPI{
		
	private static final Logger _log = LoggerFactory.getLogger(CustomerAPIController.class);	

	@Value("${auth.token.ttl}")
	private String timeOutInSeconds; 
	
	@Autowired
	private AuthTokenSessionService authTokenSessionService;
	
    public ResponseEntity<AuthTokenSession> findById(@PathVariable String authToken){    	
    	
    	_log.info("Entering CustomerAPIController.findById ...");
    	
    	AuthTokenSession result = authTokenSessionService.startTokenSession(authToken, Integer.parseInt(timeOutInSeconds));
    	
    	if(result != null) {
    		return new ResponseEntity<AuthTokenSession>(result, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<AuthTokenSession>(result, HttpStatus.BAD_REQUEST);
    	
    }


    
}