package objects;

import java.io.Serializable;
import java.util.List;

import raytracer.Intersection;
import raytracer.Ray;

public interface Object3D extends Serializable {

	public Intersection getIntersection(Ray ray);
	public List<Intersection> getIntersections(Ray ray);
	
}
