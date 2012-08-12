package lighting;

import common.Point;
import common.Vect3;

import raytracer.Color;
import raytracer.Scene;


public abstract class Light {

	private Color color;
	
	private double intensity;

	public Light(Color color, double intensity) {
		super();
		this.color = color;
		this.intensity = intensity;
	}

	public abstract boolean lightsPoint(Point point, Scene scene);
	
	public abstract Vect3 fromPointToLight(Point point);
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}
	
	
	
}
