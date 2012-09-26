package objects;


import materials.Material;

public abstract class Primitive implements Object3D {

	protected Material material;

	public Primitive(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
}
