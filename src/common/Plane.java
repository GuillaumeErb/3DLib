package common;

public class Plane {
	
	private Vect3 firstVector;
	
	private Vect3 secondVector;
	
	private Point point;
	

	public Plane() {
		super();
	}
	
	public Plane(Vect3 firstVector, Vect3 secondVector, Point point) {
		super();
		this.firstVector = firstVector;
		this.secondVector = secondVector;
		this.point = point;
	}

	public double distance(Point p) {
		Vect3 n = firstVector.cross(secondVector).normalize();
		return Math.abs(p.toVect3().minus(point.toVect3()).scalar(n));
	}
	
	public boolean intersects(Triangle f) {
		Vect3 normal = firstVector.cross(secondVector);
		Vect3 a = f.getA().toVect3().minus(this.point.toVect3());
		Vect3 b = f.getB().toVect3().minus(this.point.toVect3());
		Vect3 c = f.getC().toVect3().minus(this.point.toVect3());
		double sa = a.scalar(normal);
		double sb = b.scalar(normal);
		double sc = c.scalar(normal);
		return((sa>=0 && sb<=0) || (sa>=0 && sc<=0) || (sb>=0 && sc<=0) || 
			   (sa<=0 && sb>=0) || (sa<=0 && sc>=0) || (sb<=0 && sc>=0));
	}

	public Vect3 getFirstVector() {
		return firstVector;
	}

	public void setFirstVector(Vect3 firstVector) {
		this.firstVector = firstVector;
	}

	public Vect3 getSecondVector() {
		return secondVector;
	}

	public void setSecondVector(Vect3 secondVector) {
		this.secondVector = secondVector;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	
	
}
