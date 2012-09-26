package raytracer;

import java.io.Serializable;
import java.util.ArrayList;

import common.Point;
import common.Vect3;

public class Camera implements Serializable {
	
	private Point position;
	
	private Vect3 direction;
	
	private Vect3 normal;
	
	private double viewPlaneDistance;
	
	private double viewPlaneWidth;
	
	private double viewPlaneHeight;
	
	private int xResolution;
	
	private int yResolution;
	
	
	private final static double translateCoef = 1;
	
	private final static double rotateCoef = 1./10;
	
	private final static double rotateCenterCoef = 1./10;
	

	public Camera(Point position, Vect3 direction, Vect3 normal,
			double viewPlaneDistance, double viewPlaneWidth,
			double viewPlaneHeight, int xResolution, int yResolution) {
		super();
		this.position = position;
		this.direction = direction;
		this.normal = normal;
		this.viewPlaneDistance = viewPlaneDistance;
		this.viewPlaneWidth = viewPlaneWidth;
		this.viewPlaneHeight = viewPlaneHeight;
		this.xResolution = xResolution;
		this.yResolution = yResolution;
	}

	public Vect3 getDirection(double x, double y) {
		Vect3 dir = direction.times(viewPlaneDistance);
		Vect3 nor = normal.times(viewPlaneHeight/2 - y*(viewPlaneHeight/yResolution));
		Vect3 lat = direction.cross(normal).times(viewPlaneWidth/2 - x*(viewPlaneWidth/xResolution));
		return dir.plus(nor).plus(lat).normalize();
	}
	
	public ArrayList<Vect3> getFourDirections(double x, double y, int subdivision) {
		ArrayList<Vect3> directions = new ArrayList<Vect3>();

		double s = 1./(subdivision+1);
		
		directions.add(this.getDirection(x+s, y+s));
		directions.add(this.getDirection(x+s, y-s));
		directions.add(this.getDirection(x-s, y+s));
		directions.add(this.getDirection(x-s, y-s));
		
	    return directions;
	}
	
	
	public void translateFront() {
		this.position = new Point(position.toVect3().plus(direction.times(translateCoef)));
	}
	
	public void translateBack() {
		this.position = new Point(position.toVect3().plus(direction.times(-translateCoef)));
	}
	
	public void translateLeft() {
		this.position = new Point(position.toVect3().plus(direction.cross(normal).times(translateCoef)));
	}
	
	public void translateRight() {
		this.position = new Point(position.toVect3().plus(direction.cross(normal).times(-translateCoef)));
	}
	
	public void translateUp() {
		this.position = new Point(position.toVect3().plus(normal.times(translateCoef)));
	}
	
	public void translateDown() {
		this.position = new Point(position.toVect3().plus(normal.times(-translateCoef)));
	}
	
	
	public void rotateUp() {
		Vect3 axis = direction.cross(normal);
		this.direction = this.direction.rotate(rotateCoef, axis);
		this.normal = this.normal.rotate(rotateCoef, axis);
	}
	
	public void rotateDown() {
		Vect3 axis = direction.cross(normal);
		this.direction = this.direction.rotate(-rotateCoef, axis);
		this.normal = this.normal.rotate(-rotateCoef, axis);
	}
	
	public void rotateLeft() {
		this.direction = this.direction.rotate(-rotateCoef, normal);
	}
	
	public void rotateRight() {
		this.direction = this.direction.rotate(rotateCoef, normal);
	}
	
	public void rotateClock() {
		this.normal = this.normal.rotate(rotateCoef, direction);
	}
	
	public void rotateTrigo() {
		this.normal = this.normal.rotate(-rotateCoef, direction);
	}
	
	
	
	public void rotateCenterUp() {
		Vect3 axis = direction.cross(normal);
		this.position = new Point(this.position.toVect3().rotate(-rotateCenterCoef, axis));
		this.direction = this.position.toVect3().times(-1).normalize();
		this.normal = this.normal.rotate(-rotateCenterCoef, axis);
	}
	
	public void rotateCenterDown() {
		Vect3 axis = direction.cross(normal);
		this.position = new Point(this.position.toVect3().rotate(rotateCenterCoef, axis));
		this.direction = this.position.toVect3().times(-1).normalize();
		this.normal = this.normal.rotate(rotateCenterCoef, axis);
	}
	
	public void rotateCenterLeft() {
		this.position = new Point(this.position.toVect3().rotate(rotateCenterCoef, normal));
		this.direction = this.position.toVect3().times(-1).normalize();
		this.normal = this.normal.rotate(rotateCenterCoef, normal);
	}
	
	public void rotateCenterRight() {
		this.position = new Point(this.position.toVect3().rotate(-rotateCenterCoef, normal));
		this.direction = this.position.toVect3().times(-1).normalize();
		this.normal = this.normal.rotate(-rotateCenterCoef, normal);
	}
	
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Vect3 getDirection() {
		return direction;
	}

	public void setDirection(Vect3 direction) {
		this.direction = direction;
	}

	public Vect3 getNormal() {
		return normal;
	}

	public void setNormal(Vect3 normal) {
		this.normal = normal;
	}

	public double getViewPlaneDistance() {
		return viewPlaneDistance;
	}

	public void setViewPlaneDistance(double viewPlaneDistance) {
		this.viewPlaneDistance = viewPlaneDistance;
	}

	public double getViewPlaneWidth() {
		return viewPlaneWidth;
	}

	public void setViewPlaneWidth(double viewPlaneWidth) {
		this.viewPlaneWidth = viewPlaneWidth;
	}

	public double getViewPlaneHeight() {
		return viewPlaneHeight;
	}

	public void setViewPlaneHeight(double viewPlaneHeight) {
		this.viewPlaneHeight = viewPlaneHeight;
	}

	public int getXResolution() {
		return xResolution;
	}

	public void setXResolution(int xResolution) {
		this.xResolution = xResolution;
	}

	public int getYResolution() {
		return yResolution;
	}

	public void setYResolution(int yResolution) {
		this.yResolution = yResolution;
	}


}

