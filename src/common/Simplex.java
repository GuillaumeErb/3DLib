package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Simplex {
	
	private Point a;
	private Point b;
	private Point c;
	private Point d;
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public Simplex(Point a, Point b, Point c, Point d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Sphere circumSphere() {
		Vect3 u = a.toVect3().minus(d.toVect3());
		Vect3 v = b.toVect3().minus(d.toVect3());
		Vect3 w = c.toVect3().minus(d.toVect3());
		
		double den = 2*u.scalar(v.cross(w));
		
		Vect3 num =  v.cross(w).times(u.scalar(u))
				   .plus(w.cross(u).times(v.scalar(v)))
				   .plus(u.cross(v).times(w.scalar(w)));
		
		return new Sphere(new Point(num.dividedBy(den)), 
						  num.norm()/Math.abs(den));
	}
	
	public int circumSphereContains(Point point) {
		Sphere s = this.circumSphere();
		double d = (point.toVect3().minus(s.getCenter().toVect3())).norm();
		if(d<s.getRadius()) {
			return 1;
		} else if (d == s.getRadius()) {
			return 2;
		} else {
			return 0;
		}
	}
	
	public Collection<Point> getCommonPoints(Simplex simplex) {
		
		Collection<Point> result = new HashSet<Point>();
		
		if(this.hasAsVertex(simplex.getA())) {
			result.add(simplex.getA());
		}
		if(this.hasAsVertex(simplex.getB())) {
			result.add(simplex.getB());
		}
		if(this.hasAsVertex(simplex.getC())) {
			result.add(simplex.getC());
		}
		if(this.hasAsVertex(simplex.getD())) {
			result.add(simplex.getD());
		}
		
		return result;
	}
	
	public boolean contains(Point point) {
		return this.SameSide(point, a, b, c, d) &&
			   this.SameSide(point, b, a, c, d) &&
			   this.SameSide(point, c, a, b, d) &&
			   this.SameSide(point, d, a, b, c) ;
		
		
	}
	
	private boolean SameSide(Point p1, Point p2, Point a, Point b, Point c) {
		Vect3 n = (a.toVect3().minus(c.toVect3())).cross(b.toVect3().minus(c.toVect3())).normalize();
		Vect3 cp1 = p1.toVect3().minus(c.toVect3()).normalize();
		Vect3 cp2 = p2.toVect3().minus(c.toVect3()).normalize();
		return cp1.scalar(n)*cp2.scalar(n) > 0;
	}

	public boolean hasAsVertex(Point point) {
		return a.equals(point) || 
			   b.equals(point) || 
			   c.equals(point) || 
			   d.equals(point) ;
	}
	
	public boolean hasAsVertices(Point pa, Point pb, Point pc) {
		
		return 
				
		(pa.equals(a) ||
		 pa.equals(b) ||
		 pa.equals(c) ||
		 pa.equals(d))&&
		
		(pb.equals(a) ||
		 pb.equals(b) ||
		 pb.equals(c) ||
		 pb.equals(d))&&
		
		(pc.equals(a) ||
		 pc.equals(b) ||
		 pc.equals(c) ||
		 pc.equals(d));
		
	}
	
	public Collection<Point> getPoints() {
		Collection<Point> points = new ArrayList<Point>();
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		
		return points;
	}
	
	public Triangle extractTriangle(Point point) {
		if(this.a.equals(point)) {
			return new Triangle(b,c,d);
		} else if(this.b.equals(point)) {
			return new Triangle(a,c,d);
		} else if(this.c.equals(point)) {
			return new Triangle(a,b,d);
		} else if(this.d.equals(point)) {
			return new Triangle(a,b,c);
		} else return null;
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

	public Point getD() {
		return d;
	}

	public void setD(Point d) {
		this.d = d;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((d == null) ? 0 : d.hashCode());
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
		Simplex other = (Simplex) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		return true;
	}
	
	
	
}
