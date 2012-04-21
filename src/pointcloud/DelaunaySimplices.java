package pointcloud;

import io.OBJObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.Mesh;
import common.Point;
import common.Simplex;
import common.Triangle;

//TODO This class definitely needs illustrations !!!

public class DelaunaySimplices {

	/**
	 * Collection of the points already added to the mesh.
	 */
	private Collection<Point> points;
	
	/**
	 * Collection of the simplices allowing us to search simplices in it.
	 * Pointer shared between all the simplices
	 */
	private Set<DelaunaySimplices> simplices;
	
	/**
	 * The current simplex (structure of list)
	 */
	private Simplex current;
	

	private Collection<DelaunaySimplices> neighbors;
	
	
	public DelaunaySimplices(Simplex simplex) {
		this.current = simplex;
		this.neighbors = new ArrayList<DelaunaySimplices>();
		this.points = new ArrayList<Point>();
		this.points.add(simplex.getA());
		this.points.add(simplex.getB());
		this.points.add(simplex.getC());
		this.points.add(simplex.getD());
		this.simplices = new HashSet<DelaunaySimplices>();
		this.simplices.add(this);
	}
	
	
	public DelaunaySimplices(Set<DelaunaySimplices> simplices, Simplex s, Collection<Point> points) {
		this.simplices = simplices;
		this.current = s;
		this.neighbors = new ArrayList<DelaunaySimplices>();
		this.points = points;
	}

	
	public void triangulate(Collection<Point> points) {
		for(Point point : points) {
			this.addPoint(point);
		}
		
		this.makeDisapear(this.current.getPoints());
	}
	
	
	public void triangulate(Collection<Point> points, Collection<Point> virtualPoints) {
		for(Point point : points) {
			this.addPoint(point);
		}
		this.makeDisapear(this.current.getPoints());
		this.makeDisapear(virtualPoints);
	}
	
	
	public Mesh transformToMesh(Collection<Point> points) {
		Mesh mesh = new Mesh();
		
		for(Point point : points) {
			this.addPoint(point);
		}
		
		Collection<Point> superPoints = this.current.getPoints();
		
		int i = 0;
		Triangle candidate = null;
		for(DelaunaySimplices ds : this.simplices) {
			for(Point p : superPoints) {
				if(ds.current.hasAsVertex(p)) {
					i++;
					candidate = ds.current.extractTriangle(p);
				}
			}
			if(i==1) {
				mesh.addTriangle(candidate);
				i=0;
			}
		}
		
		return mesh;
	}
	
	private void makeDisapear(Collection<Point> rPoints) {
		Set<DelaunaySimplices> toBeRemoved = new HashSet<DelaunaySimplices>();
		for(Point point : rPoints) {
			for(DelaunaySimplices simplex : this.simplices) {
				if(simplex.current.hasAsVertex(point)) {
					toBeRemoved.add(simplex);
				}
			}
			this.points.remove(point);
		}
		for(DelaunaySimplices simplex : toBeRemoved) {
			simplex.remove();
		}
	}
	
	
	private DelaunaySimplices getNeighbor(Point pa, Point pb, Point pc) {
		for(DelaunaySimplices ds : neighbors) {
			if(ds.current.hasAsVertices(pa, pb, pc)) {
				return ds;
			}
		}
		return null;
	}
	
