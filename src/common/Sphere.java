package common;

import java.util.ArrayList;


public class Sphere {
	
	protected Point center;
	protected double radius;
	
	/**
	 * @param center
	 * @param radius
	 */
	public Sphere(Point center, double radius) {
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
		
		Mesh mesh = new Mesh();
		
		Vect3 z = new Vect3(0, 0, 1);
		
		Vect3 bottom = this.center.toVect3().minus(z.times(radius));
		Vect3 top = this.center.toVect3().plus(z.times(radius));
		

		ArrayList<Point> previous;
		ArrayList<Point> current;
		
		Vect3 step = z.times(2*radius/rings);
				
		Vect3 center = bottom.plus(step);

		previous = this.cut(new Point(center), segments);
		for(int i = 1; i<=segments; i++) {
			mesh.addTriangle(new Triangle(new Point(bottom), 
					                      previous.get(i%segments), 
					                      previous.get((i+1)%segments)));
		}
		
		for(int k = 0; k<rings-2; k++) {
			center = center.plus(step);
			current = this.cut(new Point(center), segments);
			for(int i = 1; i<=segments; i++) {
				mesh.addTriangle(new Triangle(current.get(i%segments), 
	                      					  previous.get(i%segments), 
	                      					  previous.get((i+1)%segments)));
				mesh.addTriangle(new Triangle(previous.get((i+1)%segments), 
    					  					  current.get(i%segments), 
    					  					  current.get((i+1)%segments)));
			}
			previous = current;
		}
		
		for(int i = 1; i<=segments; i++) {
			mesh.addTriangle(new Triangle(new Point(top), 
					                      previous.get(i%segments), 
					                      previous.get((i+1)%segments)));
		}
		
		return mesh;
	}
	
	private ArrayList<Point> cut(Point p, int segments) {
		ArrayList<Point> res = new ArrayList<Point>();
		double h  = p.toVect3().minus(center.toVect3()).norm();
		double r = Math.sqrt(radius*radius - h*h);
		for(int i = 0; i<segments; i++) {
			Point point = new Point(p.getX()+Math.cos(2*Math.PI*i/segments)*r,
					  				p.getY()+Math.sin(2*Math.PI*i/segments)*r,
					  				p.getZ());
			res.add(point);
		}
		
		return res;
	}
	
	
	
	
}
