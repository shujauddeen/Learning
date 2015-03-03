package com.shuja.redis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.commons.configuration.PropertiesConfiguration;

import redis.clients.jedis.Jedis;



public class RedisClient extends Thread{

	/**
	 * @param args
	 * @throws Exception 
	 */
	ConnectionFactory conn = null;
	File confFile = null; 
 	File devfile = null;
 	PropertiesConfiguration config = null;
	
 	
 	RedisClient(String id){
		super(id);
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			devfile = new File(classLoader.getResource("conf/device_udids.csv").getFile());
			conn = ConnectionFactory.getInstance(new PropertiesConfiguration(new File(classLoader.getResource("conf/settings.properties").getFile())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		
	

		Redis redis = null;
		Jedis jedis = null;
		
		try {
			
		
		
			
			
//	=================== Dump dummy data ===========================
			FileInputStream fis = new FileInputStream(devfile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 	String line = null;
		 	jedis = conn.getConnection();
			redis = new Redis(jedis);
			Random rand = new Random();
			int  n = rand.nextInt(1000);
			System.out.println("rand : " + n);
			String name = getName();
			int count = 0;
				while ((line = br.readLine()) != null) {
						line.replaceAll("\\s+","");
						redis.set(line + n, "redis-response-" + count);
						count++;
						System.out.println(name + ": count :" + count);
				}
			conn.returnConnection(jedis);
			redis = null;
			br.close();
			fis.close();
//		
			System.out.println("Data populated...");
			
			
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
			
	
//			TEST READ DATA
			
//			List<String> data = new ArrayList<String>();
//			data.add("1223434234~3434");
//			data.add("435434343~3434");
//			data.add("3454434322~3434");
//			data.add("1223434234~3434");
//	
			
//			redis.getConnection();
			
//			for(String d : data){
//				String[] val = d.split("~");
//				List<String> adList = redis.lrange(val[0], 0, -1);
//				
//				if(!adList.contains(val[1])){
//					long check = redis.incr(val[0] + "~" + val[1]);
//					
//					if(check >= 5){
//						System.out.println("Under if : " + val[0] + " : " + val[1]);
//						redis.lpush(val[0], val[1]);
//					} else{
//						System.out.println("Under else : " + val[0] + " : " + val[1]);
//					}
//				}else{
//					System.out.println("under main else");
//				}
//			}
//			ConnectionFactory.getInstance(config).returnConnection(jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
