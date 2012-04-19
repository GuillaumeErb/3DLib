package common;

import io.OBJObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
		this.setCurrent(simplex);
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

	
	public void execute(Collection<Point> points) {
		for(Point point : points) {
			this.addPoint(point);
		}
	}
	
	private DelaunaySimplices getNeighbor(Point pa, Point pb, Point pc) {
		for(DelaunaySimplices ds : neighbors) {
			if(ds.getCurrent().hasAsVertices(pa, pb, pc)) {
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
	
	
	public void addPoint(Point point) {
		
		this.points.add(point);
		
		DelaunaySimplices dsim = this.getContainingSimplex(point);
		Collection<DelaunaySimplices> critical = dsim.getCriticalSimplices(point);

		for(DelaunaySimplices ds : critical) {
			ds.remove();
		}
		
		for(DelaunaySimplices ds : critical) {
			for(Point sPoint : ds.getCurrent().getPoints()) {
				Simplex s1 = new Simplex(current.getA(), current.getB(), current.getC(), sPoint);
				Simplex s2 = new Simplex(current.getA(), current.getB(), current.getD(), sPoint);
				Simplex s3 = new Simplex(current.getA(), current.getC(), current.getD(), sPoint);
				Simplex s4 = new Simplex(current.getB(), current.getC(), current.getD(), sPoint);

				DelaunaySimplices ds1 = new DelaunaySimplices(this.simplices, s1, points);
				DelaunaySimplices ds2 = new DelaunaySimplices(this.simplices, s2, points);
				DelaunaySimplices ds3 = new DelaunaySimplices(this.simplices, s3, points);
				DelaunaySimplices ds4 = new DelaunaySimplices(this.simplices, s4, points);

				this.simplices.add(ds1);
				this.simplices.add(ds2);
				this.simplices.add(ds3);
				this.simplices.add(ds4);

				DelaunaySimplices dsabc = this.getNeighbor(current.getA(), current.getB(), current.getC());
				DelaunaySimplices dsabd = this.getNeighbor(current.getA(), current.getB(), current.getD());
				DelaunaySimplices dsacd = this.getNeighbor(current.getA(), current.getC(), current.getD());
				DelaunaySimplices dsbcd = this.getNeighbor(current.getB(), current.getC(), current.getD());
				
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
		}
		
	}
	
	
	private Collection<DelaunaySimplices> getCriticalSimplices(Point point) {
		Collection<DelaunaySimplices> result = new ArrayList<DelaunaySimplices>();
		
		for(DelaunaySimplices ds : this.neighbors) {
			if(/*!ds.equals(this) && */ds.getCurrent().circumSphereContains(point) >= 1) {
				result.add(ds);
			}
		}
		
		result.add(this);
		
		return result;
	}
	
	
	public DelaunaySimplices getContainingSimplex(Point point) {
		for(DelaunaySimplices s : simplices) {
			if(s.getCurrent().contains(point)) {
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
			face[0] = pointsToInt.get(simplex.getCurrent().getA());
			face[1] = pointsToInt.get(simplex.getCurrent().getB());
			face[2] = pointsToInt.get(simplex.getCurrent().getC());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getCurrent().getA());
			face[1] = pointsToInt.get(simplex.getCurrent().getB());
			face[2] = pointsToInt.get(simplex.getCurrent().getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getCurrent().getA());
			face[1] = pointsToInt.get(simplex.getCurrent().getC());
			face[2] = pointsToInt.get(simplex.getCurrent().getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getCurrent().getB());
			face[1] = pointsToInt.get(simplex.getCurrent().getC());
			face[2] = pointsToInt.get(simplex.getCurrent().getD());
			obj.addFace(face);
		}
		
		return obj;
	}
	
	public Simplex getCurrent() {
		return current;
	}

	public void setCurrent(Simplex current) {
		this.current = current;
	}

	public Collection<DelaunaySimplices> getSimplices() {
		return simplices;
	}

	public Collection<Point> getPoints() {
		return points;
	}

	public void setPoints(Collection<Point> points) {
		this.points = points;
	}

	public Collection<DelaunaySimplices> getNeighbors() {
		return neighbors;
	}

	public void addNeighbors(DelaunaySimplices simplex) {
		this.neighbors.add(simplex);
	}
	
}
