package objects;

import io.OBJObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import raytracer.Intersection;
import raytracer.Ray;

import common.Point;
import common.Vect3;

public class Mesh implements Object3D {

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

	
	public List<Intersection> getIntersections(Ray ray) {
		
		Intersection intersection;
		List<Intersection> intersections = new ArrayList<Intersection>();
		for(Triangle object : this.triangles) {
			intersection = object.getIntersection(ray);
			if(intersection != null) {
				intersections.add(intersection);
			}
		}
		return intersections;
	}
	
	@Override
	public Intersection getIntersection(Ray ray) {

		Intersection intersection = null;
		Intersection tempIntersection;
		double distance = Double.MAX_VALUE;

		List<Intersection> intersections = this.getIntersections(ray);
		ArrayList<Double> distances = new ArrayList<Double>();
		for(Intersection i : intersections) {
			distances.add(i.getDistance());
		}
		
		
		for(Intersection i : intersections) {
			if(i.getDistance()>0) {
				 intersection = i;
//				 intersection.setDistance(intersection.getDistance()*-1);
			}
		}
		
		
		for(Triangle object : this.triangles)	{
			tempIntersection = object.getIntersection(ray);
			if(tempIntersection != null && 
//			   tempIntersection.getDistance() > 0 && 
			   tempIntersection.getDistance() < distance) {
				intersection = tempIntersection;
				distance = tempIntersection.getDistance();
			}
	    }
		
		if(intersection != null) {
		
			Triangle triangle = (Triangle) intersection.getPrimitive();
			Point point = new Point(ray.getOrigin().toVect3().plus(
					ray.getDirection().times(intersection.getDistance())));
			
//			Vect3 ab = triangle.getB().toVect3().minus(triangle.getA().toVect3());
//			Vect3 ac = triangle.getC().toVect3().minus(triangle.getA().toVect3());
			boolean softShading = true;
			if(softShading) {
				Triangle apb = new Triangle(triangle.getA(), point, triangle.getB());
				Triangle apc = new Triangle(triangle.getA(), point, triangle.getC());
				Triangle bpc = new Triangle(triangle.getB(), point, triangle.getC());
				
				Vect3 normal = this.vertexToNormals.get(triangle.getA()).times(bpc.area()).plus(
							   this.vertexToNormals.get(triangle.getB()).times(apc.area())).plus(
							   this.vertexToNormals.get(triangle.getC()).times(apb.area())).normalize();
				intersection.setNormal(normal);
			}
//			} else {
//				intersection.setNormal(intersection.getNormal().times(-1));
//			}
		}
		
		return intersection;

	}
	
}
