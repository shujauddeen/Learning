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
			System.out.println(conf.getInt("maxIdleConnection"));
			System.out.println(conf.getInt("minIdleConnection"));
			this.auth = conf.getString("authToken");
			System.out.println("hello");
			poolConfig.setMaxIdle(10);
	        poolConfig.setMinIdle(1);
	        System.out.println("hai");
	        poolConfig.setTestOnBorrow(conf.getBoolean("testOnBorrow"));
	        System.out.println("intermediat22");
	        connectionPool = new JedisPool(poolConfig, conf.getString("host"), conf.getInt("port"));
	        System.out.println("intermedate");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString() + "    dfaSFD ");
		}
		
	}
	
	public Jedis getConnection() throws Exception {
		try {
			System.out.println("check1");
			jedis = connectionPool.getResource();
			System.out.println("check2");
			jedis.auth(this.auth);
			System.out.println("check3");
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
