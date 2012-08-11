package common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class SimplexTest {

	Simplex simplex;
	
	@Before
	public void setUp() throws Exception {
		Point a = new Point(-1,-1,0);
		Point b = new Point(-1,1,0);
		Point c = new Point(1,0,0);
		Point d = new Point(0,0,1);
		
		this.simplex = new Simplex(a,b,c,d);
	}

	@Test
	public void testCircumSphere() {
		Sphere s = simplex.circumSphere();
		double ra = (s.getCenter().toVect3().minus(simplex.getA().toVect3())).norm();
		double rb = (s.getCenter().toVect3().minus(simplex.getB().toVect3())).norm();
		double rc = (s.getCenter().toVect3().minus(simplex.getC().toVect3())).norm();
		double rd = (s.getCenter().toVect3().minus(simplex.getD().toVect3())).norm();
		
		System.out.println(ra);
		System.out.println(rb);
		System.out.println(rc);
		System.out.println(rd);
		System.out.println(s.getRadius());
		
		assertTrue(ra == rb);
		assertTrue(rb == rc);
		assertTrue(rc == rd);
		assertTrue(rd == s.getRadius());
		
		/***********************************/
		System.out.println("************************");
		
		Point a1 = new Point(-1,-5,0);
		Point b1 = new Point(5,1,42);
		Point c1 = new Point(1,-6,0);
		Point d1 = new Point(2,0,1);
		
		Simplex simplex1 = new Simplex(a1,b1,c1,d1);
		
		Sphere s1 = simplex1.circumSphere();
		double ra1 = (s1.getCenter().toVect3().minus(simplex1.getA().toVect3())).norm();
		double rb1 = (s1.getCenter().toVect3().minus(simplex1.getB().toVect3())).norm();
		double rc1 = (s1.getCenter().toVect3().minus(simplex1.getC().toVect3())).norm();
		double rd1 = (s1.getCenter().toVect3().minus(simplex1.getD().toVect3())).norm();
		
				
		System.out.println(ra1);
		System.out.println(rb1);
		System.out.println(rc1);
		System.out.println(rd1);
		System.out.println(s1.getRadius());
		
		assertTrue(ra1 == rb1);
		assertTrue(rb1 == rc1);
		assertTrue(rc1 == rd1);
		assertTrue(rd1 == s1.getRadius());
		
		
		/***********************************/
		System.out.println("************************");
		
		Point a2 = new Point(0,0,5);
		Point b2 = new Point(1,0,4);
		Point c2 = new Point(5,0,1);
		Point d2 = new Point(1,0,1);
		
		Simplex simplex2 = new Simplex(a2,b2,c2,d2);
		
		Sphere s2 = simplex2.circumSphere();
		double ra2 = (s2.getCenter().toVect3().minus(simplex2.getA().toVect3())).norm();
		double rb2 = (s2.getCenter().toVect3().minus(simplex2.getB().toVect3())).norm();
		double rc2 = (s2.getCenter().toVect3().minus(simplex2.getC().toVect3())).norm();
		double rd2 = (s2.getCenter().toVect3().minus(simplex2.getD().toVect3())).norm();
		
				
		System.out.println(ra2);
		System.out.println(rb2);
		System.out.println(rc2);
		System.out.println(rd2);
		System.out.println(s2.getRadius());
		
		assertTrue(s2.getRadius() == Double.MAX_VALUE);
		
		/***********************************/
		System.out.println("************************");
		
		Point a3 = new Point(-1,0,1);
		Point b3 = new Point(0,1,345);
		Point c3 = new Point(0,0,619);
		Point d3 = new Point(0,0,78);
		
		Simplex simplex3 = new Simplex(a3,b3,c3,d3);
		
		Sphere s3 = simplex3.circumSphere();
		double ra3 = (s3.getCenter().toVect3().minus(simplex3.getA().toVect3())).norm();
		double rb3 = (s3.getCenter().toVect3().minus(simplex3.getB().toVect3())).norm();
		double rc3 = (s3.getCenter().toVect3().minus(simplex3.getC().toVect3())).norm();
		double rd3 = (s3.getCenter().toVect3().minus(simplex3.getD().toVect3())).norm();
		
				
		System.out.println(ra3);
		System.out.println(rb3);
		System.out.println(rc3);
		System.out.println(rd3);
		System.out.println(s3.getRadius());
		
		assertTrue(ra3 == rb3);
		assertTrue(rb3 == rc3);
		assertTrue(rc3 == rd3);
		assertTrue(rd3 == s3.getRadius());
		
	}

	@Test
	public void testGetCommonPoints() {
		Point a1 = new Point(-1,-1,0);
		Point b1 = new Point(-1,1,0);
		Point c1 = new Point(1,0,0);
		Point d1 = new Point(0,0,-2);
		Simplex simplex1 = new Simplex(a1,b1,c1,d1);
		Collection<Point> cp1 = new ArrayList<Point>();
		cp1.add(a1);
		cp1.add(b1);
		cp1.add(c1);
		assertTrue(simplex.getCommonPoints(simplex1).containsAll(cp1));

		
		Point a2 = new Point(-1,-1,0);
		Point b2 = new Point(-1,1,0);
		Point c2 = new Point(5,0,0);
		Point d2 = new Point(0,0,-2);
		Simplex simplex2 = new Simplex(a2,b2,c2,d2);
		Collection<Point> cp2 = new ArrayList<Point>();
		cp2.add(a2);
		cp2.add(b2);
		assertTrue(simplex.getCommonPoints(simplex2).containsAll(cp2));
				
		Point a3 = new Point(-1,6,0);
		Point b3 = new Point(-1,1,0);
		Point c3 = new Point(5,0,0);
		Point d3 = new Point(0,0,-2);
		Simplex simplex3 = new Simplex(a3,b3,c3,d3);
		Collection<Point> cp3 = new ArrayList<Point>();
		cp3.add(b3);
		assertTrue(simplex.getCommonPoints(simplex3).containsAll(cp3));
		
		Point a4 = new Point(-1,6,0);
		Point b4 = new Point(-1,1,0);
		Point c4 = new Point(5,0,0);
		Point d4 = new Point(0,0,-2);
		Simplex simplex4 = new Simplex(a4,b4,c4,d4);
		Collection<Point> cp4 = new ArrayList<Point>();
		assertTrue(simplex.getCommonPoints(simplex4).containsAll(cp4));
	}

	@Test
	public void testContains() {
		assertTrue(this.simplex.contains(new Point(0,0,0)));
		assertTrue(this.simplex.contains(new Point(-0.4,0.5,0.1)));
		assertTrue(this.simplex.contains(new Point(-1,-1,0)));
		assertTrue(this.simplex.contains(new Point(-1,1,0)));
		assertTrue(this.simplex.contains(new Point(1,0,0)));
		assertTrue(this.simplex.contains(new Point(0,0,1)));
	}

	@Test
	public void testSameSide() {
		assertTrue(Simplex.sameSide(new Point(0,0,5),
									new Point(0,1,3),
									new Point(-1,0,0),
									new Point(1, 0, 0),
									new Point(0, 1, 0)));
		assertFalse(Simplex.sameSide(new Point(0,0,5),
				 					 new Point(0,1,-3),
				 					 new Point(-1,0,0),
				 					 new Point(1, 0, 0),
				 					 new Point(0, 1, 0)));
	}

	@Test
	public void testHasAsVertex() {
		Point a = new Point(-1,-1,0);
		Point b = new Point(-1,1,0);
		Point c = new Point(1,0,0);
		Point d = new Point(0,0,1);
		Point e = new Point(0,0,-1);
		assertTrue(this.simplex.hasAsVertex(a));
		assertTrue(this.simplex.hasAsVertex(b));
		assertTrue(this.simplex.hasAsVertex(c));
		assertTrue(this.simplex.hasAsVertex(d));
		assertFalse(this.simplex.hasAsVertex(e));
		
	}

	@Test
	public void testHasAsVertices() {
		Point a = new Point(-1,-1,0);
		Point b = new Point(-1,1,0);
		Point c = new Point(1,0,0);
		Point e = new Point(0,0,-1);
		assertTrue(this.simplex.hasAsVertices(a,b,c));
		assertFalse(this.simplex.hasAsVertices(a,c,e));
		
	}

	@Test
	public void testExtractTriangle() {
		Point a = new Point(-1,-1,0);
		Point b = new Point(-1,1,0);
		Point c = new Point(1,0,0);
		Point d = new Point(0,0,1);
		
		assertTrue(this.simplex.extractTriangle(a).equals(new Triangle(b,c,d)));
	}

}
