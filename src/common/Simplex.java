package common;

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
	
	public boolean circumSphereContains(Point point) {
		Sphere s = this.circumSphere();
		double d = (point.toVect3().minus(s.getCenter().toVect3())).norm();
		return d<=s.getRadius();
	}
	
	public boolean contains(Point point) {
	
		Vect3 u = a.toVect3().minus(d.toVect3());
		Vect3 v = b.toVect3().minus(d.toVect3());
		Vect3 w = c.toVect3().minus(d.toVect3());
		
		Vect3 p = point.toVect3().minus(d.toVect3());
		
		double criteria = p.scalar(u) + p.scalar(v) + p.scalar(w); 
		
		if(criteria == 1) {
			System.out.println("non unicity, 4 coplanar points");
		}
		
		return criteria <= 1;
		
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
