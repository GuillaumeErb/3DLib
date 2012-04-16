package common;

import java.util.ArrayList;
import java.util.Collection;

public class DelaunaySimplices {

	/**
	 * Collection of the simplices allowing us to search simplices in it.
	 * Pointer shared between all the simplices
	 */
	private Collection<DelaunaySimplices> simplices;
	
	/**
	 * The current simplex (structure of list)
	 */
	private Simplex current;
	
	/**
	 * Neighbors of the current simplex
	 */
	private Collection<DelaunaySimplices> neighbors;


	public DelaunaySimplices(Point ddd, Point ddu, Point dud, Point duu,
							 Point udd, Point udu, Point uud, Point uuu) {
		
		Simplex s1 = new Simplex(ddd, udd, ddu, dud);
		Simplex s2 = new Simplex(dud, uud, udd, uuu);
		Simplex s3 = new Simplex(uuu, duu, dud, ddu);
		Simplex s4 = new Simplex(ddu, udu, udd, uuu);
		Simplex s5 = new Simplex(uuu, udd, ddu, dud);
		
		this.setCurrent(s1);
		DelaunaySimplices ds2 = new DelaunaySimplices(s2);
		DelaunaySimplices ds3 = new DelaunaySimplices(s3);
		DelaunaySimplices ds4 = new DelaunaySimplices(s4);
		DelaunaySimplices ds5 = new DelaunaySimplices(s5);
		
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
		
		this.addNeihbors(ds4);
		ds2.addNeihbors(ds4);
		ds3.addNeihbors(ds4);
		ds5.addNeihbors(ds4);
		
		ds4.addNeihbors(this);
		ds4.addNeihbors(ds2);
		ds4.addNeihbors(ds3);
		ds4.addNeihbors(ds5);
		
	}
	
	/**
	 * Be carefull, you have to make a setSimplices after !
	 * @param current
	 */
	private DelaunaySimplices(Simplex current) {
		this.setCurrent(current);
	}
	
	public DelaunaySimplices(Collection<DelaunaySimplices> simplices, Simplex s) {
		this.simplices = simplices;
		this.current = s;
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
		
		ds1.addNeihbors(ds2);
		ds1.addNeihbors(ds3);
		ds1.addNeihbors(ds4);
		
		ds2.addNeihbors(ds1);
		ds2.addNeihbors(ds3);
		ds2.addNeihbors(ds4);
		
		ds3.addNeihbors(ds1);
		ds3.addNeihbors(ds2);
		ds3.addNeihbors(ds4);
		
		ds4.addNeihbors(ds1);
		ds4.addNeihbors(ds2);
		ds4.addNeihbors(ds3);
		
		/*
		 * We are adding the external neighbors of the new simplices. 
		 * Has to be optimized by improving the neighbors structure. 
		 */
		for(DelaunaySimplices ds : dsim.getNeighbors()) {
			if(ds.getCurrent().hasAsVertex(current.getA()) &&
			   ds.getCurrent().hasAsVertex(current.getB()) &&
			   ds.getCurrent().hasAsVertex(current.getC())) {
				
				ds1.addNeihbors(ds);
				
			} else if(ds.getCurrent().hasAsVertex(current.getA()) &&
			          ds.getCurrent().hasAsVertex(current.getB()) &&
					  ds.getCurrent().hasAsVertex(current.getD())) {
						
				ds2.addNeihbors(ds);
						
			} else if(ds.getCurrent().hasAsVertex(current.getA()) &&
			          ds.getCurrent().hasAsVertex(current.getC()) &&
					  ds.getCurrent().hasAsVertex(current.getD())) {
						
				ds3.addNeihbors(ds);
						
			}  else if(ds.getCurrent().hasAsVertex(current.getB()) &&
			           ds.getCurrent().hasAsVertex(current.getC()) &&
					   ds.getCurrent().hasAsVertex(current.getD())) {
						
				ds4.addNeihbors(ds);
						
			}
		}
		
		//TODO Check the Delaunay criteria
		
	}
	
	private void addNeihbors(DelaunaySimplices neighbor) {
		neighbors.add(neighbor);
	}
	
	public DelaunaySimplices getContainingSimplex(Point point) {
		for(DelaunaySimplices s : simplices) {
			if(s.getCurrent().contains(point)) {
				return s;
			}
		}
		return null;
	}
	
	public Collection<DelaunaySimplices> getNeighbors() {
		return neighbors;
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
	
}
