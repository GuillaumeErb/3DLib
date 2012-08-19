package common;

import java.util.ArrayList;
import java.util.Collection;

public class Triangle {

	private Point a;
	private Point b;
	private Point c;
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 */
	public Triangle(Point a, Point b, Point c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Triangle(Collection<Point> points) {
		super();
		Point[] vpoints = (Point[]) points.toArray();
		a = vpoints[0];
		b = vpoints[1];
		c = vpoints[2];
	}

	public Collection<Point> getPoints() {
		ArrayList<Point> list = new ArrayList<>();
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
