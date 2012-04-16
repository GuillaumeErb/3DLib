package common;

import java.util.ArrayList;
import java.util.Collection;

public class Mesh {

	private Collection<Triangle> triangles;

	/**
	 * @param triangles
	 */
	public Mesh(Collection<Triangle> triangles) {
		super();
		this.triangles = triangles;
	}
	
	public Mesh() {
		super();
		this.triangles = new ArrayList<Triangle>();
	}
	
	public void addTriangle(Triangle t) {
		triangles.add(t);
	}
}
