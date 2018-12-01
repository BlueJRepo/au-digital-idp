package com.hsbc.hbau.digital.security.idp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hsbc.hbau.digital.security.idp.dao.UserRepository;


@SpringBootApplication
public class Application {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
