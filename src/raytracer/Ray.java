package raytracer;

import java.io.Serializable;

import common.Point;
import common.Vect3;


public class Ray implements Serializable {
	private Point origin;
	private Vect3 direction;
	
	public Ray(Point origin, Vect3 direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Vect3 getDirection() {
		return direction;
	}

	public void setDirection(Vect3 direction) {
		this.direction = direction;
	}
	
}
