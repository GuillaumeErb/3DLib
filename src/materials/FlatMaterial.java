package materials;


import common.Point;

import lighting.Light;
import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public class FlatMaterial implements Material {

	private Color color;
		
	public FlatMaterial(Color color) {
		super();
		this.color = color;
	}

	public Color renderRay(Ray ray, Intersection intersection, Scene scene) {
		Point pinter = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		for(Light light : scene.lights) {
			if(light.lightsPoint(pinter, scene)) {
				return this.color;
			}
		}
		return new Color(0,0,0);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	
}
