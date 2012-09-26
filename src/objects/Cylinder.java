package objects;

import java.util.Collection;
import java.util.List;

import common.Point;
import common.Vect3;

import materials.Material;
import raytracer.Intersection;
import raytracer.Ray;

public class Cylinder extends Primitive {

	private Point center;
	private Vect3 axis;
	private double radius;

	public Cylinder(Point center, Vect3 axis, double radius, Material material) {
		super(material);
		this.center = center;
		this.axis = axis.normalize();
		this.radius = radius;
		
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Vect3 getAxis() {
		return axis;
	}

	public void setAxis(Vect3 axis) {
		this.axis = axis;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		
		Vect3 centerPar = this.axis.times(center.toVect3().scalar(this.axis)); 
		Vect3 centerPerp = this.center.toVect3().minus(centerPar);
		
		Vect3 originPar = this.axis.times(ray.getOrigin().toVect3().scalar(this.axis));
		Vect3 originPerp = ray.getOrigin().toVect3().minus(originPar);
		
		Vect3 directionPar = this.axis.times(ray.getDirection().scalar(this.axis));
		Vect3 directionPerp = ray.getDirection().minus(directionPar);
		
		Sphere phantom = new Sphere(new Point(centerPerp), 
									this.radius, 
									this.material);
		
		Ray ghost = new Ray(new Point(originPerp), directionPerp);
		
//		Intersection i = phantom.getIntersection(ghost);
//		if(i != null) {
//			i.setNormal(i.getNormal().times(-1));	
//		}
//		return i;
		
		return phantom.getIntersection(ghost);
	}

	@Override
	public List<Intersection> getIntersections(Ray ray) {
		Vect3 centerPar = this.axis.times(center.toVect3().scalar(this.axis)); 
		Vect3 centerPerp = this.center.toVect3().minus(centerPar);
		
		Vect3 originPar = this.axis.times(ray.getOrigin().toVect3().scalar(this.axis));
		Vect3 originPerp = ray.getOrigin().toVect3().minus(originPar);
		
		Vect3 directionPar = this.axis.times(ray.getDirection().scalar(this.axis));
		Vect3 directionPerp = ray.getDirection().minus(directionPar);
		
		Sphere phantom = new Sphere(new Point(centerPerp), 
									this.radius, 
									this.material);
		
		Ray ghost = new Ray(new Point(originPerp), directionPerp);
		
//		List<Intersection> i = phantom.getIntersections(ghost);
//		if(i != null) {
//			for(Intersection ii : i) {
//				if(ii!=null) {
//					ii.setNormal(ii.getNormal().times(-1));
//				}
//			}
//		}
//		return i;
		
		return phantom.getIntersections(ghost);
	}

}
