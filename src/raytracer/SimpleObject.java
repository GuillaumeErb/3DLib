package raytracer;

import materials.Material;

public abstract class SimpleObject {
	
	private Material material;

	public SimpleObject(Material material) {
		super();
		this.material = material;
	}
	
	public abstract Intersection getIntersection(Ray ray);

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
