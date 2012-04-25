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
	
	public Simplex(Triangle t, Point p) {
		this.a = t.getA();
		this.b = t.getB();
		this.c = t.getC();
		this.d = p;
	}
	
	public Sphere circumSphere() {
		Matrix alphaM = new Matrix(4);
		alphaM.set(1, 1, a.getX());
		alphaM.set(1, 2, a.getY());
		alphaM.set(1, 3, a.getZ());
		alphaM.set(1, 4, 1);
		alphaM.set(2, 1, b.getX());
		alphaM.set(2, 2, b.getY());
		alphaM.set(2, 3, b.getZ());
		alphaM.set(2, 4, 1);
		alphaM.set(3, 1, c.getX());
		alphaM.set(3, 2, c.getY());
		alphaM.set(3, 3, c.getZ());
		alphaM.set(3, 4, 1);
		alphaM.set(4, 1, d.getX());
		alphaM.set(4, 2, d.getY());
		alphaM.set(4, 3, d.getZ());
		alphaM.set(4, 4, 1);
				
		Matrix dxM = new Matrix(4);
		dxM.set(1, 1, a.getX()*a.getX() + a.getY()*a.getY() + a.getZ()*a.getZ());
		dxM.set(1, 2, a.getY());
		dxM.set(1, 3, a.getZ());
		dxM.set(1, 4, 1);
		dxM.set(2, 1, b.getX()*b.getX() + b.getY()*b.getY() + b.getZ()*b.getZ());
		dxM.set(2, 2, b.getY());
		dxM.set(2, 3, b.getZ());
		dxM.set(2, 4, 1);
		dxM.set(3, 1, c.getX()*c.getX() + c.getY()*c.getY() + c.getZ()*c.getZ());
		dxM.set(3, 2, c.getY());
		dxM.set(3, 3, c.getZ());
		dxM.set(3, 4, 1);
		dxM.set(4, 1, d.getX()*d.getX() + d.getY()*d.getY() + d.getZ()*d.getZ());
		dxM.set(4, 2, d.getY());
		dxM.set(4, 3, d.getZ());
		dxM.set(4, 4, 1);
		
		Matrix dyM = new Matrix(4);
		dyM.set(1, 1, a.getX()*a.getX() + a.getY()*a.getY() + a.getZ()*a.getZ());
		dyM.set(1, 2, a.getX());
		dyM.set(1, 3, a.getZ());
		dyM.set(1, 4, 1);
		dyM.set(2, 1, b.getX()*b.getX() + b.getY()*b.getY() + b.getZ()*b.getZ());
		dyM.set(2, 2, b.getX());
		dyM.set(2, 3, b.getZ());
		dyM.set(2, 4, 1);
		dyM.set(3, 1, c.getX()*c.getX() + c.getY()*c.getY() + c.getZ()*c.getZ());
		dyM.set(3, 2, c.getX());
		dyM.set(3, 3, c.getZ());
		dyM.set(3, 4, 1);
		dyM.set(4, 1, d.getX()*d.getX() + d.getY()*d.getY() + d.getZ()*d.getZ());
		dyM.set(4, 2, d.getX());
		dyM.set(4, 3, d.getZ());
		dyM.set(4, 4, 1);
		
		Matrix dzM = new Matrix(4);
		dzM.set(1, 1, a.getX()*a.getX() + a.getY()*a.getY() + a.getZ()*a.getZ());
		dzM.set(1, 2, a.getX());
		dzM.set(1, 3, a.getY());
		dzM.set(1, 4, 1);
		dzM.set(2, 1, b.getX()*b.getX() + b.getY()*b.getY() + b.getZ()*b.getZ());
		dzM.set(2, 2, b.getX());
		dzM.set(2, 3, b.getY());
		dzM.set(2, 4, 1);
		dzM.set(3, 1, c.getX()*c.getX() + c.getY()*c.getY() + c.getZ()*c.getZ());
		dzM.set(3, 2, c.getX());
		dzM.set(3, 3, c.getY());
		dzM.set(3, 4, 1);
		dzM.set(4, 1, d.getX()*d.getX() + d.getY()*d.getY() + d.getZ()*d.getZ());
		dzM.set(4, 2, d.getX());
		dzM.set(4, 3, d.getY());
		dzM.set(4, 4, 1);
		
		
		double alpha = alphaM.det();
		double dx = dxM.det();
		double dy = dyM.det();
		double dz = dzM.det();
		
		Vect3 cs = (new Vect3(dx,dy,dz)).dividedBy(2*alpha);
		
		return new Sphere(new Point(cs), cs.minus(this.a.toVect3()).norm());
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
		return sameSide(point, a, b, c, d) &&
			   sameSide(point, b, a, c, d) &&
			   sameSide(point, c, a, b, d) &&
			   sameSide(point, d, a, b, c) ;
	}
	
	public static boolean sameSide(Point p1, Point p2, Point a, Point b, Point c) {
		Vect3 n = (a.toVect3().minus(c.toVect3())).cross(b.toVect3().minus(c.toVect3())).normalize();
		Vect3 cp1 = p1.toVect3().minus(c.toVect3()).normalize();
		Vect3 cp2 = p2.toVect3().minus(c.toVect3()).normalize();
		return cp1.scalar(n)*cp2.scalar(n) >= 0;
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
	
	public Triangle getCommonFace(Simplex simplex) {
		Collection<Point> points = this.getCommonPoints(simplex);
		if(points.size() == 3) {
			return new Triangle(points);
		} else {
			return null;
		}
	}
	
	public Collection<Triangle> getTriangles() {
		Collection<Triangle> res = new ArrayList<Triangle>();
		res.add(new Triangle(a,b,c));
		res.add(new Triangle(a,b,d));
		res.add(new Triangle(a,c,d));
		res.add(new Triangle(b,c,d));
		return res;
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

	@Override
	public String toString() {
		return "Simplex [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
	}
	
	
	
}
