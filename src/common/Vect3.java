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
		return x*v.getX()+y*v.getY()+z*v.getZ();
	}
	
	public Vect3 normalize() {
		double norm = this.norm();
		if(norm != 0) {
			return new Vect3(x/norm, y/norm, z/norm);
		} else {
			return new Vect3(0, 0, 0);
		}
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
	
	public boolean colinear(Vect3 v) {
		Vect3 tn = this.normalize();
		Vect3 vn = this.normalize();
		double c = tn.scalar(vn);
		return c == 1 || c == -1;
	}
	
	public Vect3 symmetry(Vect3 v) {
		return this.times(-1).plus(v.times(2*this.scalar(v)));
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


	@Override
	public String toString() {
		return "Vect3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
}
