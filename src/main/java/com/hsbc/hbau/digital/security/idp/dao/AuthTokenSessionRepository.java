package com.hsbc.hbau.digital.security.idp.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.hsbc.hbau.digital.security.idp.domain.AuthTokenSession;

@RepositoryRestResource(path = "authTokenSessions", collectionResourceRel = "authTokenSessions")
public interface AuthTokenSessionRepository extends PagingAndSortingRepository<AuthTokenSession, String>{

}
	