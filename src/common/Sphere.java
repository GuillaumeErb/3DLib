package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class Sphere {
	
	private Point center;
	private double radius;
	
	/**
	 * @param center
	 * @param radius
	 */
	public Sphere(Point center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}

	
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public Mesh toMesh(int segments, int rings) {
		Vect3 x = new Vect3(1, 0, 0);
		Vect3 y = new Vect3(0, 1, 0);
		Vect3 z = new Vect3(0, 0, 1);
		
		Vect3 CBot = this.center.toVect3().minus(z.times(radius));
		

		Vector<Point> previous = new Vector<Point>();
		Vector<Point> current  = new Vector<Point>();
		
		for(int ring = 1; ring<=rings; ring++) {
			Vect3 BO =  CBot.plus(CBot.times(ring/rings));
			for(int segment = 1; segment<=segments-2; segment++) {
				
				
				
			}
		}
		return null;
	}
	
	private ArrayList<Point> cut(Point p, int segments) {
		ArrayList<Point> res = new ArrayList<Point>();
		Vect3 x = new Vect3(1, 0, 0);
		Vect3 y = new Vect3(0, 1, 0);
		double h  = p.toVect3().minus(center.toVect3()).norm();
		double r = Math.sqrt(radius*radius + h*h);
		for(int i = 0; i<segments; i++) {
			res.add(new Point(p.getX()+Math.cos(2*Math.PI*i/segments)*r, p.getY()+Math.sin(2*Math.PI*i/segments)*r, p.getZ()));
		}
		
		return res;
	}
	
}
