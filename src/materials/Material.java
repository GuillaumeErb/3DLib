package materials;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public interface Material {
	public Color renderRay(Ray ray, Intersection intersection, Scene scene);
}
