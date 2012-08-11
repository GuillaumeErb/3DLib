package raytracer;

import common.Vect3;

public class Intersection {

	private SimpleObject object;
	private Vect3 normal;
	private double distance;
	
	public Intersection(SimpleObject object, Vect3 normal, double distance) {
		super();
		this.object = object;
		this.normal = normal;
		this.distance = distance;
	}
	
	public SimpleObject getObject() {
		return this.object;
	}
	
	public void setObject(SimpleObject object) {
		this.object = object;
	}
	
	public Vect3 getNormal() {
		return normal;
	}
	
	public void setNormal(Vect3 normal) {
		this.normal = normal;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
