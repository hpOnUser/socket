package hust.phone.mapper.pojo;

import java.io.Serializable;

public class Plane implements Serializable{
	private int planeid;
	private double longitude;
	private double latitude;
	private double altitude;
	
	public int getPlaneid() {
		return planeid;
	}

	public void setPlaneid(int planeid) {
		this.planeid = planeid;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		
		return "planeId:"+planeid+"longitude"+longitude+" latitude:"+latitude+" altitude:"+altitude;
	}
	

}
