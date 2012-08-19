package pointcloud;

import io.OBJObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import objects.Mesh;


import common.Point;
import common.Simplex;
import common.Triangle;

//TODO This class definitely needs illustrations !!!

public class DelaunayNaive {

	private Simplex superSimplex;
	
	/**
	 * Collection of the points already added to the mesh.
	 */
	private Collection<Point> points;
	
	/**
	 * Collection of the simplices allowing us to search simplices in it.
	 * Pointer shared between all the simplices
	 */
	public Set<Simplex> simplices;
	
	
	public DelaunayNaive(Simplex simplex) {
		this.superSimplex = simplex;
		this.points = new ArrayList<Point>();
		this.points.add(simplex.getA());
		this.points.add(simplex.getB());
		this.points.add(simplex.getC());
		this.points.add(simplex.getD());
		this.simplices = new HashSet<Simplex>();
		this.simplices.add(simplex);
	}
	
	
	private void addPoint(Point point) {
		Collection<Triangle> toAdd = new ArrayList<Triangle>();
		Collection<Simplex> toRemove = new ArrayList<Simplex>();
		points.add(point);
		for(Simplex s : this.simplices) {
			if(s.circumSphereContains(point) >= 1) {
				toAdd.add(new Triangle(s.getA(), s.getB(), s.getC()));
				toAdd.add(new Triangle(s.getA(), s.getB(), s.getD()));
				toAdd.add(new Triangle(s.getA(), s.getC(), s.getD()));
				toAdd.add(new Triangle(s.getB(), s.getC(), s.getD()));
				toRemove.add(s);
			}
		}
		
		System.out.println("#####################");
		Collection<Triangle> toReallyAdd = new ArrayList<Triangle>();
		for(Triangle t : toAdd) {
			if(toReallyAdd.contains(t)) {
				toReallyAdd.remove(t);
			} else {
				System.out.println(t);
				toReallyAdd.add(t);
			}
		}
		System.out.println("###########-#########");

		
		this.simplices.removeAll(toRemove);
//		this.simplices.clear();
		for(Triangle t : toReallyAdd) {
			this.simplices.add(new Simplex(t,point));
		}
		
		System.out.println("--------------");
		for(Simplex s : this.simplices) {
			System.out.println(s);
		}
		System.out.println("------#-------");
		
		System.out.println(point + " added");
	}
	
	public DelaunayNaive triangulate(Collection<Point> points) {
		int i=0;
		for(Point p : points) {
			if(i<2) {
				this.addPoint(p);
			}
			i++;
		}
//		this.removeSuperSimplex();
		return this;
		
	}
	
	public void removeSuperSimplex() {
		HashSet<Simplex> toRemove = new HashSet<Simplex>();
		for(Point p : this.superSimplex.getPoints()) {
			for(Simplex s : this.simplices) {
				if(s.hasAsVertex(p)) {
					toRemove.add(s);
				}
			}
			this.points.remove(p);
		}
		this.simplices.removeAll(toRemove);
	}
	
	public Mesh toMesh() {
		return null;
	}
	
	public OBJObject getOBJObject(String name, int iInit) {
		OBJObject obj = new OBJObject(name);
		HashMap<Point, Integer> pointsToInt = new HashMap<Point, Integer>();
		
		int i = iInit;
		for(Point point : this.points) {
			pointsToInt.put(point, i);
			obj.addVertex(point.getX(), point.getY(), point.getZ());
			i++;
		}
		
		int[] face = {0,0,0};
		for(Simplex simplex : this.simplices) {
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getB());
			face[2] = pointsToInt.get(simplex.getC());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getB());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getC());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getB());
			face[1] = pointsToInt.get(simplex.getC());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
		}
		
		obj.iPoints = i;
		return obj;
	}


	public Mesh transformToMesh(Collection<Point> pointCloud) {
		return null;
	}	
}
