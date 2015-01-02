package com.shuja.redis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			
//	=================== Dump dummy data ===========================
//			FileInputStream fis = new FileInputStream(new File("/home/km/device_udids.csv"));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//		 	String line = null;
//			redis.getConnection();
//		 		int count = 0;
//				while ((line = br.readLine()) != null) {
//					count++;
//					for(int i = 0; i < 50; i++) {
//						line.replaceAll("\\s+","");
//						redis.set(line + i, "redis-response-" + i);
//					}
//					System.out.println(count);
//				}
//			redis.returnConnection();
//			br.close();
//			fis.close();
//		
//			System.out.println("Data populated...");
//			
			
//  =================== Read test data=============================			
//			Long start, end;
//			for(int y = 0; y < 100; y++){
//				Random rand = new Random();
//				int  n = rand.nextInt(46500);
//				String data = FileUtils.readLines(new File("/home/km/device_udids.csv")).get(n);
//				
//				
//				int  x = rand.nextInt(49);
//				System.out.println(data + x);
//				start = System.currentTimeMillis();
//				redis.getConnection();
//				System.out.println("response :" + redis.get(data + x));
//				redis.returnConnection();
//				end = System.currentTimeMillis();
//				System.out.println(end-start + " ms");
//			}
			

//			Long start, end;
//			for(int y = 0; y < 100; y++){
//				Random rand = new Random();
//				int  n = rand.nextInt(46500);
//				String data = FileUtils.readLines(new File("/home/km/device_udids.csv")).get(n);
//				int  x = rand.nextInt(49);
//				redis.getConnection();
//				System.out.println("response :" + redis.get(data + x));
//				redis.returnConnection();
//			}
			
			
			List<String> data = new ArrayList<String>();
			data.add("1223434234~3434");
			data.add("435434343~3434");
			data.add("3454434322~3434");
			data.add("1223434234~3434");
			data.add("1223434234~3434");
			data.add("2332453423~3434");
			data.add("1223434234~3434");
			data.add("1223434234~3434");
			data.add("2343423~3434");
			data.add("1223434234~3434");
			data.add("1223434234~3434");
			
			redis.getConnection();
			
			for(String d : data){
				String[] val = d.split("~");
				List<String> adList = redis.lrange(val[0], 0, -1);
				
				if(!adList.contains(val[1])){
					long check = redis.incr(val[0] + "~" + val[1]);
					
					if(check >= 5){
						System.out.println("Under if : " + val[0] + " : " + val[1]);
						redis.lpush(val[0], val[1]);
					} else{
						System.out.println("Under else : " + val[0] + " : " + val[1]);
					}
				}else{
					System.out.println("under main else");
				}
			}
			redis.returnConnection();
		} catch (Exception e) {
			redis.returnBrokenConnection();
			e.printStackTrace();
		}
		
		
	}

}
