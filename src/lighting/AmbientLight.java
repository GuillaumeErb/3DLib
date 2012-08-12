package lighting;

import raytracer.Color;

public class AmbientLight {

	private Color color;
	private double intensity;
	
	public AmbientLight(Color color, double intensity) {
		this.color = color;
		this.intensity = intensity;
	}

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
