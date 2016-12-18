package com.wpw.dream.redis.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpw.dream.redis.core.action.JedisAction;
import com.wpw.dream.redis.core.action.JedisActionNoResult;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

/**
 * redis客户端操作类
 * @date 2014-7-7
 * @author 
 * 
 */
public class RedisClient {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Pool<Jedis> jedisPool;
	
	public RedisClient(final Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 
	 * 执行有返回结果的action
	 * @param jedisAction
	 * @return
	 * @throws JedisException
	 */
	public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedisAction.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error("redis连接无效", e);
			
			broken = true;
			
			throw e;
		} finally {
			returnResource(jedis, broken);
		}
	}

	/**
	 * 
	 * 执行无返回结果的action
	 * @param jedisAction
	 * @throws JedisException
	 */
	public void execute(JedisActionNoResult jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedisAction.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error("Redis连接无效", e);
			
			broken = true;
			
			throw e;
		} finally {
			returnResource(jedis, broken);
		}
	}
	
	/**
	 * 
	 * 判断redis服务是否正在运行
	 * @return true:正在运行；false:未运行
	 */
	public boolean isRunning() {
		try {
			return execute(new JedisAction<Boolean>() {
	
				@Override
				public Boolean action(Jedis jedis) {
					String result = jedis.ping();
					return "OK".equals(result) || "PONG".equals(result);
				}
			});
		} catch(Exception e) {
			logger.warn("检查redis服务状态失败");
			
			return false;
		}
	}
	
	/**
	 * 
	 * 清空redis
	 */
	public void flushDB() {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.flushDB();
			}
		});
	}
	
	/**
	 * 
	 * 删除keys，如果指定key不存在，则直接忽略
	 * @param keys
	 * @return 实际删除的数量
	 */
	public Long del(final String... keys) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}
	
	/**
	 * 
	 * 设定该Key持有指定的字符串Value，如果该Key已经存在，则覆盖其原有值。
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		execute(new JedisActionNoResult() {
			
			@Override
			public void action(Jedis jedis) {
				jedis.set(key, value);
			}
		});
	}
	
	/**
	 * 
	 * 从redis中获取指定key对应的内容，如果key不存在, 返回null
	 * 如果与该Key关联的Value不是string类型，Redis将返回错误信息，因为GET命令只能用于获取string Value
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	
	/**
	 * 
	 * 原子性的设置该Key为指定的Value，同时返回该Key的原有值。
	 * 如果与该Key关联的Value不是string类型，Redis将返回错误信息，因为GET命令只能用于获取string Value
	 * @param key
	 * @param value 新值
	 * @return 原值
	 */
	public String getSet(final String key, final String value) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.getSet(key, value);
			}
		});
	}
	
	/**
	 * 
	 * 原子性完成两个操作，一是设置该Key的值为指定字符串，同时设置该Key在Redis服务器中的存活时间(秒数)
	 * 该命令主要应用于Redis被当做Cache服务器使用时
	 * @param key
	 * @param seconds 设置的值的存活时间
	 * @param value
	 */
	public void setex(final String key, final int seconds, final String value) {
		execute(new JedisActionNoResult() {
			
			@Override
			public void action(Jedis jedis) {
				jedis.setex(key, seconds, value);
			}
		});
	}
	
	/**
	 * 
	 * 如果指定的Key不存在，则设定该Key持有指定字符串Value，此时其效果等价于SET命令。相反，如果该Key已经存在，该命令将不做任何操作并返回。
	 * @param key
	 * @param value
	 * @return true:已设置为新值，false:未设置为新值
	 */
	public boolean setnx(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.setnx(key, value) == 1;
			}
		});
	}
	
	/**
	 * 
	 * 该命令原子性的完成参数中所有key/value的设置操作，其具体行为可以看成是多次迭代执行SET命令
	 * @param keysvalues 例：mset("name", "zs", "age", "30")等同于以下调用
	 * 						set("name", "zs");
	 * 						set("age", "30");
	 */
	public void mset(final String... keysvalues) {
		execute(new JedisActionNoResult() {
			
			@Override
			public void action(Jedis jedis) {
				jedis.mset(keysvalues);
			}
		});
	}
	
	/**
	 * 
	 * 返回所有指定Keys的Values，如果其中某个Key不存在，或者其值不为string类型，该Key的Value将返回nil
	 * @param keys
	 * @return
	 */
	public List<String> mget(final String... keys) {
		return execute(new JedisAction<List<String>>() {

			@Override
			public List<String> action(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
	}
	
	/**
	 * 
	 * 判断指定的key是否存在
	 * @param key
	 * @return true:存在；false:不存在
	 */
	public boolean exists(final String key) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}
	
	/**
	 * 
	 * 在指定Key所关联的List Value的头部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * @param key
	 * @param values
	 * @return 插入后链表中元素的数量。
	 */
	public Long lpush(final String key, final String... values) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.lpush(key, values);
			}
		});
	}
	
	/**
	 * 
	 * 在指定Key所关联的List Value的尾部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的尾部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * @param key
	 * @param values
	 * @return 插入后链表中元素的数量。
	 */
	public Long rpush(final String key, final String... values) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.rpush(key, values);
			}
		});
	}
	
	/**
	 * 
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素
	 * 如果该Key不存，返回nil
	 * @param key
	 * @return
	 */
	public String lpop(final String key) {
		return execute(new JedisAction<String>() {
			
			@Override
			public String action(Jedis jedis) {
				return jedis.lpop(key);
			}
		});
	}
	
	/**
	 * 
	 * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素
	 * 如果该Key不存，返回nil
	 * @param key
	 * @return
	 */
	public String rpop(final String key) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}
	
	/**
	 * 
	 * 返回List长度，key不存在时返回0，key类型不是list时抛出异常
	 * @param key
	 * @return
	 */
	public Long llen(final String key) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	/**
	 * 
	 *  删除List中第一个等于value的元素，value不存在或key不存在时返回false
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean lremOne(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.lrem(key, 1, value) != 0;
			}
		});
	}
	
	/**
	 * 
	 *  删除List中最后一个等于value的元素，value不存在或key不存在时返回false
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean rremOne(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.lrem(key, -1, value) != 0;
			}
		});
	}

	/**
	 * 
	 * 删除List中所有等于value的元素，value不存在或key不存在时返回false
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean remAll(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.lrem(key, 0, value) != 0;
			}
		});
	}
	
	/**
	 * 
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
	 * 参数 start 和 end 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 示例：lrange("l_1", 0, -1)返回所有
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(final String key, final long start, final long end) {
		return execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}
	
	/**
	 * 
	 * 以阻塞的形式从srckey指定的list尾部取出一个元素并push到dstkey指定的list的头部，保证数据在处理成功之前不会丢失
	 * 该方法常用于阻塞的先进（必须从头部进）先出队列
	 * @param srckey
	 * @param dstkey
	 * @return
	 */
	public String brpoplpush(final String srckey, final String dstkey) {
		return execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.brpoplpush(srckey, dstkey, 0);
			}
		});
	}
	
	/**
	 * 
	 * 为指定的Key设定Field/Value对，如果Key不存在，该命令将创建新Key以参数中的Field/Value对，
	 * 如果参数中的Field在该Key中已经存在，则用新值覆盖其原有值。
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(final String key, final String field, final String value) {
		execute(new JedisActionNoResult() {
			
			@Override
			public void action(Jedis jedis) {
				jedis.hset(key, field, value);
			}
		});
	}
	
	/**
	 * 
	 * 返回指定Key中指定Field的关联值
	 * @param key
	 * @param field
	 * @return 如果参数中的Key或Field不存在，返回null。
	 */
	public String hget(final String key, final String field) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}
	
	/**
	 * 
	 * 判断指定Key中的指定Field是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	public boolean hexists(final String key, final String field) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.hexists(key, field);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key所包含的field的数量
	 * @param key
	 * @return key不存在则返回0
	 */
	public Long hlen(final String key) {
		return execute(new JedisAction<Long>() {
			
			@Override
			public Long action(Jedis jedis) {
				return jedis.hlen(key);
			}
		});
	}
	
	/**
	 * 
	 * 从指定Key的Hashes Value中删除参数中指定的多个字段，不存在的字段将被忽略
	 * 如果Key不存在，则将其视为空Hashes，并返回0
	 * 如果fields未传值，则返回0
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(final String key, final String... fields) {
		return execute(new JedisAction<Long>() {
			
			@Override
			public Long action(Jedis jedis) {
				return jedis.hdel(key, fields);
			}
		});
	}
	
	/**
	 * 
	 * 返回指定Key的所有Fields名
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(final String key) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.hkeys(key);
			}
		});
	}
	
	/**
	 * 
	 * 逐对依次设置参数中给出的Field/Value对。
	 * 如果其中某个Field已经存在，则用新值覆盖原有值。
	 * 如果Key不存在，则创建新Key，同时设定参数中的Field/Value。
	 * 如果hash为空，则抛出异常
	 * @param key
	 * @param hash
	 */
	public void hmset(final String key, final Map<String, String> hash) {
		execute(new JedisActionNoResult() {
			
			@Override
			public void action(Jedis jedis) {
				jedis.hmset(key, hash);
			}
		});
	}
	
	/**
	 * 
	 * 获取和参数中指定Fields关联的一组Values。
	 * 如果请求的Field不存在，其值返回null。
	 * 如果参数fields未设置值，则返回空list
	 * 如果Key不存在，该命令将其视为空Hash，因此返回一组null。
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> hmget(final String key, final String... fields) {
		return execute(new JedisAction<List<String>>() {
			
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
	}
	
	/**
	 * 
	 * 获取该键包含的所有Field/Value
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(final String key) {
		return execute(new JedisAction<Map<String, String>>() {
			
			@Override
			public Map<String, String> action(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}
	
	/**
	 * 
	 * 批量添加参数中指定的所有成员及其分数到指定key的Sorted-Set中
	 * @param key
	 * @param members Map<成员, 分数>
	 * @return 实际插入的成员数量
	 */
	public void zadd(final String key, final Map<String, Double> members) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.zadd(key, members);
			}
		});
	}
	
	/**
	 * 
	 * 添加/更新参数中指定的所有成员及其分数到指定key的Sorted-Set中
	 * @param key
	 * @param member 成员
	 * @param score 分数
	 */
	public void zaddOrUpdate(final String key, final String member, final double score) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.zadd(key, score, member);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中min<=score<=max的所有成员
	 * @param key
	 * @param min 最小分数
	 * @param max 最大分数
	 * @return
	 */
	public Set<String> zrangeGteMinLteMax(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中min<=score<max的所有成员
	 * @param key
	 * @param min 最小分数
	 * @param max 最大分数
	 * @return
	 */
	public Set<String> zrangeGteMinLtMax(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrangeByScore(key, String.valueOf(min), "(" + max);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中min<score<=max的所有成员
	 * @param key
	 * @param min 最小分数
	 * @param max 最大分数
	 * @return
	 */
	public Set<String> zrangeGtMinLteMax(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrangeByScore(key, "(" + min, String.valueOf(max));
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中min<score<max的所有成员
	 * @param key
	 * @param min 最小分数
	 * @param max 最大分数
	 * @return
	 */
	public Set<String> zrangeGtMinLtMax(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrangeByScore(key, "(" + min, "(" + max);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中所有大于score的成员
	 * @param key
	 * @param score
	 * @return
	 */
	public Set<String> zrangeGtScore(final String key, final double score) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				double max = 0;
				
				Set<Tuple> set = jedis.zrangeWithScores(key, -1, -1);
				if (null != set && set.iterator().hasNext()) {
					max = set.iterator().next().getScore();
				}
				
				return jedis.zrangeByScore(key, "(" + score, String.valueOf(max));
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中所有大于等于score的成员
	 * @param key
	 * @param score
	 * @return
	 */
	public Set<String> zrangeGteScore(final String key, final double score) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				double max = 0;
				
				Set<Tuple> set = jedis.zrangeWithScores(key, -1, -1);
				if (null != set && set.iterator().hasNext()) {
					max = set.iterator().next().getScore();
				}
				
				return jedis.zrangeByScore(key, score, max);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中所有小于score的成员
	 * @param key
	 * @param score
	 * @return
	 */
	public Set<String> zrangeLtScore(final String key, final double score) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				double min = 0;
				
				Set<Tuple> set = jedis.zrangeWithScores(key, 0, 0);
				if (null != set && set.iterator().hasNext()) {
					min = set.iterator().next().getScore();
				}
				
				return jedis.zrangeByScore(key, String.valueOf(min), "(" + score);
			}
		});
	}
	
	/**
	 * 
	 * 获取指定key的Sorted-Set中所有小于等于score的成员
	 * @param key
	 * @param score
	 * @return
	 */
	public Set<String> zrangeLteScore(final String key, final double score) {
		return execute(new JedisAction<Set<String>>() {
			
			@Override
			public Set<String> action(Jedis jedis) {
				double min = 0;
				
				Set<Tuple> set = jedis.zrangeWithScores(key, 0, 0);
				if (null != set && set.iterator().hasNext()) {
					min = set.iterator().next().getScore();
				}
				
				return jedis.zrangeByScore(key, min, score);
			}
		});
	}
	
	/**
	 * 
	 * 根据连接是否已中断的标志，分别调用returnBrokenResource或returnResource返回jedis到池中
	 * @param jedis
	 * @param connectionBroken 连接是否已中断
	 */
	private void returnResource(Jedis jedis, boolean connectionBroken) {
		if (jedis != null) {
			try {
				if (connectionBroken) {
					jedisPool.returnBrokenResource(jedis);
				} else {
					jedisPool.returnResource(jedis);
				}
			} catch (Exception e) {
				logger.error("返回jedis到池中时出现错误，尝试关闭它。", e);
				closeJedis(jedis);
			}
		}
	}
	
	/**
	 * 
	 * 关闭jedis
	 * @param jedis
	 */
	private void closeJedis(Jedis jedis) {
		if ((jedis != null) && jedis.isConnected()) {
			try {
				try {
					jedis.quit();
				} catch (Exception e) {
					logger.error("关闭jedis（调用jedis.quit()）时出现异常", e);
				}
				jedis.disconnect();
			} catch (Exception e) {
				logger.error("关闭jedis（调用jedis.disconnect()）时出现异常", e);
			}
		}
	}
}
