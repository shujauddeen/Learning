package com.poc.geohyperlocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class HyperLocalBean {

	SortedMap<Double, List<Detail>> tempHyperLocalLatMap;
	SortedMap<Double, List<Detail>> tempHyperLocalLngMap;
	HashMap<String, List<String>> tempCountryAdclientMap;
	HashMap<Integer, HyperLocal> tempHyperLocalObject;

	SortedMap<Double, List<Detail>> hyperLocalLatMap = new TreeMap<Double, List<Detail>>();
	SortedMap<Double, List<Detail>> hyperLocalLngMap = new TreeMap<Double, List<Detail>>();;
	HashMap<String, List<String>> countryAdclientMap = new HashMap<String, List<String>>();
	HashMap<Integer, HyperLocal> hyperLocalObject = new HashMap<Integer, HyperLocal>();

	private static final Pattern SPLITDOT = Pattern.compile("\\.");
	private static final Pattern SPLITSPACE = Pattern.compile(" ");
	private static final int CIRCLE = 1;
	private static final int POLYGON = 2;
	private static final int ZIPCODE = 0;

	public HyperLocalBean() {
		tempHyperLocalLatMap = new TreeMap<Double, List<Detail>>();
		tempHyperLocalLngMap = new TreeMap<Double, List<Detail>>();
		tempCountryAdclientMap = new HashMap<String, List<String>>();
		tempHyperLocalObject = new HashMap<Integer, HyperLocal>();
	}

	public void add(String campaignId, String entityName, int type, int pos,
			double tempLat, double tempLng) {

		List<Detail> tempLatCampDetails = hyperLocalLatMap.get(tempLat);

		List<Detail> tempLngCampDetails = hyperLocalLngMap.get(tempLng);
		try {
			// System.out.println("Data Before Adding");

			if (tempLngCampDetails != null) {
				// System.out.println("HyperLocalBean : add : IF CONDITION");
				final Detail e = new Detail(Integer.parseInt(campaignId),
						entityName, tempLat, tempLng, 0, pos, type);
				tempLngCampDetails.add(e);
				// tempLongMap.put(tempLng, tempCampDetails);
				tempHyperLocalLngMap.put(tempLng, tempLngCampDetails);
			} else {
				// System.out.println("HyperLocalBean : add : ELSE CONDITION");
				tempLngCampDetails = new ArrayList<Detail>();
				final Detail e = new Detail(Integer.parseInt(campaignId),
						entityName, tempLat, tempLng, 0, pos, type);
				tempLngCampDetails.add(e);
				// tempLongMap.put(tempLng, tempCampDetails);
				tempHyperLocalLngMap.put(tempLng, tempLngCampDetails);
			}

			if (tempLatCampDetails != null) {
				// System.out.println("HyperLocalBean : add : IF CONDITION");
				final Detail e = new Detail(Integer.parseInt(campaignId),
						entityName, tempLat, tempLng, 0, pos, type);

				tempLatCampDetails.add(e);
				// tempLongMap.put(tempLng, tempCampDetails);
				tempHyperLocalLatMap.put(tempLat, tempLatCampDetails);
			} else {
				tempLatCampDetails = new ArrayList<Detail>();
				final Detail e = new Detail(Integer.parseInt(campaignId),
						entityName, tempLat, tempLng, 0, pos, type);
				tempLatCampDetails.add(e);
				// tempLongMap.put(tempLng, tempCampDetails);
				tempHyperLocalLatMap.put(tempLat, tempLatCampDetails);
			}
		} catch (Throwable e) {
			System.out.println(e.getCause() + e.toString());
			e.printStackTrace();
		}

	}

	public Object search(String country, String adclient, double lat,
			double lng, String zipCode) {
		// System.out.println("Obsect search : " + country);
		final ArrayList<String> adclientSet = (ArrayList<String>) countryAdclientMap
				.get(country);
		List<Object> resultArray = new ArrayList<Object>(2);
		int count = 0;
		double max = 0.0d;
		double min = 0.0d;
		
		if (adclientSet != null) {
			if (adclientSet.contains(adclient)) {
				// final List<Detail> latValue;
				
				final List<Entry<Double, List<Detail>>> latValue = new ArrayList<Entry<Double, List<Detail>>>(
						4);
				latValue.add((Entry<Double, List<Detail>>) ((NavigableMap<Double, List<Detail>>) hyperLocalLatMap
						.headMap(lat)).lastEntry());

				latValue.add((Entry<Double, List<Detail>>) ((NavigableMap<Double, List<Detail>>) hyperLocalLatMap
						.tailMap(lat)).firstEntry());

				latValue.add((Entry<Double, List<Detail>>) ((NavigableMap<Double, List<Detail>>) hyperLocalLngMap
						.headMap(lng)).lastEntry());
				latValue.add((Entry<Double, List<Detail>>) ((NavigableMap<Double, List<Detail>>) hyperLocalLngMap
						.tailMap(lng)).firstEntry());

				resultArray = new ArrayList<Object>(10);
				int sort = 0;
				if (latValue != null) {
					for (final Entry<Double, List<Detail>> campDetailList : latValue) {
						if (campDetailList != null) {
							for (final Detail campDetail : campDetailList
									.getValue()) {
								System.out.println(campDetail.getEntityName());

								final HyperLocal hyp = hyperLocalObject
										.get(campDetail.getCampaignId());

								if (campDetail.getType() == CIRCLE) {
									double distanceCircle = hyp.isPointCircle(
											lat, lng, campDetail.getPos());
									if (distanceCircle > -1d) {
										// System.out.println("under first if condition");
										if (sort != 0) {
											// System.out.println("under second if condition");
											if (distanceCircle < min) {
												min = distanceCircle;
											}
											if (distanceCircle > max) {
												max = distanceCircle;
											}
										} else {
											min = max = distanceCircle;
										}
										campDetail.setDistance((int) Math
												.floor(distanceCircle));
										resultArray.add(count, campDetail);
										// System.out.println(campDetail);

										count++;
									}
								} else if (campDetail.getType() == POLYGON) {
									// System.out.println("UNDER POLYGON search");
									double distancePoly = hyp.isPointInPolygon(
											lat, lng, campDetail.getPos());
									if (distancePoly > -1d) {
										if (sort != 0) {
											if (distancePoly < min) {
												min = distancePoly;
											}
											if (distancePoly > max) {
												max = distancePoly;
											}
										} else {
											min = max = distancePoly;
										}
										campDetail.setDistance((int) Math
												.floor(distancePoly));
										resultArray.add(count, campDetail);

										count++;
									}
								} else if (campDetail.getType() == ZIPCODE) {

								}
								sort++;

							}
						}
					}
				} else {
					// System.out.println("UNDER BEAN FIRST ELSE");
					resultArray.add(0, min);
					resultArray.add(1, max);
				}
				
			} else {
				// System.out.println("UNDER BEAN SECOND ELSE");
				resultArray.add(0, min);
				resultArray.add(1, max);
			}
		} else {
			// System.out.println("UNDER BEAN THIRD ELSE");
			resultArray.add(0, min);
			resultArray.add(1, max);
		}
		// } else {
		// System.out.println("UNDER BEAN FOURTH ELSE");
		// resultArray.add(0, min);
		// resultArray.add(1, max);
		// }
		if (count > 0) {
			resultArray.add(count++, min);
			resultArray.add(count++, max);
		}
		// System.out.println(count);
		// System.out.println(hyperLocalLatMap);
		// System.out.println(hyperLocalLngMap);
		return resultArray;
	}

	public void swap() {
		System.out.println("UNDER THE SWAP METHOD");
		if (tempHyperLocalLatMap != null) {
			hyperLocalLatMap = tempHyperLocalLatMap;
		}
		if (tempHyperLocalLngMap != null) {
			hyperLocalLngMap = tempHyperLocalLngMap;
		}
		if (tempCountryAdclientMap != null) {
			countryAdclientMap = tempCountryAdclientMap;
		}
		if (tempHyperLocalObject != null) {
			hyperLocalObject = tempHyperLocalObject;
		}
		System.out.println("SWAP FINISHED");
	}

	public void setCountryAdclient(String countries, String adclients) {
		final String[] countryArray = getSplit(countries, SPLITSPACE);
		final String[] adclientArray = getSplit(adclients, SPLITSPACE);

		for (final String country : countryArray) {
			final String[] countryName = getSplit(country, SPLITDOT);
			Set<String> adclientSet;
			if(tempCountryAdclientMap
					.get(countryName[0])==null){
				adclientSet=new HashSet<String>();
			}else{
				adclientSet=Sets.newHashSet(tempCountryAdclientMap
						.get(countryName[0]));	
			}
			
			if (adclientSet != null) {
				for (String adc : adclientArray) {
					// System.out.println("adclient if: " + adc);
					adclientSet.add(adc);
				}
				tempCountryAdclientMap.put(countryName[0],
						Lists.newArrayList(adclientSet));
			} else {
				adclientSet = new HashSet<String>();
				for (String adc : adclientArray) {
					// System.out.println("adclients else:" + adc);
					adclientSet.add(adc);
				}
				// System.out.println("Countername : " + countryName[0]);
				tempCountryAdclientMap.put(countryName[0],
						Lists.newArrayList(adclientSet));
			}
		}

	}

	public String[] getSplit(String str, Pattern pattern) {
		final String[] data = pattern.split(str);
		return data;
	}

	public void setHyperLocalObject(Integer campaignId, HyperLocal hyperLocalObj) {
		tempHyperLocalObject.put(campaignId, hyperLocalObj);
	}
}
