package lighting;

import java.util.List;

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
							new Ray(new Point(point.toVect3().plus(this.direction.times(Math.pow(10, -10)))),
									this.direction.times(-1)));
			
//			List<Intersection> li = object.getIntersections(
//					new Ray(point,
//							this.direction.times(-1)));
//			if(i == null) {
//				if(li != null) {
//					if(!li.isEmpty()) {
//						System.out.println("ERROR");
//					}
//				}
//			}
			
			if(i != null) {
//				System.out.println(i.getDistance());
//				if(Math.abs(i.getDistance()) < Math.pow(10, -10) ||
//				   i.getDistance() > 0.000001) {
//					return false;
//				}
//				if(Math.abs(i.getDistance()) < Math.pow(10, -10) ||
//						   if(i.getDistance() > Math.pow(10, -10)) {
//							return false;
//						}
				
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

	@Override
	public Vect3 fromPointToLight(Point point) {
		return this.direction.times(-1);
	}

}
