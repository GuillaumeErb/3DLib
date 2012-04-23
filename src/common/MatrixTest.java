package common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

	private Matrix matrix;
	
	@Before
	public void setUp() throws Exception {
		this.matrix = new Matrix(4);
	}

	@Test
	public void testSet() {
//		System.out.println(matrix);
		matrix.set(2, 3, 5);
//		System.out.println(matrix);
	}

	@Test
	public void testGet() {
		matrix.set(2, 3, 5);
		assertTrue(matrix.get(2,3) == 5);
	}

	@Test
	public void testCoMatrix() {
		int c = 1;
		for(int i=1; i<=matrix.getDim(); i++) {
			for(int j=1; j<=matrix.getDim(); j++) {
				matrix.set(i, j, c++);
			}
		}
		
//		System.out.println(matrix);
//		System.out.println(matrix.coMatrix(1, 1));
//		System.out.println(matrix.coMatrix(1, 4));
//		System.out.println(matrix.coMatrix(3, 2));
		
		Matrix co = matrix.coMatrix(3, 2);
		assertTrue(co.getDim() == 3);
		assertTrue(co.get(1,1) == 1);
		assertTrue(co.get(3,3) == 16);
		assertTrue(co.get(1,3) == 4);
		
	}

	@Test
	public void testDet() {
		assertTrue(matrix.det() == 0);
		
		matrix.set(1, 1, 42);
		assertTrue(matrix.det() == 0);
		
		for(int i=1; i<=matrix.getDim(); i++) {
			matrix.set(i, i, i);
		}
		assertTrue(matrix.det() == 1*2*3*4);
		
		matrix.set(1, 3, -2);
		matrix.set(2, 1, 5);
		matrix.set(1, 3, -2);
		matrix.set(3, 4, 7);
		matrix.set(4, 1, 1);
		matrix.set(4, 2, -8);
		matrix.set(4, 3, 4);
		System.out.println(matrix);
		assertTrue(matrix.det() == -620);
		
	}

}
