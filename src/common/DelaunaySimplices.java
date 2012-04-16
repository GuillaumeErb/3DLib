package common;

import java.util.ArrayList;
import java.util.Collection;

//TODO This class definitely needs illustrations !!!

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
	 * The neighbor at the opposite of the a vertex of the current simplex
	 */
	DelaunaySimplices na;
	DelaunaySimplices nb;
	DelaunaySimplices nc;
	DelaunaySimplices nd;
	

	public DelaunaySimplices(Point ddd, Point ddu, Point dud, Point duu,
							 Point udd, Point udu, Point uud, Point uuu) {
		
		Simplex s1 = new Simplex(ddd, udd, ddu, dud);
		Simplex s2 = new Simplex(uud, dud, uuu, udd);
		Simplex s3 = new Simplex(duu, uuu, dud, ddu);
		Simplex s4 = new Simplex(udu, ddu, udd, uuu);
		Simplex s5 = new Simplex(uuu, ddu, udd, dud);
		
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
		
		
		this.setANeighbor(ds5);
		this.setBNeighbor(null);
		this.setCNeighbor(null);
		this.setDNeighbor(null);
		
		ds2.setANeighbor(ds5);
		ds2.setBNeighbor(null);
		ds2.setCNeighbor(null);
		ds2.setDNeighbor(null);
		
		ds3.setANeighbor(ds5);
		ds3.setBNeighbor(null);
		ds3.setCNeighbor(null);
		ds3.setDNeighbor(null);
		
		ds4.setANeighbor(ds5);
		ds4.setBNeighbor(null);
		ds4.setCNeighbor(null);
		ds4.setDNeighbor(null);
		
		ds5.setANeighbor(this);
		ds5.setBNeighbor(ds2);
		ds5.setCNeighbor(ds3);
		ds5.setDNeighbor(ds4);
		
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
		
		ds1.setANeighbor(ds4);
		ds1.setBNeighbor(ds3);
		ds1.setCNeighbor(ds2);
		ds1.setDNeighbor(ds1);
		
		ds2.setANeighbor(ds4);
		ds2.setBNeighbor(ds3);
		ds2.setCNeighbor(ds1);
		ds2.setDNeighbor(ds2);
		
		ds3.setANeighbor(ds4);
		ds3.setBNeighbor(ds2);
		ds3.setCNeighbor(ds1);
		ds3.setDNeighbor(ds3);
		
		ds4.setANeighbor(ds3);
		ds4.setBNeighbor(ds2);
		ds4.setCNeighbor(ds1);
		ds4.setDNeighbor(ds4);
		
		
		//TODO Check the Delaunay criteria
		
	}
	
	
	public DelaunaySimplices getContainingSimplex(Point point) {
		for(DelaunaySimplices s : simplices) {
			if(s.getCurrent().contains(point)) {
				return s;
			}
		}
		return null;
	}
	
	
	
	public DelaunaySimplices getANeighbor() {
		return this.na;
	}

	public void setANeighbor(DelaunaySimplices na) {
		this.na = na;
	}
	
	
	public DelaunaySimplices getBNeighbor() {
		return this.nb;
	}
	
	public void setBNeighbor(DelaunaySimplices nb) {
		this.nb = nb;
	}
	
	
	public DelaunaySimplices getCNeighbor() {
		return this.nc;
	}
	
	public void setCNeighbor(DelaunaySimplices nc) {
		this.nc = nc;
	}
	
	
	public DelaunaySimplices getDNeighbor() {
		return this.nd;
	}
	
	public void setDNeighbor(DelaunaySimplices nd) {
		this.nd = nd;
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
