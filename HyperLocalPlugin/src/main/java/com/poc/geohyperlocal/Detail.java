package com.poc.geohyperlocal;

public class Detail {
	private int campaignId;
	private String entityName;
	private double lat;
	private double lng;
	private int distance;
	private int pos;
	private int type;

	public Detail(int campaignId, String entityName, double lat, double lng,
			int distance, int pos, int type) {

		this.campaignId = campaignId;
		this.entityName = entityName;
		this.lat = lat;
		this.lng = lng;
		this.distance = distance;
		this.pos = pos;
		this.type = type;
	}

	public int getCampaignId() {
		return campaignId;
	}

	@Override
	public String toString() {
		return "Detail [campaignId=" + campaignId + ", entityName="
				+ entityName + ", lat=" + lat + ", lng=" + lng + ", distance="
				+ distance + ", pos=" + pos + ", type=" + type + "]";
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
