package com.shuja.redis;


import java.util.List;


import redis.clients.jedis.Jedis;

public class Redis {

	
    Jedis jedis = null;
    public Redis(Jedis jed){
        jedis = jed;       
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
    
    
    public <T> void lpush(T key, T value) {
        if (key == null || key.toString().isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
         this.jedis.lpush(key.toString(), value.toString());
         
    }
    
    public <T> List<String> lrange(T key, long firstIndix, long lastIndex) {
        if (key == null || key.toString().isEmpty())
            throw new NullPointerException("The key cannot be null or empty.");
        return this.jedis.lrange(key.toString(), firstIndix, lastIndex);
    }
    
//    public <T> List<String> mget(String key) {
//        if (key == null || key.toString().isEmpty())
//            throw new NullPointerException("The key cannot be null or empty.");
//        return this.jedis.mget(key);
//         
//    } 
}
