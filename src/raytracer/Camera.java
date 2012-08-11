package raytracer;

import common.Point;
import common.Vect3;

public class Camera {
	
	private Point position;
	
	private Vect3 direction;
	
	private Vect3 normal;
	
	private double viewPlaneDistance;
	
	private double viewPlaneWidth;
	
	private double viewPlaneHeight;
	
	private int xResolution;
	
	private int yResolution;

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

	public Vect3 getDirection(int x, int y) {
		Vect3 dir = direction.times(viewPlaneDistance);
		Vect3 nor = normal.times(viewPlaneHeight/2 - y*(viewPlaneHeight/yResolution));
		Vect3 lat = direction.cross(normal).times(viewPlaneWidth/2 - x*(viewPlaneWidth/xResolution));
		return dir.plus(nor).plus(lat).normalize();
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
