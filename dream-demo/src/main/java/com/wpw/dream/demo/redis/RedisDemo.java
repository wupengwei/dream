package com.wpw.dream.demo.redis;

import com.wpw.dream.redis.core.RedisClient;
import com.wpw.dream.redis.core.RedisClientConfig;
import com.wpw.dream.redis.core.RedisClientFactory;

public class RedisDemo {
	
	public static void main(String[] args) {
		RedisClientConfig config = new RedisClientConfig();
		config.setHost("127.0.0.1");
		config.setPort(6379);
		RedisClient client = RedisClientFactory.getClient(config);
		
		client.set("a", "aaa");
		String string = client.get("a");
		System.out.println(string);
		
	}

}
