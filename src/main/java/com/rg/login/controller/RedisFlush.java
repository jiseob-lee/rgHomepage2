package com.rg.login.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RedisFlush extends QuartzJobBean {
	
	private final Logger logger = LogManager.getLogger(RedisFlush.class);
	
	//@Autowired
	//private RedisTemplate<String, Object> redisTemplate;

	private ApplicationContext appCtx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		appCtx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");

        callService(context);
        
	}

	private void callService(JobExecutionContext context) {
		
		@SuppressWarnings("unchecked")
		RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>)appCtx.getBean("redisTemplate");
		
		redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
	}
}
