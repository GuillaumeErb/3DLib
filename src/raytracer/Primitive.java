package raytracer;


import materials.Material;

public abstract class Primitive extends SimpleObject {

	public Primitive(Material material) {
		super(material);
	}
}
