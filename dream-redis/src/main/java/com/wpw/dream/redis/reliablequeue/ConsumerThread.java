package com.wpw.dream.redis.reliablequeue;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import com.wpw.dream.redis.core.RedisClient;
import com.wpw.dream.redis.core.action.JedisActionNoResult;

/**
 * 可靠队列消费线程
 * @date 2014-7-17
 * @author rrx
 * 
 */
class ConsumerThread implements Runnable {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 代表空值
	 */
	private static final String NULL_VALUE = "_nil_";
	
	/**
	 * 可靠队列名称（唯一标识）
	 */
	private String queueName;
	/**
	 * 可靠队列备份名称（唯一标识）
	 */
	private String queueNameBak;
	/**
	 * redis客户端
	 */
	private RedisClient redisClient;
	/**
	 * 消费回调
	 */
	private ConsumeCallback callback;
	/**
	 * 消费线程是否正在运行
	 */
	private boolean isRunning = false;
	
	ConsumerThread(String queueName, RedisClient redisClient) {
		this.queueName = queueName;
		
		//TODO 后续需要扩展，使用其支持多消费线程
		this.queueNameBak = queueName + "_bak";
		
		this.redisClient = redisClient;
	}

	@Override
	public void run() {
		isRunning = true;
		while(isRunning) {
			try {
				String data = redisClient.brpoplpush(queueName, queueNameBak);
				if (StringUtils.isNotBlank(data)) {
					//向备份队列插入"_nil_"表示从可靠队列取出的数据尚未处理完成
					redisClient.lpush(queueNameBak, NULL_VALUE);
					if (callback.consume(queueName, data)) {
						//数据处理成功，从备份队列头部彻底删除数据
						redisClient.execute(new JedisActionNoResult() {
							
							@Override
							public void action(Jedis jedis) {
								jedis.ltrim(queueNameBak, 2, -1);
							}
						});
					} else {
						//从备份队列头部清除元素"_nil_"表示数据已经处理但处理失败，后续由监控任务进行尝试处理
						redisClient.lpop(queueNameBak);
					}
				} else {
					//从备份队列删除无效数据
					redisClient.lpop(queueNameBak);
				}
			} catch(Throwable t) {
				//此处捕获异常，避免消息线程终止
				logger.error("可靠队列{}消费过程中出现异常：{}", queueName, t.getMessage());
			} finally {
				//每次处理从可靠队列中取出数据处理后，均需要从备份队列中删除所有的"_nil_"
				try {
					redisClient.remAll(queueNameBak, NULL_VALUE);
				} catch (Throwable t) {
					logger.warn("从可靠队列{}中删除所有的\"_nil_\"失败，下次操作可靠队列时会继续删除", queueName);
				}
			}
		}
	}
	
	/**
	 * 默认监控周期（1分钟）
	 */
	final static long DEFAULT_MONITOR_PERIOD = 60000;

	/**
	 * 
	 * 启动消费线程
	 * @param callback 消费回调实现
	 * @param monitorPeriod 监控周期，单位为毫秒，默认为60000毫秒（1分钟）
	 */
	void start(ConsumeCallback callback, long monitorPeriod) {
		if (!isRunning) {
			this.callback = callback;
			
			Executors.newSingleThreadExecutor().execute(this);
			
			this.startMonitor(monitorPeriod);
		}
	}
	
	/**
	 * 
	 * 启动可靠队列监控任务
	 */
	private void startMonitor(long monitorPeriod) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				List<String> datas = Collections.emptyList();
				try {
					//取出所有数据
					datas = redisClient.lrange(queueNameBak, 0, -1);
				} catch(JedisException je) {
					logger.error("从缓存中加载数据失败", je);
				}
				
				if (!datas.isEmpty()) {
					//代表最后一个要处理数据的序号，如果最后一个要处理的数据是"_nil_"，表示前一个数据尚未处理完成，此时不需要再次进行处理
					int endIndex = NULL_VALUE.equals(datas.get(0)) ? 2 : 0;
					
					String data = null;
					//倒序，按照数据入队列的顺序（先进先出）进行处理
					for (int index = datas.size() - 1; index >= endIndex; index--) {
						data = datas.get(index);
						try {
							if (StringUtils.isBlank(data) || NULL_VALUE.equals(data) || callback.consume(queueName, data)) {
								redisClient.rremOne(queueNameBak, data);
							}
						} catch(Throwable t) {
							//此处捕获异常，避免终止对其他数据的处理
							logger.error("可靠队列{}重试消费过程中出现异常：{}", queueName, t.getMessage());
						}
					}
				}
			}
		}, 0, (monitorPeriod < 10000 || monitorPeriod > 180000) ? DEFAULT_MONITOR_PERIOD : monitorPeriod);
	}
}
