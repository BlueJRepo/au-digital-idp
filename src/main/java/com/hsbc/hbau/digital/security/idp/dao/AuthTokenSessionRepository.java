package com.hsbc.hbau.digital.security.idp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;

@Repository("authTokenSessionRepository")
public interface AuthTokenSessionRepository extends CrudRepository<AuthTokenSession, String>{

}
	