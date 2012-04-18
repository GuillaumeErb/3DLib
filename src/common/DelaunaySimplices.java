package common;

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
	private Collection<DelaunaySimplices> simplices;
	
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
	}
	
	
	public DelaunaySimplices(Collection<DelaunaySimplices> simplices, Simplex s) {
		this.simplices = simplices;
		this.current = s;
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
	
	
	public DelaunaySimplices addPoint(Point point) {
		DelaunaySimplices dsim = this.getContainingSimplex(point);
		Simplex current = dsim.getCurrent();
		
		Simplex s1 = new Simplex(current.getA(), current.getB(), current.getC(), point);
		Simplex s2 = new Simplex(current.getA(), current.getB(), current.getD(), point);
		Simplex s3 = new Simplex(current.getA(), current.getC(), current.getD(), point);
		Simplex s4 = new Simplex(current.getB(), current.getC(), current.getD(), point);
		
		DelaunaySimplices ds1 = new DelaunaySimplices(this.simplices, s1);
		DelaunaySimplices ds2 = new DelaunaySimplices(this.simplices, s2);
		DelaunaySimplices ds3 = new DelaunaySimplices(this.simplices, s3);
		DelaunaySimplices ds4 = new DelaunaySimplices(this.simplices, s4);
		
		this.simplices.add(ds1);
		this.simplices.add(ds2);
		this.simplices.add(ds3);
		this.simplices.add(ds4);

		
		ds1.addNeighbors(this.getNeighbor(current.getA(), current.getB(), current.getC()));
		ds1.addNeighbors(ds2);
		ds1.addNeighbors(ds3);
		ds1.addNeighbors(ds4);
		
		ds2.addNeighbors(this.getNeighbor(current.getA(), current.getB(), current.getD()));
		ds2.addNeighbors(ds1);
		ds2.addNeighbors(ds3);
		ds2.addNeighbors(ds4);
		
		ds3.addNeighbors(this.getNeighbor(current.getA(), current.getC(), current.getD()));
		ds3.addNeighbors(ds1);
		ds3.addNeighbors(ds2);
		ds3.addNeighbors(ds4);
		
		ds4.addNeighbors(this.getNeighbor(current.getB(), current.getC(), current.getD()));
		ds4.addNeighbors(ds1);
		ds4.addNeighbors(ds2);
		ds4.addNeighbors(ds3);
		
		this.remove();
		
		return ds1;
		//TODO Check the Delaunay criteria
		
	}
	
	
	private HashMap<Point, DelaunaySimplices> getCriticalPoints() {
		HashMap<Point,DelaunaySimplices> result = new HashMap<Point,DelaunaySimplices>();
		for(DelaunaySimplices ds : this.neighbors) {
			result.put(ds.getCurrent().getA(), ds);
			result.put(ds.getCurrent().getB(), ds);
			result.put(ds.getCurrent().getC(), ds);
			result.put(ds.getCurrent().getD(), ds);
		}
		result.remove(this.getCurrent().getA());
		result.remove(this.getCurrent().getB());
		result.remove(this.getCurrent().getC());
		result.remove(this.getCurrent().getD());
		
		return result;
	}
	
	
	private DelaunaySimplices checkDelaunayCriteria() {
		HashMap<Point, DelaunaySimplices> criticalPoints = this.getCriticalPoints();
		for(Point point : criticalPoints.keySet()) {
			if(this.getCurrent().circumSphereContains(point) == 1) {
				return criticalPoints.get(point);
			}
		}
		return null;
	}
	
	private void flipWith(DelaunaySimplices simplex) {
		
		Collection<Point> common = this.getCurrent().getCommonPoints(simplex.getCurrent());
		
		Point a1 = this.getCurrent().getA();
		Point b1 = this.getCurrent().getB();
		Point c1 = this.getCurrent().getC();
		Point d1 = this.getCurrent().getD();
		
		Point a2 = simplex.getCurrent().getA();
		Point b2 = simplex.getCurrent().getB();
		Point c2 = simplex.getCurrent().getC();
		Point d2 = simplex.getCurrent().getD();
		
		
	}
	
	public DelaunaySimplices getContainingSimplex(Point point) {
		for(DelaunaySimplices s : simplices) {
			if(s.getCurrent().contains(point)) {
				return s;
			}
		}
		return null;
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

	public void setSimplices(Collection<DelaunaySimplices> simplices) {
		this.simplices = simplices;
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
