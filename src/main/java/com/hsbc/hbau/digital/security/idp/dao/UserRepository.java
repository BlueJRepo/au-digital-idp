package com.hsbc.hbau.digital.security.idp.dao;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.hsbc.hbau.digital.security.idp.domain.User;

//@RepositoryRestResource(path = "users", collectionResourceRel = "users")
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
	