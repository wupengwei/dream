package com.wpw.dream.redis.core.action;

import redis.clients.jedis.Jedis;

/**
 * 有返回结果的回调接口定义。
 * @date 2014-7-7
 * @author rrx
 * 
 */
public interface JedisAction<T> {
	/**
	 * 
	 * 使用指定的jedis执行对缓存数据的操作，多用于查询
	 * @param jedis
	 * @return
	 */
	T action(Jedis jedis);
}
