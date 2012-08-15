package raytracer;

import java.util.ArrayList;
import java.util.Collection;

import common.Point;
import common.Vect3;

import materials.Material;

import raytracer.Intersection;

public class Triangle extends Primitive {

	private Point a;
	private Point b;
	private Point c;
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 */
	public Triangle(Point a, Point b, Point c, Material material) {
		super(material);
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Triangle(Collection<Point> points, Material material) {
		super(material);
		Point[] vpoints = (Point[]) points.toArray();
		a = vpoints[0];
		b = vpoints[1];
		c = vpoints[2];
	}

	public Collection<Point> getPoints() {
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(a);
		list.add(b);
		list.add(c);
		return list;
	}
	
	public double getCircumRadius() {
		double x = a.toVect3().minus(b.toVect3()).norm();
		double y = a.toVect3().minus(c.toVect3()).norm();
		double z = b.toVect3().minus(c.toVect3()).norm();
		
		return (x*y*z)/Math.sqrt((x+y+z)*(y+z-x)*(z+x-y)*(x+y-z));
	}
	
	public boolean planeContains(Point p) {
		Vect3 ab = b.toVect3().minus(a.toVect3());
		Vect3 ac = c.toVect3().minus(a.toVect3());
		Vect3 ap = p.toVect3().minus(a.toVect3());
		Vect3 n1 = ab.cross(ac);
		Vect3 n2 = ab.cross(ap);
		return n1.colinear(n2);
	}
		
	public Intersection getIntersection(Ray ray) {

	    // find vectors for two edges sharing A                                   
	    Vect3 edge1 = b.toVect3().minus(a.toVect3());
	    Vect3 edge2 = c.toVect3().minus(a.toVect3());

	    // begin calculating determiant - also used to calculate U parameter      
	    Vect3 pvec = ray.getDirection().cross(edge2);

	    // if determinant is near zero, ray lies in plane of triangle             
	    double det = edge1.scalar(pvec);

	    if (det < 0) {
	        return null;
	    }

	    // calculate distance from A to ray origin                                
	    Vect3 tvec = ray.getOrigin().toVect3().minus(a.toVect3());

	    // calculate U parameter and test bounds                                  
	    double u = tvec.scalar(pvec);
	    if (u < 0 || u > det) {
	    	return null;
	    }

	    // prepare to test V parameter                                            
	    Vect3 qvec = tvec.cross(edge1);

	    // calculate V parameter and test bounds                                  
	    double v = ray.getDirection().scalar(qvec);
	    if (v < 0.0 || u + v > det) {
	        return null;
	    }

	    Vect3 normal = edge1.cross(edge2);
//	    if(normal.scalar(ray.getDirection()) > 0) {
//	    	normal = normal.times(-1);
//	    }
	    
	    return new Intersection(this, normal, edge2.scalar(qvec) / det);
	}
	
	public Point getA() {
		return a;
	}

	public void setA(Point a) {
		this.a = a;
	}

	public Point getB() {
		return b;
	}

	public void setB(Point b) {
		this.b = b;
	}

	public Point getC() {
		return c;
	}

	public void setC(Point c) {
		this.c = c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		return other.getPoints().containsAll(this.getPoints());
	}

	@Override
	public String toString() {
		return a + ", " + b + ", " + c;
	}

	public boolean hasAsVertex(Point pp) {
		return pp.equals(a) || pp.equals(b) || pp.equals(c);
	}
	
	
	
}
