package com.wpw.dream.redis.core;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis client工厂类
 * @date 2014-7-8
 * @author rrx
 * 
 */
public class RedisClientFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisClientFactory.class);
	
	/**
	 * 单例客户端
	 */
	private static Hashtable<String, RedisClient> singleClients = new Hashtable<String, RedisClient>();
	
	private static final byte[] LOCK = new byte[0];
	
	/**
	 * 
	 * 以单例形式返回redis client，此处的单例是指在RedisClientFactory范围内且相同的（host:6379）
	 * @param config
	 * @return
	 */
	public static RedisClient getClient(String redisHost) {
		RedisClientConfig config = new RedisClientConfig();
		config.setHost(redisHost);
		
		return getClient(config);
	}
	
	/**
	 * 
	 * 以单例形式返回redis client，此处的单例是指在RedisClientFactory范围内且相同的（host:port）
	 * @param config
	 * @return
	 */
	public static RedisClient getClient(String redisHost, int redisPort) {
		RedisClientConfig config = new RedisClientConfig();
		config.setHost(redisHost);
		config.setPort(redisPort);
		
		return getClient(config);
	}
	
	/**
	 * 
	 * 以单例形式返回redis client，此处的单例是指在RedisClientFactory范围内且相同的（host:port）
	 * @param config
	 * @return
	 */
	public static RedisClient getClient(RedisClientConfig config) {
		String tag = config.getHost() + ":" + config.getPort();
		
		logger.debug("获取连接到\"{}\"的redis客户端", tag);
		
		RedisClient client = singleClients.get(tag);
		if (null == client) {
			synchronized (LOCK) {
				client = singleClients.get(tag);
				if (client == null) {
					client = createRedisClient(config);
					
					singleClients.put(tag, client);
				}
			}
		}
		return client;
	}
	
	/**
	 * 
	 * 使用指定的配置信息创建redis client实例
	 * @param clientConfig
	 * @return
	 */
	private static RedisClient createRedisClient(RedisClientConfig clientConfig) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(clientConfig.getMaxIdle());
		poolConfig.setMaxTotal(clientConfig.getMaxTotal());
		poolConfig.setMaxWaitMillis(clientConfig.getMaxWaitMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(-1);
		//取连接之前先检查，如果断了再从池中取出一个
		poolConfig.setTestOnBorrow(true);

		// 创建 jedis pool
		JedisPool jedisPool = new JedisPool(poolConfig, clientConfig.getHost(), clientConfig.getPort(), clientConfig.getTimeout());
		
		return new RedisClient(jedisPool);
	}
}
