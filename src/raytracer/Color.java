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
	
	public Color plus(Color color) {
		double r = Math.min(this.r + color.getR(), 1);
		double g = Math.min(this.g + color.getG(), 1);
		double b = Math.min(this.b + color.getB(), 1);
		return new Color(r, g, b);
	}
	
	public Color times(Color color) {
		double r = Math.min(this.r * color.getR(), 1);
		double g = Math.min(this.g * color.getG(), 1);
		double b = Math.min(this.b * color.getB(), 1);
		return new Color(r, g, b);
	}
	
	public Color times(double coef) {
		double r = Math.min(this.r * coef, 1);
		double g = Math.min(this.g * coef, 1);
		double b = Math.min(this.b * coef, 1);
		return new Color(r, g, b);
	}
	
	public double getR() {
		return r;
	}

	public int getR255() {
		return (int)Math.floor(r*255);
	}
	
	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public int getG255() {
		return (int)Math.floor(g*255);
	}
	
	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public int getB255() {
		return (int)Math.floor(b*255);
	}
	
	public void setB(double b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
	
}
