package com.wpw.dream.redis.reliablequeue;

/**
 * 可靠队列消费回调接口
 * @date 2014-7-17
 * @author rrx
 * 
 */
public interface ConsumeCallback {

	/**
	 * 
	 * 消费可靠队列提供的数据，并返回是否可以从队列中彻底移除数据
	 * @param queueName 可靠队列名称
	 * @param data 待处理的数据
	 * @return 是否可彻底移除数据
	 * 		   true:处理成功/没必要重试（譬如：数据格式错），false:处理失败需要重试
	 */
	boolean consume(String queueName, String data);
}
