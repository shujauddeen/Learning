package com.poc.hyperlocal.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import com.poc.geohyperlocal.Detail;
import com.poc.geohyperlocal.HyperLocalDataPlugin;

public class HyperLocalPluginTest {
	@Test
	public void testhyperLocal() throws ConfigurationException, IOException{
		final Configuration config = new PropertiesConfiguration("/home/km/JavaWorkspace/Learning/HyperLocalPlugin/src/main/resources/conf/hyperlocal.properties");
		final HyperLocalDataPlugin obj = new HyperLocalDataPlugin();
		obj.init(config);
		
		final String hyperLocalFile = config.getString("file.hyperlocal");
		obj.refreshHyperLocalData(hyperLocalFile);
		//--------------------------------------
		BufferedReader buff;
		final FileWriter fw = new FileWriter("/home/km/JavaWorkspace/Learning/HyperLocalPlugin/src/main/resources/conf/HyperlocalPerformanceTest.csv");
		final BufferedWriter bw = new BufferedWriter(fw);
		final StringBuilder sb = new StringBuilder();
		try {
			Detail det=null;
			buff = new BufferedReader(new FileReader("/home/km/JavaWorkspace/Learning/HyperLocalPlugin/src/main/resources/conf/hyperlocaltestdata.csv"));
			String line = null;
			
			String lineBreak = "";
			System.out.println("start");
			while ((line = buff.readLine()) != null) {
				sb.append(lineBreak);
				lineBreak = "\n";
				final String[] data = line.split(",");
				long start=System.nanoTime();
				@SuppressWarnings("unchecked")
				//List<Object> resultArray = (List<Object>) obj.call("SINGAPORE","45795","1.38543","103.842673","1125251");
				List<Object> resultArray = (List<Object>) obj.call(data[0].toString(),data[1].toString(),data[2].toString(),data[3].toString(),data[4].toString());
				long end=System.nanoTime();
//				System.out.println("duration : " + (end-start));
				System.out.println(data[0].toString() +" , " + data[1].toString() +" , latitude : " + data[2].toString() +" , longitude : " + data[3].toString() +" , " +data[4].toString());
			
				sb.append(data[0].toString()).append(",");
				sb.append(data[2].toString()).append(",");
				sb.append(data[3].toString()).append(",");
				sb.append("2000").append(",");
				System.out.println(" resultArray.size()  : "+resultArray);
				if(resultArray.size() > 2){
					det = (Detail)resultArray.get(0);
					sb.append(det.getDistance()).append(",");
					sb.append(det.getEntityName()).append(",");
					
					System.out.println(det.getEntityName()  + " : " + det.getDistance());
				}else{
					sb.append("NA").append(",");
					sb.append("NA").append(",");
				}
				sb.append((end-start)/1000000.00).append(",");
				//sb.append(det.getUtmGrid());
				System.out.println("------------------------------------");
			}
			bw.write(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			bw.close();
			fw.close();
		}
		//--------------------------------------
	}
}
