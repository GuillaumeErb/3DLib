package common;

public class Vect {

	private double[] vector;

	private int dim;

	public Vect(int n) {
		this.vector = new double[n];
		this.dim = n;
		for (int i = 1; i <= dim; i++) {
			set(i, 0);
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vect(double x, double y) {
		this.vector = new double[2];
		set(1, x);
		set(2, y);
		this.dim = 2;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vect(double x, double y, double z) {
		this.vector = new double[3];
		set(1, x);
		set(2, y);
		set(3, z);
		this.dim = 3;
	}

	public void set(int i, double v) {
		vector[i - 1] = v;
	}

	public double get(int i) {
		return vector[i - 1];
	}

	public double norm() {
		double s = 0;
		for (double d : this.vector) {
			s += d * d;
		}
		return Math.sqrt(s);
	}

	public double scalar(Vect v) {
		double res = 0;
		for (int i = 1; i <= dim; i++) {
			res += this.get(i) * v.get(i);
		}
		return res;
	}

	public Vect normalize() {
		double norm = this.norm();
		if (norm != 0) {
			Vect v = new Vect(this.dim);
			for (int i = 1; i <= dim; i++) {
				v.set(i, this.get(i) / norm);
			}
			return v;
		} else {
			return new Vect(this.dim);
		}
	}

	public Vect cross(Vect v) {
		if (this.dim == 3) {
			return new Vect(get(2) * v.get(3) - get(3) * v.get(2), get(3)
					* v.get(1) - get(1) * v.get(3), get(1) * v.get(2) - get(2)
					* v.get(1));
		} else
			return null;
	}

	public Vect plus(Vect v) {
		Vect res = new Vect(this.dim);
		for (int i = 1; i <= dim; i++) {
			res.set(i, this.get(i) + v.get(i));
		}
		return res;
	}

	public Vect minus(Vect v) {
		Vect res = new Vect(this.dim);
		for (int i = 1; i <= dim; i++) {
			res.set(i, this.get(i) - v.get(i));
		}
		return res;
	}

	public Vect times(Vect v) {
		Vect res = new Vect(this.dim);
		for (int i = 1; i <= dim; i++) {
			res.set(i, this.get(i) * v.get(i));
		}
		return res;
	}

	public Vect times(double d) {
		Vect res = new Vect(this.dim);
		for (int i = 1; i <= dim; i++) {
			res.set(i, this.get(i) * d);
		}
		return res;
	}

	public Vect dividedBy(double d) {
		Vect res = new Vect(this.dim);
		for (int i = 1; i <= dim; i++) {
			res.set(i, this.get(i) / d);
		}
		return res;
	}

	public int getDim() {
		return this.dim;
	}

	public double[] toArray() {
		return this.vector;
	}
}
