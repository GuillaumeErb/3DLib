package materials;

import lighting.Light;

import common.Point;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public class Lambert implements Material {

	Color color;
	
	public Lambert(Color color) {
		this.color = color;
	}

	@Override
	public Color renderRay(Ray ray, Intersection intersection, Scene scene) {
		Point pinter = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		for(Light light : scene.lights) {
			if(light.lightsPoint(pinter, scene)) {
				return this.color;
			}
		}
		return new Color(0,0,0);
	}

}
