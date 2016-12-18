package com.wpw.dream.redis.reliablequeue;

import java.util.Hashtable;

import com.wpw.dream.redis.core.RedisClient;
import com.wpw.dream.redis.core.RedisClientConfig;
import com.wpw.dream.redis.core.RedisClientFactory;

/**
 * 可靠队列工厂类，负责生产可靠队列
 * @date 2014-7-17
 * @author rrx
 * 
 */
public class ReliableQueueFactory {

	/**
	 * 单例可靠队列
	 */
	private static Hashtable<String, ReliableQueue> singleReliableQueues = new Hashtable<String, ReliableQueue>();
	
	private static final byte[] LOCK = new byte[0];
	
	/**
	 * 
	 * 以单例形式返回可靠队列，此处的单例是指在ReliableQueueFactory范围内且相同的（queueName）
	 * @param queueName 队列名称
	 * @param config redis client配置信息
	 * @return
	 */
	public static ReliableQueue createReliableQueue(final String queueName, final RedisClientConfig config) {
		return createReliableQueue(queueName, RedisClientFactory.getClient(config));
	}
	
	/**
	 * 
	 * 以单例形式返回可靠队列，此处的单例是指在ReliableQueueFactory范围内且相同的（queueName）
	 * @param queueName 队列名称
	 * @param redisClient
	 * @return
	 */
	public static ReliableQueue createReliableQueue(final String queueName, final RedisClient redisClient) {
		ReliableQueue queue = singleReliableQueues.get(queueName);
		if (null == queue) {
			synchronized (LOCK) {
				queue = singleReliableQueues.get(queueName);
				if (queue == null) {
					queue = new ReliableQueue(queueName, redisClient);
					
					singleReliableQueues.put(queueName, queue);
				}
			}
		}
		return queue;
	}
}
