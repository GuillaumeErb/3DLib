package common;

import java.util.ArrayList;
import java.util.Collection;

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
	

	public DelaunaySimplices(Point ddd, Point ddu, Point dud, Point duu,
							 Point udd, Point udu, Point uud, Point uuu) {
		
		ArrayList<Point> points = new ArrayList<>(8);
		points.add(ddd);
		points.add(ddu);
		points.add(dud);
		points.add(duu);
		points.add(udd);
		points.add(udu);
		points.add(uud);
		points.add(uuu);
		
		
		Simplex s1 = new Simplex(ddd, udd, ddu, dud);
		Simplex s2 = new Simplex(uud, dud, uuu, udd);
		Simplex s3 = new Simplex(duu, uuu, dud, ddu);
		Simplex s4 = new Simplex(udu, ddu, udd, uuu);
		Simplex s5 = new Simplex(uuu, ddu, udd, dud);
		
		this.setCurrent(s1); this.setPoints(points);
		DelaunaySimplices ds2 = new DelaunaySimplices(points, s2);
		DelaunaySimplices ds3 = new DelaunaySimplices(points, s3);
		DelaunaySimplices ds4 = new DelaunaySimplices(points, s4);
		DelaunaySimplices ds5 = new DelaunaySimplices(points, s5);
		
		this.simplices = new ArrayList<>();
		this.simplices.add(this);
		this.simplices.add(ds2);
		this.simplices.add(ds3);
		this.simplices.add(ds4);
		this.simplices.add(ds5);
		
		ds2.setSimplices(simplices);
		ds3.setSimplices(simplices);
		ds4.setSimplices(simplices);
		ds5.setSimplices(simplices);
		
		
		this.getNeighbors().add(ds5);
		
		ds2.getNeighbors().add(ds5);
		
		ds3.getNeighbors().add(ds5);
		
		ds4.getNeighbors().add(ds5);
	
		ds5.getNeighbors().add(this);
		ds5.getNeighbors().add(ds2);
		ds5.getNeighbors().add(ds3);
		ds5.getNeighbors().add(ds4);

	}
	
	/**
	 * Be carefull, you have to make a setSimplices after !
	 * @param points 
	 * @param current
	 */
	private DelaunaySimplices(ArrayList<Point> points, Simplex current) {
		this.setCurrent(current);
		this.setPoints(points);
		this.neighbors = new ArrayList<DelaunaySimplices>();
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
	
	
	public void addPoint(Point point) {
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
		
		
		//TODO Check the Delaunay criteria
		
	}
	
	private void checkDelaunayCriteria(DelaunaySimplices simplex) {

	}
	
	private void flipWith(DelaunaySimplices simplex) {
		
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
