package common;


public class Plane {
	
	private Vect3 normal;
	
	private Point point;

	public Plane(Vect3 normal, Point point) {
		super();
		this.normal = normal.normalize();
		this.point = point;
	}

	public double distance(Point p) {
		return Math.abs(p.toVect3().minus(point.toVect3()).scalar(this.normal));
	}
	
	public boolean intersects(Triangle f) {
		Vect3 a = f.getA().toVect3().minus(this.point.toVect3());
		Vect3 b = f.getB().toVect3().minus(this.point.toVect3());
		Vect3 c = f.getC().toVect3().minus(this.point.toVect3());
		double sa = a.scalar(this.normal);
		double sb = b.scalar(this.normal);
		double sc = c.scalar(this.normal);
		return((sa>=0 && sb<=0) || (sa>=0 && sc<=0) || (sb>=0 && sc<=0) || 
			   (sa<=0 && sb>=0) || (sa<=0 && sc>=0) || (sb<=0 && sc>=0));
	}
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Vect3 getNormal() {
		return this.normal;
	}

	public void setNormal(Vect3 normal) {
		this.normal = normal;
	}	
	
}
