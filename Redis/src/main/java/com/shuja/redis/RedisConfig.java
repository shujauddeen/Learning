package com.shuja.redis;

public class RedisConfig {

	String host = null;
	int port;
	String auth;
	
	public RedisConfig(String host, int port, String auth){
		this.host = host;
		this.port = port; 
		this.auth = auth;
	}
	
	
	public String getHost(){
		return this.host;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public String getAuthToken(){
		return this.auth;
	}
}
