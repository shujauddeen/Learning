package com.poc.geohyperlocal;

import java.util.List;

public class HyperLocal {

	int campaignId;
	double[][] polyLat;
	double[][] polyLong;
	double[] polyCenterLat;
	double[] polyCenterLong;
	double[] circleLat;
	double[] circleLong;
	double[][] circle360Lat;
	double[][] circle360Long;
	double[][] constraints;
	double[][] multiples;
	int[] radius;
	int[] hour;
	final static int SIZE = 360;

	public boolean isPointInPolygon(double x, double y) {
		boolean oddNodes = false;
		try {
			if (polyLat != null && polyLat.length > 0) {
				int polygons = polyLat.length;
				for (int k = 0; k < polygons; k++) {
					int polyside = polyLat[k].length;
					double[] longitude = polyLong[k];
					int i, j = polyside - 1;
					for (i = 0; i < polyside; i++) {
						if ((longitude[i] < y && longitude[j] >= y || longitude[j] < y
								&& longitude[i] >= y)) {
							oddNodes ^= (y * multiples[k][i]
									+ constraints[k][i] < x);
						}
						j = i;
					}
					if (oddNodes == true)
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR : isPointInPolygon : " + e.toString());
		}
		return oddNodes;
	}

	public double isPointInPolygon(double x, double y, int pos) {
		boolean oddNodes = false;
		try {
			if (polyLat != null && polyLat.length > 0) {

				int polyside = polyLat[pos].length;
				double[] longitude = polyLong[pos];
				int i, j = polyside - 1;
				for (i = 0; i < polyside; i++) {
					if ((longitude[i] < y && longitude[j] >= y || longitude[j] < y
							&& longitude[i] >= y)) {
						oddNodes ^= (y * multiples[pos][i]
								+ constraints[pos][i] < x);
					}
					j = i;
				}

			}
		} catch (Exception e) {
			System.out.println("ERROR : isPointInPolygon : " + e.toString());
		}

//		System.out.println(oddNodes+" : "+pos);
		if (oddNodes) {
			return calculateDistanceForPolyGon(x, y, pos);
		} else {
			return -1d;
		}
	}

	public void preCalculatePolygon(double[] latitude, double[] longitude,
			int polyside, int pos) {
		try {
			int i, j = polyside-1;
			constraints[pos] = new double[latitude.length];
			multiples[pos] = new double[latitude.length];
			for (i = 0; i < polyside; i++) {
				if (longitude[j] == longitude[i]) {
					constraints[pos][i] = latitude[i];
					multiples[pos][i] = 0;
				} else {
					constraints[pos][i] = latitude[i]
							- (longitude[i] * latitude[j])
							/ (longitude[j] - longitude[i])
							+ (longitude[i] * latitude[i])
							/ (longitude[j] - longitude[i]);
					multiples[pos][i] = (latitude[j] - latitude[i])
							/ (longitude[j] - longitude[i]);
				}
				j = i;
			}
		} catch (Exception e) {
			System.out.println("ERROR : preCalculatePolygon : " + e.toString());
		}
	}

	public void setPolygonSize(int size) {
		try {
			polyLat = new double[size][];
			polyLong = new double[size][];
			polyCenterLat = new double[size];
			polyCenterLong = new double[size];
			constraints = new double[size][SIZE];
			multiples = new double[size][SIZE];
		} catch (Exception e) {
			System.out.println("ERROR : setPolygonSize : " + e.toString());
		}
	}

	public void setCircleSize(int size){
		circleLat = new double[size];
		circleLong = new double[size];
		radius = new int[size];
		circle360Lat = new double[size][];
		circle360Long = new double[size][];
	}
	
	public void createPoly(double[] lat, double[] lng, String centerLat,
			String centerLng, String campaign_id, List<String> hour, int pos) {
		try {
			polyLat[pos] = lat;
			polyLong[pos] = lng;
			polyCenterLat[pos] = Double.parseDouble(centerLat);
			polyCenterLong[pos] = Double.parseDouble(centerLng);
			campaignId = Integer.parseInt(campaign_id);
			preCalculatePolygon(lat, lng, lat.length, pos);
		} catch (Exception e) {
			System.out.println("ERROR : createPoly : " + e.toString());
		}
	}

	public void createCircle(String[] crl, String campaignId, String country,List<String> hour, int pos,int sphereType, HyperLocalBean geoBean) {
		try {
			String landmark = crl[0].toString();
			double latitude = Double.parseDouble(crl[1]);
			double longitude = Double.parseDouble(crl[2]);
			int rad = Integer.parseInt(crl[3]);
			// campaignId = Integer.parseInt(campaignId);
			circleLat[pos] = latitude;
			circleLong[pos] = longitude;
			radius[pos] = rad;
			preBuildCircle(latitude, longitude, rad, pos, sphereType, campaignId, geoBean,landmark);
		} catch (Exception e) {
			System.out.println("ERROR : createCircle : " + e.toString());
		}
	}

	public double isPointCircle(double lat2, double lng2, int pos) {
		double distance = 0.0d;
		try {
			double x1 = Math.toRadians(circleLat[pos]);
			double y1 = Math.toRadians(circleLong[pos]);
//			System.out.println("isPointCircle circleLat : " + x1);
//			System.out.println("isPointCircle circleLong : " + y1);
			double x2 = Math.toRadians(lat2);
			double y2 = Math.toRadians(lng2);
			double sec1 = Math.sin(x1) * Math.sin(x2);
			double d1 = Math.abs(y1 - y2);
			double sec2 = Math.cos(x1) * Math.cos(x2);
			double centralAngle = Math.acos(sec1 + sec2 * Math.cos(d1));
			distance = centralAngle * 6378134.438;	// in kilometers
//			System.out.println(distance);

		} catch (Exception e) {
			System.out.println("ERROR : isPointCircle : " + e.toString());
		}
		System.out.println("isPointCircle radisus pos : " + radius[pos]);
		System.out.println("Distance : " + distance);
		if (radius[pos] >= distance)
			return distance;
		else
			return -1d;
	}

	public double calculateDistanceForPolyGon(double lat2, double lng2, int pos) {
		double distance = 0.0d;
		try {
			double x1 = Math.toRadians(polyCenterLat[pos]);
			double y1 = Math.toRadians(polyCenterLong[pos]);
			double x2 = Math.toRadians(lat2);
			double y2 = Math.toRadians(lng2);
			double sec1 = Math.sin(x1) * Math.sin(x2);
			double d1 = Math.abs(y1 - y2);
			double sec2 = Math.cos(x1) * Math.cos(x2);
			double centralAngle = Math.acos(sec1 + sec2 * Math.cos(d1));
			distance = centralAngle * 6378134.438;	//in kilometers

		} catch (Exception e) {
			System.out.println("ERROR : isPointCircle : " + e.toString());
		}
		return distance;
	}

	public void preBuildCircle(double lat, double lng, int radius, int pos,int sphereType, String campaignId, HyperLocalBean geoBean, String landmark) {
		try {
			double[] latitude = new double[SIZE+1];
			double[] longitude = new double[SIZE+1];
			double rlat = (radius / 6378134.438) * (180 / Math.PI);
			double rlng = rlat / Math.cos(lat * (Math.PI / 180));
//			System.out.println("preBuildCircle : Before LOOP" + "Size is : "+SIZE);
			for (int i = 0; i < SIZE; i++) {
				double r = i * (Math.PI / 180);
				latitude[i] = lat + (rlat * Math.sin(r));
				longitude[i] = lng + (rlng * Math.cos(r));
//				geoBean.add(campaignId, landmark, sphereType,pos, latitude[i], longitude[i]);
			}
//			System.out.println("In Prebuild Circle");
			//changes start here
			geoBean.add(campaignId, landmark, sphereType,pos, lat, lng);
			latitude[SIZE]=lat;
			longitude[SIZE]=lng;
			//changes end here
			circle360Lat[pos] = latitude;
			circle360Long[pos] = longitude;
		} catch (Exception e) {
			System.out.println("ERROR : preBuildCircle : " + e.toString());
		}
	}
	
}
