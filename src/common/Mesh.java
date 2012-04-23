package common;

import io.OBJObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
	
	public OBJObject getOBJObject(String name, int iInit) {
		OBJObject obj = new OBJObject(name);
		HashMap<Point, Integer> pointsToInt = new HashMap<Point, Integer>();
		ArrayList<Point> points = new ArrayList<Point>();
		
		int i = iInit;
		for(Triangle triangle : this.triangles) {
			if(!pointsToInt.containsKey(triangle.getA())) {
				pointsToInt.put(triangle.getA(), i++);
				points.add(triangle.getA());
			}
			if(!pointsToInt.containsKey(triangle.getB())) {
				pointsToInt.put(triangle.getB(), i++);
				points.add(triangle.getB());
			}
			if(!pointsToInt.containsKey(triangle.getC())) {
				pointsToInt.put(triangle.getC(), i++);
				points.add(triangle.getC());
			}
		}
		
		for(Point point : points) {
			obj.addVertex(point.getX(), point.getY(), point.getZ());
		}
		
		int[] face = {0,0,0};
		for(Triangle triangle : this.triangles) {
			face[0] = pointsToInt.get(triangle.getA());
			face[1] = pointsToInt.get(triangle.getB());
			face[2] = pointsToInt.get(triangle.getC());
			obj.addFace(face);
		}
		
		obj.iPoints = i;
		return obj;
	}
	
	public Collection<Triangle> getTriangles() {
		return this.triangles;
	}
	
}
