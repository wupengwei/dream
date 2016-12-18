package com.wpw.dream.redis.core;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * redis client配置参数类
 * 
 * @date 2014-7-9
 * @author Administrator
 * 
 */
public class RedisClientConfig {

	/**
	 * redis服务地址，默认为localhost
	 */
	private String host = "localhost";
	/**
	 * redis服务端口，默认为6379
	 */
	private int port = Protocol.DEFAULT_PORT;
	/**
	 * redis连接超时时间，默认为2000毫秒
	 */
	private int timeout = Protocol.DEFAULT_TIMEOUT;
	/**
	 * 最大空闲连接数，默认为20
	 */
	private int maxIdle = 20;
	/**
	 * 最大连接数，默认为200
	 */
	private int maxTotal = 200;
	/**
	 * 最大建立连接等待时间，毫秒值，默认为-1（不限制）
	 */
	private long maxWaitMillis = JedisPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	/**
	 * @return 返回{@linkplain #host}
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            要设置的 host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return 返回{@linkplain #port}
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            要设置的 port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return 返回{@linkplain #timeout}
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            要设置的 timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return 返回{@linkplain #maxIdle}
	 */
	public int getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle 要设置的 maxIdle
	 */
	public void setMaxIdle(String maxIdle) {
		if (StringUtils.isNumeric(maxIdle)) {
			this.maxIdle = Integer.parseInt(maxIdle);
		}
	}

	/**
	 * @return 返回{@linkplain #maxTotal}
	 */
	public int getMaxTotal() {
		return maxTotal;
	}

	/**
	 * @param maxTotal 要设置的 maxTotal
	 */
	public void setMaxTotal(String maxTotal) {
		if (StringUtils.isNumeric(maxTotal)) {
			this.maxTotal = Integer.parseInt(maxTotal);
		}
	}

	/**
	 * @return 返回{@linkplain #maxWaitMillis}
	 */
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	/**
	 * @param maxWaitMillis 要设置的 maxWaitMillis
	 */
	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

}
