package com.netty.learning;

import com.kafka.learning.KafkaProducer;

import io.netty.handler.codec.http.HttpRequest;

public class ProcessRequest {

	HttpRequest req = null;
	public ProcessRequest(HttpRequest req){
		this.req = req;
	}
	
	public byte[] process(){
		String ua = this.req.headers().getHeader(req, "User-Agent");
		KafkaProducer kafka = new KafkaProducer();
		kafka.produce(ua);
		System.out.println(ua);
		return ua.getBytes();
	}
}
