package objects;

import raytracer.Intersection;
import raytracer.Ray;
import materials.Material;

import common.Point;
import common.Vect3;


public class Sphere extends Primitive {
	
	private Point center;
	private double radius;
	
	/**
	 * @param center
	 * @param radius
	 */
	public Sphere(Point center, double radius, Material material) {
		super(material);
		this.center = center;
		this.radius = radius;
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/**
	 * http://www.cs.princeton.edu/courses/archive/
	 * fall00/cs426/lectures/raycast/sld013.htm
	 * 
	 * @param ray
	 * @return
	 */
	public Intersection getIntersection(Ray ray) {
	    Vect3 L = this.center.toVect3().minus(ray.getOrigin().toVect3());
	    Vect3 V = ray.getDirection().normalize();
	    double t_ca = L.scalar(V);
	    if (t_ca < 0) {
	        return null;
	    } else {
	        double d2 = L.scalar(L) - t_ca * t_ca;
	        double r2 = radius * radius;
	        if (d2 > r2) {
	            return null;
	        } else {
	            double t_hc = Math.sqrt(r2 - d2);
	            double t1 = t_ca - t_hc;
	            double t2 = t_ca + t_hc;
	            double distance = (t1<t2) ? t1 : t2;

	            Vect3 OP = ray.getOrigin().toVect3().
	            		   plus(V.times(distance)).
	            		   minus(center.toVect3());
	            return new Intersection(this, OP.normalize(), distance);
	        }
	    }
	}	
}
