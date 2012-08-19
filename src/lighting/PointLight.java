package lighting;

import objects.Object3D;
import common.Point;
import common.Vect3;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public class PointLight extends Light {

	private Point point;

	public PointLight(Color color, double intensity, Point point) {
		super(color, intensity);
		this.point = point;
	}

	@Override
	public boolean lightsPoint(Point point, Scene scene) {
		for(Object3D object : scene.getObjects()) {
			Intersection i = 
					object.getIntersection(
							new Ray(point, 
									point.toVect3().minus(
											this.point.toVect3()).normalize()));
			
			if(i != null) {
				return false;
			}
		}
		return true;
	}	
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public Vect3 fromPointToLight(Point point) {
		return this.point.toVect3().minus(point.toVect3()).normalize();
	}

}
