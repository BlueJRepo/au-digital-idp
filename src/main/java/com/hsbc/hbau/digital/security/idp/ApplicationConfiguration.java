package com.hsbc.hbau.digital.security.idp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan("com.hsbc.hbau.digital.security")
@EnableRedisRepositories(basePackages= {"com.hsbc.hbau.digital.security.idp", "com.hsbc.hbau.digital.security.idp.dao"})
public class ApplicationConfiguration {

}
