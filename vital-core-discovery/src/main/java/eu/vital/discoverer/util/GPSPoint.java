package eu.vital.discoverer.util;


public class GPSPoint {

	private double longitude;
	private double latitude;


	public GPSPoint(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
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


	public String toString() {
		return "("+latitude+","+longitude+")";
	}

}
