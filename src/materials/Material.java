package materials;

import java.io.Serializable;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public interface Material extends Serializable {
	
	public Color renderRay(Ray ray, Intersection intersection, Scene scene, int recursionsLeft);

}
