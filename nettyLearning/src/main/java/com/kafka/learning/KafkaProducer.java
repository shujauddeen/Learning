package com.kafka.learning;

import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
    
	public void produce(String ua){
		System.out.println("KafkaProducer : " + ua);

	    Properties props = new Properties();
	    props.put("metadata.broker.list", "20.20.20.21:9092"); 	
//	    props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("partitioner.class", "com.kafka.learning.SimplePartitioner");
	    props.put("request.required.acks", "1");
	    
	    props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("zk.connect", "20.20.20.21:2181");
	    
		ProducerConfig config = new ProducerConfig(props);
	    Producer<String, String> producer = new Producer<String, String>(config);
	           String ip = "useragent"; 
	           String msg = ua; 
	           KeyedMessage<String, String> data = new KeyedMessage<String, String>("CASSANDRA", ip, msg);
	           
	           producer.send(data);
	    
	    producer.close();
	}
}
