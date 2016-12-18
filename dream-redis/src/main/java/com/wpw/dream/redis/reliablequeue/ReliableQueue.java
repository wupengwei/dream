package com.wpw.dream.redis.reliablequeue;

import com.wpw.dream.redis.core.RedisClient;

/**
 * 可靠队列
 * @date 2014-7-17
 * @author rrx
 * 
 */
public class ReliableQueue {
	
	/**
	 * 可靠列队名称（标识）
	 */
	private String queueName;
	/**
	 * redis客户端
	 */
	private RedisClient client;
	
	private ConsumerThread consumerThread;
	
	/**
	 * 
	 * @Description：构造可靠队列
	 * @param queueName
	 * @param client
	 */
	ReliableQueue(String queueName, RedisClient client) {
		this.queueName = queueName;
		this.client = client;
		
		consumerThread = new ConsumerThread(queueName, client);
	}

	/**
	 * 
	 * 向可靠队列中push数据
	 * @param data
	 */
	public void push(final String... data) {
		this.client.lpush(queueName, data);
	}
	
	/**
	 * 
	 * 开启消费线程，重复调用该方法无效
	 * @param callback 业务回调实现
	 */
	public void startConsume(final ConsumeCallback callback) {
		consumerThread.start(callback, ConsumerThread.DEFAULT_MONITOR_PERIOD);
	}
	
	/**
	 * 
	 * 开启消费线程，重复调用该方法无效
	 * @param callback 业务回调实现
	 * @param monitorPeriod 监控周期，单位为毫秒，默认为60000毫秒（1分钟），必须界于10秒和3分钟之间
	 */
	public void startConsume(final ConsumeCallback callback, final long monitorPeriod) {
		consumerThread.start(callback, monitorPeriod);
	}
}
