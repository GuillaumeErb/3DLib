package common;

public class Vect3 {

	private double x;
	private double y;
	private double z;
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vect3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	public double norm() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	public double scalar(Vect3 v) {
		return Math.sqrt(x*v.getX()+y*v.getY()+z*v.getZ());
	}
	
	public Vect3 normalize() {
		double norm = this.norm();
		return new Vect3(x/norm, y/norm, z/norm);
	}
	
	public Vect3 cross(Vect3 v) {
		return new Vect3(y*v.getZ() - z*v.getY(),
					     z*v.getX() - x*v.getZ(),
					     x*v.getY() - y*v.getX());
	}
	
	public Vect3 plus(Vect3 v) {
		return new Vect3(x+v.getX(),
			             y+v.getY(),
			             z+v.getZ());
	}
	
	public Vect3 minus(Vect3 v) {
		return new Vect3(x-v.getX(),
			             y-v.getY(),
			             z-v.getZ());
	}
	
	public Vect3 times(Vect3 v) {
		return new Vect3(x*v.getX(),
			             y*v.getY(),
			             z*v.getZ());
	}
	
	public Vect3 times(double d) {
		return new Vect3(x*d,
			             y*d,
			             z*d);
	}
	
	public Vect3 dividedBy(double d) {
		return new Vect3(x/d,
			             y/d,
			             z/d);
	}
	
	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getZ() {
		return z;
	}


	public void setZ(double z) {
		this.z = z;
	}
}
