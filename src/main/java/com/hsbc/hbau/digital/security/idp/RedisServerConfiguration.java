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
import redis.embedded.RedisExecProvider;

import redis.embedded.util.OS;
import redis.embedded.util.OsArchitecture;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

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
	    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	    String redisUnixPath = environment.getProperty("redis.unix.path",String.class, "home/vcap/app/BOOT-INF/classes");
	   
	    if (!isWindows) {
		ProcessBuilder builder = new ProcessBuilder();
		//EXTRACT redis-5.0.2.tar.gz
		builder.command("sh", "-c", "tar -xvf redis-5.0.2.tar.gz");
                builder.directory(new File(redisUnixPath));
	    	Process process = builder.start();
	    	StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            	Executors.newSingleThreadExecutor().submit(streamGobbler);
            	int exitCode = process.waitFor();
		
		//BUILD RUNNING INSTANCE
		assert exitCode == 0;
		builder = new ProcessBuilder();
		builder.command("sh", "-c", "make");
		builder.directory(new File(redisUnixPath.concat("/redis-5.0.2")));
                process = builder.start();
		streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            	Executors.newSingleThreadExecutor().submit(streamGobbler);
            	exitCode = process.waitFor();
		assert exitCode == 0;		
	    }	

	    RedisExecProvider customProvider = 	RedisExecProvider.defaultProvider()
		.override(OS.UNIX, redisUnixPath.concat("/redis-5.0.2").concat("/src/redis-server"));            
            
	    redisServer = new RedisServer(customProvider, port);
            redisServer.start();
	    
  	    System.out.println("Starting local embedded redis server successfully, port is " + port);

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

private static class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;
 
    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }
 
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines()
          .forEach(consumer);
    }
}
    
}
