package objects;

import raytracer.Intersection;
import raytracer.Ray;

public interface Object3D {

	public Intersection getIntersection(Ray ray);
	
}
