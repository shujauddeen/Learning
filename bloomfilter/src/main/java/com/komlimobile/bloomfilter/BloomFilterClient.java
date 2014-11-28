package com.komlimobile.bloomfilter;

import java.util.Date;
import java.util.Random;

public class BloomFilterClient {
	
	
	
	static int j=0;
	
	public static void main(String[] args) throws InterruptedException {
		
		Random rand = new Random();
		Date date = new Date();
		final  int ELEMENT_COUNT = 1000;
		final  double FALSE_POSITIVE_PROP = 0.001;
		Long delivery_time  = new Long(date.getTime()/1000);
		final BloomFilter<String> bf = new BloomFilter<String>(FALSE_POSITIVE_PROP, ELEMENT_COUNT);
		
		for(int i=0;i<1500;i++){
			int client_id = rand.nextInt(99999);
			int ad_id = rand.nextInt(99999);
			String ip_address = rand.nextInt(224)+ "." + rand.nextInt(224) + "." + rand.nextInt(224) + "." + rand.nextInt(224);
			String str = client_id + ad_id + ip_address + delivery_time + "komli"+i+rand.nextInt(500);
			
			if(!bf.contains(str)){
				bf.add(str);
			}
			else{
				j++;
			}
			
			
			//Thread.sleep(1);
		}
		System.out.println("number of false probability: " + j);
		System.out.println("number of entries: " + bf.count());
//		bfClient.get(client_id + ad_id + ip_address + delivery_time + "komli1");
//		bfClient.get(client_id + ad_id + ip_address + delivery_time + "komli10");
//		bfClient.get(client_id + ad_id + ip_address + delivery_time +"komli100875565");
//		bfClient.get(client_id + ad_id + ip_address + delivery_time +"komli1000545");
//		bfClient.get(client_id + ad_id + ip_address + delivery_time +"komli10000");
	}
	
	
}	