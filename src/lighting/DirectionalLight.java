package lighting;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.SimpleObject;

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
		for(SimpleObject object : scene.getObjects()) {
			Intersection i = 
					object.getIntersection(
							new Ray(point,
									this.direction.times(-1)));
			
			if(i != null) {
				return false;
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

}
