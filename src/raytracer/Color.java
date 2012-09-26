package raytracer;

import java.io.Serializable;


public class Color implements Serializable {

	private double r;
	
	private double g;
	
	private double b;
	
	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Color(double grey) {
		this.r = grey;
		this.g = grey;
		this.b = grey;
	}

	public Color plus(Color color) {
		double r = this.r + color.getR();
		double g = this.g + color.getG();
		double b = this.b + color.getB();
		return new Color(r, g, b);
	}
	
	public Color minus(Color color) {
		double r = this.r - color.getR();
		double g = this.g - color.getG();
		double b = this.b - color.getB();
		return new Color(r, g, b);
	}
	
	public Color times(Color color) {
		double r = this.r * color.getR();
		double g = this.g * color.getG();
		double b = this.b * color.getB();
		return new Color(r, g, b);
	}
	
	public Color times(double coef) {
		double r = this.r * coef;
		double g = this.g * coef;
		double b = this.b * coef;
		return new Color(r, g, b);
	}
	
	public Color dividedBy(double coef) {
		double r = this.r / coef;
		double g = this.g / coef;
		double b = this.b / coef;
		return new Color(r, g, b);
	}
	
	public double sum() {
		return r + g + b;
	}
	
	public double getR() {
		return r;
	}

	public int getR255() {
		return (int)Math.floor((1-Math.exp(-r))*255);
//		return Math.min((int)Math.floor(r*255), 255);
	}
	
	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public int getG255() {
		return (int)Math.floor((1-Math.exp(-g))*255);
//		return Math.min((int)Math.floor(g*255), 255);
	}
	
	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public int getB255() {
		return (int)Math.floor((1-Math.exp(-b))*255);
//		return Math.min((int)Math.floor(b*255), 255);
	}
	
	public void setB(double b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
	public int getRGB() {
		int rgb = this.getR255();
		rgb = (rgb << 8) + getG255();
		rgb = (rgb << 8) + getB255();
		
		return rgb;
	}
	
	
}
