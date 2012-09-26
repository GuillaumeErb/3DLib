package raytracer;

import java.io.Serializable;

import objects.Object3D;
import objects.Primitive;
import common.Vect3;

public class Intersection implements Comparable<Intersection>, Serializable {

	private Object3D object;
	private Primitive primitive;
	private Vect3 normal;
	private double distance;
	
	public Intersection(Primitive primitive, Vect3 normal, double distance) {
		super();
		this.setObject(null);
		this.primitive = primitive;
		this.normal = normal;
		this.distance = distance;
	}
	
//	public Intersection(Object3D object, Primitive primitive, Vect3 normal, double distance) {
//		super();
//		this.setObject(object);
//		this.primitive = primitive;
//		this.normal = normal;
//		this.distance = distance;
//	}
	
	public Primitive getPrimitive() {
		return this.primitive;
	}
	
	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
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

	public Object3D getObject() {
		return object;
	}

	public void setObject(Object3D object) {
		this.object = object;
	}

	@Override
	public int compareTo(Intersection o) {
		if(this.getDistance() == o.getDistance()) {
			return 0;
		} else if(this.getDistance() < o.getDistance()) {
			return -1;
		} else {
			return 1;
		}
	}

}
