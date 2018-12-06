package com.hsbc.hbau.digital.security.idp.invoker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;


public interface CustomerAPI {

    @GetMapping("/customers/{authToken}")
    public ResponseEntity<AuthTokenSession> findById(@PathVariable String authToken);
    
}