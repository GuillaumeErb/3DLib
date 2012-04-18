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

		return ds1;
		//TODO Check the Delaunay criteria
		
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

}
