package com.shuja.redis;

import java.net.URISyntaxException;

import org.apache.commons.configuration.Configuration;

import redis.clients.jedis.Jedis;

public class Redis {

	
	private final Jedis jedis;
    private Configuration config;

    //    private final Connection connection;
    public Redis(Configuration config) throws Exception{
        //Get the configuration object and use it
        this.config = config;
        jedis = ConnectionFactory.getInstance(this.config).getConnection();
    }
    
    

    public <T> boolean set(T key, T value) {
      
        final String ret = this.jedis.set(key.toString(),  value.toString());
        if (ret != null)
            return true;
        else
            return false;
    }
    
    
    public Long incr(String key){
    	if (key == null || key.isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.incr(key);
    }
    
    
    public <T> Long incrBy(T key, T value){
    	if (key == null || key.toString().isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.incrBy(key.toString(), Integer.parseInt(value.toString()));
    }
    
    
    public String get(String key) {
        if (key == null || key.isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.get(key);
    }

    
    public Long del(String key) {
        if (key == null || key.isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.del(key);
    }
    
    
    public <T> Long expire(T key, T value) {
        if (key == null || key.toString().isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.expire(key.toString(), Integer.parseInt(value.toString()));
    }
    
    
}
