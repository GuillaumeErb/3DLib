package common;

/**
 * Class representing square double matrices of a given size.
 * 
 * @author Guillaume Erb
 * 
 */
public class Matrix {

	/**
	 * The matrix stored as an array
	 */
	private double[][] matrix;

	/**
	 * The dimension of the square matrix stored.
	 */
	private int dim;

	/**
	 * Create a matrix full of zeros
	 * 
	 * @param n
	 *            , dimension of the square matrix
	 */
	public Matrix(int n) {
		this.matrix = new double[n][n];
		for (int i = 1; i <= dim; i++) {
			for (int j = 1; j <= dim; j++) {
				set(i, j, 0);
			}
		}
		this.dim = n;
	}

	/**
	 * Set the value of an element of the matrix. M(i,j) = value.
	 * 
	 * @param i
	 *            number of the line
	 * @param j
	 *            number of the column
	 * @param value
	 *            the value to put in the matrix
	 */
	public void set(int i, int j, double value) {
		this.matrix[i - 1][j - 1] = value;
	}

	/**
	 * Give the value of an element of the matrix, M(i,j)
	 * 
	 * @param i
	 *            number of the line
	 * @param j
	 *            number of the column
	 * @return the element M(i,j)
	 */
	public double get(int i, int j) {
		return this.matrix[i - 1][j - 1];
	}

	/**
	 * Determine a co-matrix of the current matrix, i.e. the same matrix without
	 * a line and a column
	 * 
	 * @param i_
	 *            the number of the line to suppress
	 * @param j_
	 *            the number of the column to suppress
	 * @return the co-matrix
	 */
	public Matrix coMatrix(int i_, int j_) {
		Matrix result = new Matrix(dim - 1);
		for (int i = 1; i <= dim; i++) {
			for (int j = 1; j <= dim; j++) {
				if (i < i_) {
					if (j < j_) {
						result.set(i, j, this.get(i, j));
					} else if (j > j_) {
						result.set(i, j - 1, this.get(i, j));
					}
				} else if (i > i_) {
					if (j < j_) {
						result.set(i - 1, j, this.get(i, j));
					} else if (j > j_) {
						result.set(i - 1, j - 1, this.get(i, j));
					}
				}
			}
		}
		return result;
	}

	/**
	 * Function calculating recursively the determinant of the matrix using the
	 * column development technique.
	 * 
	 * @return the determinant of the matrix
	 */
	public double det() {
		double res = 0;
		if (dim > 1) {
			for (int i = 1; i <= dim; i++) {
				res += (coMatrix(i, 1).det()) * get(i, 1) * Math.pow(-1, i + 1);
			}
		} else {
			res = get(1, 1);
		}
		return res;
	}

	/**
	 * Multiplicate this matrix with another of the same size.
	 * 
	 * @param m
	 *            a matrix
	 * @return the product of this with the parameter m
	 */
	public Matrix times(Matrix m) {
		if (m.getDim() == dim) {
			Matrix res = new Matrix(dim);
			double tmp = 0;
			for (int i = 1; i <= dim; i++) {
				for (int j = 1; j <= dim; j++) {
					for (int k = 1; k <= dim; k++) {
						tmp += this.get(i, j) * m.get(j, i);
					}
					res.set(i, j, tmp);
				}
			}
			return res;
		} else
			return null;
	}

	/**
	 * Elevate the matrix at a certain power
	 * 
	 * @param n
	 *            the power of the matrix
	 * @return this at the power n
	 */
	public Matrix pow(int n) {
		Matrix res;
		if (n == 0) {
			res = new Matrix(dim);
			for (int i = 1; i <= dim; i++) {
				res.set(i, i, 1);
			}
			return res;
		} else {
			return this.times(this.pow(n - 1));
		}
	}

	/**
	 * Multiplication matrix/vector
	 * 
	 * @param v
	 *            the vector
	 * @return the vector resulting of the multiplication
	 */
	public Vect times(Vect v) {
		if (v.getDim() == dim) {
			Vect res = new Vect(dim);
			for (int i = 1; i <= dim; i++) {
				double tmp = 0;
				for (int j = 1; j <= dim; j++) {
					tmp += get(i, j) * v.get(j);
				}
				res.set(i, tmp);
				tmp = 0;
			}
			return res;
		} else
			return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= dim; i++) {
			builder.append("[");
			for (int j = 1; j <= dim; j++) {
				builder.append(this.get(i, j)).append(" ");
			}
			builder.append("]\n");
		}
		return builder.toString();
	}

	/**
	 * @return the dimension of the matrix
	 */
	public int getDim() {
		return this.dim;
	}
	
	/**
	 * The array representing the matrix.
	 * m[i][j] will return the element at the line i and at the column j
	 * 
	 * @return the array representing the matrix.
	 */
	public double[][] toArray() {
		return this.matrix;
	}
}
