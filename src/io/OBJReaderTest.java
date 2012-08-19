package io;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import objects.Mesh;
import objects.Triangle;

import org.junit.Test;

import common.Point;

public class OBJReaderTest {

	@Test
	public void testExtractMesh() {
		OBJReader reader = new OBJReader("/home/guillaume/programming/3DLib/blender_cube");
		Mesh mesh = reader.extractMesh();
		
		Point p1 = new Point(0, 0, 0);
		Point p2 = new Point(0, 0, 1);
		Point p3 = new Point(0, 1, 0);
		Point p4 = new Point(0, 1, 1);
		Point p5 = new Point(1, 0, 0);
		Point p6 = new Point(1, 0, 1);
		Point p7 = new Point(1, 1, 0);
		Point p8 = new Point(1, 1, 1);
		
		Set<Point> points = mesh.getPoints();
		
		assertTrue(points.contains(p1));
		assertTrue(points.contains(p2));
		assertTrue(points.contains(p3));
		assertTrue(points.contains(p4));
		assertTrue(points.contains(p5));
		assertTrue(points.contains(p6));
		assertTrue(points.contains(p7));
		assertTrue(points.contains(p8));
		
		Triangle t1 = new Triangle(p1, p5, p7);
		Triangle t2 = new Triangle(p1, p3, p7);
		Triangle t3 = new Triangle(p1, p4, p3);
		Triangle t4 = new Triangle(p1, p2, p4);
		Triangle t5 = new Triangle(p3, p8, p7);
		Triangle t6 = new Triangle(p3, p4, p8);
		Triangle t7 = new Triangle(p5, p7, p8);
		Triangle t8 = new Triangle(p5, p8, p6);
		Triangle t9 = new Triangle(p1, p5, p6);
		Triangle t10 = new Triangle(p1, p6, p2);
		Triangle t11 = new Triangle(p2, p6, p8);
		Triangle t12 = new Triangle(p2, p8, p4);
		
		Set<Triangle> triangles = mesh.getTriangles();
		
		assertTrue(triangles.contains(t1));
		assertTrue(triangles.contains(t2));
		assertTrue(triangles.contains(t3));
		assertTrue(triangles.contains(t4));
		assertTrue(triangles.contains(t5));
		assertTrue(triangles.contains(t6));
		assertTrue(triangles.contains(t7));
		assertTrue(triangles.contains(t8));
		assertTrue(triangles.contains(t9));
		assertTrue(triangles.contains(t10));
		assertTrue(triangles.contains(t11));
		assertTrue(triangles.contains(t12));
		
		Set<Triangle> surr = mesh.getSurroundingTriangles(p1);
		
		assertTrue(surr.contains(t1));
		assertTrue(surr.contains(t2));
		assertTrue(surr.contains(t3));
		assertTrue(surr.contains(t4));
		assertTrue(surr.contains(t9));
		assertTrue(surr.contains(t10));
		
		surr = mesh.getSurroundingTriangles(p2);
		
		assertTrue(surr.contains(t4));
		assertTrue(surr.contains(t10));
		assertTrue(surr.contains(t11));
		assertTrue(surr.contains(t12));
		
		surr = mesh.getSurroundingTriangles(p3);
		
		assertTrue(surr.contains(t2));
		assertTrue(surr.contains(t3));
		assertTrue(surr.contains(t5));
		assertTrue(surr.contains(t6));
		
		surr = mesh.getSurroundingTriangles(p4);
		
		assertTrue(surr.contains(t3));
		assertTrue(surr.contains(t4));
		assertTrue(surr.contains(t6));
		assertTrue(surr.contains(t12));
		
		surr = mesh.getSurroundingTriangles(p5);
		
		assertTrue(surr.contains(t1));
		assertTrue(surr.contains(t7));
		assertTrue(surr.contains(t8));
		assertTrue(surr.contains(t9));
		
		surr = mesh.getSurroundingTriangles(p6);
		
		assertTrue(surr.contains(t8));
		assertTrue(surr.contains(t9));
		assertTrue(surr.contains(t10));
		assertTrue(surr.contains(t11));
		
		surr = mesh.getSurroundingTriangles(p7);
		
		assertTrue(surr.contains(t1));
		assertTrue(surr.contains(t2));
		assertTrue(surr.contains(t5));
		assertTrue(surr.contains(t7));
		
		surr = mesh.getSurroundingTriangles(p8);
		
		assertTrue(surr.contains(t5));
		assertTrue(surr.contains(t6));
		assertTrue(surr.contains(t7));
		assertTrue(surr.contains(t8));
		assertTrue(surr.contains(t11));
		assertTrue(surr.contains(t12));
	}

}
