package com.cqsd.command.cache.config;

import com.cqsd.command.utils.builder.Builder;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author caseycheng
 * @date 2022/11/23-21:02
 **/
abstract public class RedisConn {
	public static final String DEFALT_HOST ="127.0.0.1";
	public static final Integer DEFALT_PORT =6379;
	public RedisConn() {
		final var config = Builder.builder(JedisPoolConfig::new)
				.with(JedisPoolConfig::setMaxIdle, 8)
				.with(JedisPoolConfig::setMaxTotal, 18)
				.builder();
		final var pool = new JedisPool(config, DEFALT_HOST, DEFALT_PORT,"default", "5201314zFy@");
		
	}
}
