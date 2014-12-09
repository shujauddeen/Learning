package com.poc.geohyperlocal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class HyperLocalDataPlugin {

	final ScheduledExecutorService service = Executors
			.newSingleThreadScheduledExecutor();
	static final String CONFIG_PATH = "/home/komli/workspace/HyperLocalPlugin/src/main/resources/conf/hyperlocal.properties";
	static final String FILE_PATH = "file.hyperlocal";
	static final String PROP_REFRESH = "refresh";
	private static final Pattern SPLITDOT = Pattern.compile("\\.");
	private static final Pattern SPLITCOLON = Pattern.compile("\\:");
	private static final Pattern SPLITSEMICOLON = Pattern.compile("\\;");
	private static final Pattern SPLITCOMMA = Pattern.compile("\\,");
	private static final Pattern SPLITDOLLAR = Pattern.compile("\\$");
	private static final Pattern SPLITCAP = Pattern.compile("\\^");
	private static final Pattern SPLITPIPE = Pattern.compile("\\|");
	private static final Pattern SPLITSPACE = Pattern.compile(" ");

	private static final int CIRCLE = 1;
	private static final int POLYGON = 2;
	private static final int ZIPCODE = 0;

	private boolean isSearch = false;
	HyperLocalBean geoBean;

	public static void main(String[] args) throws ConfigurationException {
		final Configuration config = new PropertiesConfiguration(CONFIG_PATH);
		final HyperLocalDataPlugin obj = new HyperLocalDataPlugin();
		obj.init(config);
	}

	public void init(Configuration conf) {
		System.out.println("UNDER INIT METHOD");
		final long refreshRate = conf.getLong(PROP_REFRESH);
		final String hyperLocalFile = conf.getString(FILE_PATH);
//		System.out.println(hyperLocalFile);
		final File file = new File(hyperLocalFile);
		if (!(file.exists() && file.canRead())) {
			throw new RuntimeException("File " + hyperLocalFile
					+ " does not exist or is not readable");
		}
		
		
		Runnable sphere = new Runnable() {
			public void run() {
				try {
					System.out.println("HYPERLOCAL PLUGIN RUNNING @ : " + new Date());
					//UNCOMMENT THE BELOW LINE. HAVE COMMENTED ONLY FOR TESTING PURPOSE
					//refreshHyperLocalData(hyperLocalFile);
				} catch (Exception e) {
					// LOG.error("IO Exception", e);
				}
			}
		};
		
		service.scheduleAtFixedRate(sphere, 0, refreshRate, TimeUnit.MINUTES);
	}

	public void refreshHyperLocalData(String file) {
//		System.out.println("UNDER refreshHyperLocalData");
		geoBean = new HyperLocalBean();
		BufferedReader buff;
		try {
			buff = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = buff.readLine()) != null) {
				final String[] parent = getSplit(line, SPLITCOMMA);
				populateSphere(parent[0], parent[1], parent[2], parent[3]);
				geoBean.setCountryAdclient(parent[1],parent[4]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isSearch = false;
		geoBean.swap();
		isSearch = true;
	}

	@SuppressWarnings("unchecked")
	public final Object call(Object... arg) {
		List<Object> resultArray;
		if (isSearch) {
			final String country = (String) arg[0];
			final String adclient = (String) arg[1];
			final double lat = Double.parseDouble(arg[2].toString());
			final double lng = Double.parseDouble(arg[3].toString());
			final String zipCode = arg[4].toString();
			resultArray = (List<Object>) geoBean.search(country,adclient,lat,lng,zipCode);
		} else {
			resultArray = new ArrayList<Object>(2);
			resultArray.add(0, 0.0d);
			resultArray.add(1, 0.0d);
		}
		return resultArray;
	}

	public void populateSphere(String campaign_id, String country,
			String hours, String hyperStr) {
//		System.out.println("UNDER populateSphere method");
		final String[] data = getSplit(hyperStr, SPLITSEMICOLON);
		if (data != null) {
			final String[] hr = getSplit(hours, SPLITSPACE);
			final List<String> hour = new ArrayList<String>(Arrays.asList(hr));
			final HyperLocal geoHyperLocal = new HyperLocal();
			geoBean.setHyperLocalObject(Integer.parseInt(campaign_id), geoHyperLocal);
			
			for (String str : data) {
				final String[] geoData = getSplit(str, SPLITCOLON);
				if (Integer.parseInt(geoData[0]) == 0)
					setZipCode(geoHyperLocal, campaign_id, country, hour,
							geoData[1]);
				if (Integer.parseInt(geoData[0]) == 1)
					setCircle(geoHyperLocal, campaign_id, country, hour,
							geoData[1]);
				if (Integer.parseInt(geoData[0]) == 2)
					setPolygon(geoHyperLocal, campaign_id, country, hour,
							geoData[1]);
			}
		}
	}

	public void setZipCode(HyperLocal geoHyperLocal, String campaign_id,
			String country, List<String> hours, String geoData) {
//		final String[] zip = getSplit(geoData, SPLITPIPE);
//		Map<String, ArrayList<String>> innerMap;
//		ArrayList<String> zipList;
//		for (String z : zip) {
//			if (zipCode.get(country) != null) {
//				innerMap = zipCode.get(country);
//				if (innerMap.get(z) != null) {
//					zipList = innerMap.get(z);
//					zipList.add(campaign_id);
//
//				} else {
//					zipList = new ArrayList<String>();
//					zipList.add(campaign_id);
//					innerMap.put(z, zipList);
//				}
//			} else {
//				innerMap = new HashMap<String, ArrayList<String>>();
//				zipList = new ArrayList<String>();
//				zipList.add(campaign_id);
//				innerMap.put(z, zipList);
//				zipCode.put(country, innerMap);
//			}
//		}
	}

	public void setCircle(HyperLocal geoHyperLocal, String campaign_id,
			String country, List<String> hours, String geoData) {
		final String[] circleGeo = getSplit(geoData, SPLITCAP);
		geoHyperLocal.setCircleSize(circleGeo.length);
		int mainCount = 0;
		for (String cl : circleGeo) {
			final String[] crl = getSplit(cl, SPLITPIPE);
			geoHyperLocal.createCircle(crl,campaign_id,country,hours,mainCount,CIRCLE,geoBean);
			mainCount++;
		}
	}

	public void setPolygon(HyperLocal geoHyperLocal, String campaignId,
			String country, List<String> hours, String geoData) {
		final String[] polygonGeo = getSplit(geoData, SPLITDOLLAR);
		geoHyperLocal.setPolygonSize(polygonGeo.length);
		int mainCount = 0;
		for (String tempPoly : polygonGeo) {
			String centerLat = null;
			String centerLng = null;
			String landmark = null;
			String[] subPoly = getSplit(tempPoly, SPLITCAP);
			final double[] tempLat = new double[subPoly.length - 2];
			final double[] tempLng = new double[subPoly.length - 2];
			int subCount = -2;
			for (String pl : subPoly) {
				if(landmark!=null){
					String[] poly = getSplit(pl, SPLITPIPE);
					if (centerLat != null && centerLng != null) {
						final double tmpLat = Double.parseDouble(poly[0]);
						final double tmpLng = Double.parseDouble(poly[1]);
						tempLat[subCount] = tmpLat;
						tempLng[subCount] = tmpLng;
						geoBean.add(campaignId,landmark,POLYGON,mainCount,tmpLat,tmpLng);
					}
					if (centerLat == null) {
						centerLat = poly[0];
					}
					if (centerLng == null) {
						centerLng = poly[1];
					}
				}else{
					landmark = pl;
				}
				subCount++;
			}
			geoHyperLocal.createPoly(tempLat, tempLng, centerLat, centerLng,campaignId, hours, mainCount);
			mainCount++;
		}
	}

	public String[] getSplit(String str, Pattern pattern) {
		final String[] data = pattern.split(str);
		return data;
	}
}
