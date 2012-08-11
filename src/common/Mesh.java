package common;

import io.OBJObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Mesh {

	private Set<Triangle> triangles;

	private HashMap<Point, Set<Triangle>> vertexToSurroundingTriangles;
	
	private HashMap<Point, Vect3> vertexToNormals;
	
	/**
	 * @param triangles
	 */
	public Mesh(Set<Triangle> triangles,
				HashMap<Point, Set<Triangle>> vertexToSurroundingTriangles,
				HashMap<Point, Vect3> vertexToNormals) {
		super();
		this.triangles = triangles;
		this.vertexToSurroundingTriangles = vertexToSurroundingTriangles;
		this.vertexToNormals = vertexToNormals;
	}
	
	public Mesh() {
		super();
		this.triangles = new HashSet<Triangle>();
		this.vertexToSurroundingTriangles = new HashMap<Point, Set<Triangle>>();
		this.vertexToNormals = new HashMap<Point, Vect3>();
	}
	
	public Set<Triangle> getSurroundingTriangles(Point p) {
		return vertexToSurroundingTriangles.get(p);
	}
	
	public Set<Point> getPoints() {
		return vertexToSurroundingTriangles.keySet();
	}
	
	public Set<Triangle> getTriangles() {
		return this.triangles;
	}
	
	public void addTriangle(Triangle t) {
		triangles.add(t);
		for(Point vertex : t.getPoints()) {
			this.addVertex(vertex, t);
		}
	}
	
	private void addVertex(Point p, Triangle t) {
		if(this.vertexToSurroundingTriangles.containsKey(p)) {
			this.vertexToSurroundingTriangles.get(p).add(t);
		} else {
			Set<Triangle> triangleList = new HashSet<Triangle>();
			triangleList.add(t);
			this.vertexToSurroundingTriangles.put(p, triangleList);
		}
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
	

	public void addNormal(Point p, Vect3 n) {
		this.vertexToNormals.put(p, n);
	}
	
}
