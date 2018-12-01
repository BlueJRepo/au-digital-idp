package com.hsbc.hbau.digital.security.idp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

import java.io.IOException;

@Component
public class RedisServerConfiguration implements DisposableBean, EnvironmentAware, InitializingBean {
	
    private Log log = LogFactory.getLog(this.getClass());
    private RedisServer redisServer;
    private Environment environment;
    
    public int getPort() {
        int v = environment.getProperty("spring.redis.port",Integer.class,0);
        v = v == 0 ? environment.getProperty("global.redis.port",Integer.class,6379) : v;
        return v;
    }
    
    private boolean isEmbedded() {
        Boolean v = environment.getProperty("spring.redis.embedded",Boolean.class,null);
        v = v == null ? environment.getProperty("global.redis.embedded",Boolean.class,false) : v;
        return v;
    }
    
    @Override
    public void destroy() throws Exception {
        if(redisServer != null) {
            redisServer.stop();
            redisServer = null;
        }
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!isEmbedded()) {
            return;
        }

        try {
            int port = getPort();
            redisServer = new RedisServer(port);
            redisServer.start();

            if(log.isInfoEnabled()) {
                log.info("Starting local embedded redis server successfully, port is " + port);
            }
        } catch (IOException e) {
            throw new FatalBeanException("Failed to start local embedded redis server ", e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
}
