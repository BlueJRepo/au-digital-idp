package com.hsbc.hbau.digital.security.idp.dao;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hsbc.hbau.digital.security.idp.ApplicationConfiguration;
import com.hsbc.hbau.digital.security.idp.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Before
    public void setUp() throws Exception {}

	@After
	
	public void cleanUp() {}
	
	@Test
	public void findUserWhenSaveUser(){
		//CREATE
		User ivan = new User(1L, "ivanm", "iv4nm", "HL93674937", "26/11/1978");
		userRepository.save(ivan);
		
		//READ
		User ivanFound1 = userRepository.findById(1L).get();
		assertTrue(ivan.getApplicationId().equals(ivanFound1.getApplicationId()));
		System.out.println("===================READ===============================");
		System.out.println(ivanFound1.getApplicationId()+ "=" + ivan.getApplicationId());
		//UPDATE
		ivanFound1.setSecret("iv4nm01");
		userRepository.save(ivanFound1);		
		User ivanFound2 = userRepository.findById(1L).get();
		assertTrue(ivanFound2.getSecret().equals("iv4nm01"));
		System.out.println("===================UPDATE===============================");				
		System.out.println(ivanFound2.getSecret()+ "=" + "iv4nm01");
		
		//DELETE
		userRepository.delete(ivanFound2);
		Optional<User> ivanOption = userRepository.findById(1L);
		assertTrue(!ivanOption.isPresent());
		System.out.println("===================DELETE===============================");				
		System.out.println(ivanOption.isPresent());
		
	}
	
	

   
}
