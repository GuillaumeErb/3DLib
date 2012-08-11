package raytracer;

public class Color {

	private double r;
	
	private double g;
	
	private double b;
	
	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	
	
	public double getR() {
		return r;
	}

	public int getR255() {
		return (int)Math.floor(r)*255;
	}
	
	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public int getG255() {
		return (int)Math.floor(g)*255;
	}
	
	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public int getB255() {
		return (int)Math.floor(b)*255;
	}
	
	public void setB(double b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
	
	
}
