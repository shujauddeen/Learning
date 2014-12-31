package com.shuja.redis;

import org.apache.commons.configuration.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectionFactory {

	private static ConnectionFactory conn = null;
	private Jedis jedis;
	private JedisPoolConfig poolConfig = new JedisPoolConfig();
	private JedisPool connectionPool;
	private String auth;
	
	public ConnectionFactory(Configuration conf) {
		try{
			this.auth = conf.getString("authToken");
			poolConfig.setMaxIdle(conf.getInt("maxIdleConnection"));
	        poolConfig.setMinIdle(conf.getInt("minIdleConnection"));
	        poolConfig.setTestOnBorrow(conf.getBoolean("testOnBorrow"));
	        connectionPool = new JedisPool(poolConfig, conf.getString("host"), conf.getInt("port"));
	        System.out.println("intermedate");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Jedis getConnection() throws Exception {
		try {
			jedis = connectionPool.getResource();
			jedis.auth(this.auth);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return jedis;
	}

	public static ConnectionFactory getInstance(Configuration conf)
			throws Exception {
		if (conn == null) {
			conn  = new ConnectionFactory(conf);
			
		}
		return conn;
	}
}
