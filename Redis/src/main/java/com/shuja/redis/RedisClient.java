package com.shuja.redis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;



public class RedisClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String propertyFile = "settings.properties";
		String confDir = "/home/km/JavaWorkspace/Learning/Redis/src/main/resources/conf";
		Redis redis = null;
		try {
			
			final File confFile = new File(confDir, propertyFile);
			final PropertiesConfiguration config = new PropertiesConfiguration(confFile);
			
			redis = new Redis(config);
			
			
//			boolean x = redis.set("12345", "4587");
			
			
//			FileInputStream fis = new FileInputStream(new File("/home/km/device_udids.csv"));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//		 	String line = null;
//						
//		 		int count = 0;
//				while ((line = br.readLine()) != null) {
//					count++;
//					for(int i = 0; i < 100; i++) {
//						line.replaceAll("\\s+","");
//						redis.set(line + i, "redis-response-" + i);
//					}
//					System.out.println(count);
//				}
//			
//			br.close();
//			fis.close();
//		
//			System.out.println("Data populated...");
			Long start, end;
			for(int y = 0; y < 100; y++){
				Random rand = new Random();
				int  n = rand.nextInt(46500);
				String data = FileUtils.readLines(new File("/home/km/device_udids.csv")).get(n);
				
				
				int  x = rand.nextInt(99);
				System.out.println(data + x);
				start = System.currentTimeMillis();
				redis.getConnection();
				System.out.println("response :" + redis.get(data + x));
				redis.returnConnection();
				end = System.currentTimeMillis();
				System.out.println(end-start + " ms");
			}
			
			
			

//			redis.set("12341234234-3242", 1);
//			System.out.println(redis.incr("12341234234-3242"));
//			System.out.println(redis.incr("12341234234-3242"));
//			System.out.println(redis.incr("12341234234-3242"));
//			System.out.println(redis.incr("12341234234-3242"));
			
			
		} catch (Exception e) {
			redis.returnBrokenConnection();
			e.printStackTrace();
		}
		
		
	}

}
