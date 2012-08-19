package lighting;

import objects.Object3D;
import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

import common.Point;
import common.Vect3;

public class DirectionalLight extends Light {

	private Vect3 direction;

	public DirectionalLight(Color color, double intensity, Vect3 direction) {

		super(color, intensity);
		this.direction = direction;
	}

	@Override
	public boolean lightsPoint(Point point, Scene scene) {
		Intersection i = null;
		for(Object3D object : scene.getObjects()) {
			i =	object.getIntersection(
							new Ray(point,
									this.direction.times(-1)));
			
			if(i != null) {
				if(Math.abs(i.getDistance()) < 0.001 ||
				   i.getDistance() > 0.001) {
					return false;
				}
			}
		}
		
		return true;
	}

	public Vect3 getDirection() {
		return direction;
	}

	public void setDirection(Vect3 direction) {
		this.direction = direction;
	}

	@Override
	public Vect3 fromPointToLight(Point point) {
		return this.direction.times(-1);
	}

}