	private void remove() {
		this.simplices.remove(this);
		for(DelaunaySimplices ds : this.neighbors) {
			ds.neighbors.remove(this);
		}
	}
	
	
	private void addPoint(Point point) {
		
		this.points.add(point);
		
		DelaunaySimplices dsim = this.getContainingSimplex(point);
		Collection<DelaunaySimplices> critical = dsim.getCriticalSimplices(point);

		for(DelaunaySimplices ds : critical) {
			ds.remove();
		}
		
		for(DelaunaySimplices ds : critical) {
				Simplex s1 = new Simplex(ds.current.getA(), ds.current.getB(), ds.current.getC(), point);
				Simplex s2 = new Simplex(ds.current.getA(), ds.current.getB(), ds.current.getD(), point);
				Simplex s3 = new Simplex(ds.current.getA(), ds.current.getC(), ds.current.getD(), point);
				Simplex s4 = new Simplex(ds.current.getB(), ds.current.getC(), ds.current.getD(), point);

				DelaunaySimplices ds1 = new DelaunaySimplices(this.simplices, s1, points);
				DelaunaySimplices ds2 = new DelaunaySimplices(this.simplices, s2, points);
				DelaunaySimplices ds3 = new DelaunaySimplices(this.simplices, s3, points);
				DelaunaySimplices ds4 = new DelaunaySimplices(this.simplices, s4, points);

				this.simplices.add(ds1);
				this.simplices.add(ds2);
				this.simplices.add(ds3);
				this.simplices.add(ds4);

				DelaunaySimplices dsabc = ds.getNeighbor(ds.current.getA(), ds.current.getB(), ds.current.getC());
				DelaunaySimplices dsabd = ds.getNeighbor(ds.current.getA(), ds.current.getB(), ds.current.getD());
				DelaunaySimplices dsacd = ds.getNeighbor(ds.current.getA(), ds.current.getC(), ds.current.getD());
				DelaunaySimplices dsbcd = ds.getNeighbor(ds.current.getB(), ds.current.getC(), ds.current.getD());
				
				if(dsabc != null) {
					ds1.addNeighbors(dsabc);
					dsabc.addNeighbors(ds1);
				}
				ds1.addNeighbors(ds2);
				ds1.addNeighbors(ds3);
				ds1.addNeighbors(ds4);
				

				if(dsabd != null) {
					ds2.addNeighbors(dsabd);
					dsabd.addNeighbors(ds2);
				}
				ds2.addNeighbors(ds1);
				ds2.addNeighbors(ds3);
				ds2.addNeighbors(ds4);
				

				if(dsacd != null) {
					ds3.addNeighbors(dsacd);
					dsacd.addNeighbors(ds3);
				}
				ds3.addNeighbors(ds1);
				ds3.addNeighbors(ds2);
				ds3.addNeighbors(ds4);
				
				
				if(dsbcd != null) {
					ds4.addNeighbors(dsbcd);
					dsbcd.addNeighbors(ds4);
				}
				ds4.addNeighbors(ds1);
				ds4.addNeighbors(ds2);
				ds4.addNeighbors(ds3);
				
		}
		System.out.println(point + " added.");
	}
	
	
	private Collection<DelaunaySimplices> getCriticalSimplices(Point point) {
		Collection<DelaunaySimplices> result = new ArrayList<DelaunaySimplices>();
		
		for(DelaunaySimplices ds : this.neighbors) {
			if(ds.current.circumSphereContains(point) >= 1) {
				result.add(ds);
			}
		}
		
		result.add(this);
		
		return result;
	}
	
	
	private DelaunaySimplices getContainingSimplex(Point point) {
		for(DelaunaySimplices s : simplices) {
			if(s.current.contains(point)) {
				return s;
			}
		}
		return null;
	}
	
	public OBJObject getOBJObject(String name) {
		OBJObject obj = new OBJObject(name);
		HashMap<Point, Integer> pointsToInt = new HashMap<Point, Integer>();
		
		int i = 1;
		for(Point point : this.points) {
			pointsToInt.put(point, i);
			obj.addVertex(point.getX(), point.getY(), point.getZ());
			i++;
		}
		
		int[] face = {0,0,0};
		for(DelaunaySimplices simplex : this.simplices) {
			face[0] = pointsToInt.get(simplex.current.getA());
			face[1] = pointsToInt.get(simplex.current.getB());
			face[2] = pointsToInt.get(simplex.current.getC());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.current.getA());
			face[1] = pointsToInt.get(simplex.current.getB());
			face[2] = pointsToInt.get(simplex.current.getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.current.getA());
			face[1] = pointsToInt.get(simplex.current.getC());
			face[2] = pointsToInt.get(simplex.current.getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.current.getB());
			face[1] = pointsToInt.get(simplex.current.getC());
			face[2] = pointsToInt.get(simplex.current.getD());
			obj.addFace(face);
		}
		
		return obj;
	}
	


	public void addNeighbors(DelaunaySimplices simplex) {
		this.neighbors.add(simplex);
	}
	
}
