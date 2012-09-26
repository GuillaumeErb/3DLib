package objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import materials.FlatMaterial;

import org.junit.Test;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;

import common.Point;
import common.Vect3;

public class ObjectCSGTest {

	@Test
	public void testGetIntersections() {
		ObjectCSG o = new ObjectCSG(
				new Sphere(
						new Point(0, 0, 0),
						1,
						new FlatMaterial(
								new Color(1, 1, 1))));
		java.util.List<Intersection> intersections =
				o.getIntersections(
						new Ray(
								new Point(-2, 0, 0), 
								new Vect3(1, 0, 0)));

		List<Double> distances = new ArrayList<Double>();
		for(Intersection i : intersections) {
			distances.add(i.getDistance());
		}
		
		assertTrue(distances.contains(1.0));
		assertTrue(distances.contains(3.0));
		assertEquals(distances.size(), 2);
		
	}
	
	@Test
	public void testGetIntersections2() {
		ObjectCSG o1 = new ObjectCSG(
				new Sphere(
						new Point(0, 0, 0),
						2,
						new FlatMaterial(
								new Color(1, 1, 1))));
		
		ObjectCSG o2 = new ObjectCSG(
				new Sphere(
						new Point(3, 0, 0),
						2,
						new FlatMaterial(
								new Color(1, 1, 1))));
		
		ObjectCSG o = new ObjectCSG(OperationCSG.UNION, o1, o2);
		
		java.util.List<Intersection> intersections =
				o.getIntersections(
						new Ray(
								new Point(-10, 0, 0), 
								new Vect3(1, 0, 0)));

		List<Double> distances = new ArrayList<Double>();
		for(Intersection i : intersections) {
			distances.add(i.getDistance());
		}
		
//		System.out.println(distances);
		
		assertTrue(distances.contains(8.0));
		assertTrue(distances.contains(15.0));
		assertEquals(distances.size(), 2);
	}
	
	@Test
	public void testGetIntersections3() {
		ObjectCSG o1 = new ObjectCSG(
				new Sphere(
						new Point(0, 0, 0),
						2,
						new FlatMaterial(
								new Color(1, 1, 1))));
		
		ObjectCSG o2 = new ObjectCSG(
				new Sphere(
						new Point(5, 0, 0),
						2,
						new FlatMaterial(
								new Color(1, 1, 1))));
		
		ObjectCSG o = new ObjectCSG(OperationCSG.UNION, o1, o2);
		
		java.util.List<Intersection> intersections =
				o.getIntersections(
						new Ray(
								new Point(-10, 0, 0), 
								new Vect3(1, 0, 0)));

		List<Double> distances = new ArrayList<Double>();
		for(Intersection i : intersections) {
			distances.add(i.getDistance());
		}
		
//		System.out.println(distances);
		
		assertTrue(distances.contains(8.0));
		assertTrue(distances.contains(12.0));
		assertTrue(distances.contains(13.0));
		assertTrue(distances.contains(17.0));
		assertEquals(distances.size(), 4);
		
	}

}
