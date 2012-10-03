package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import materials.FlatMaterial;

import org.junit.Test;

import common.Point;
import common.Vect3;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;

public class SphereTest {

	@Test
	public void testGetIntersection() {
		Sphere sphere = new Sphere(new Point(0,0,0), 5, new FlatMaterial(new Color(0,0,0)));
		Intersection i = sphere.getIntersection(new Ray(new Point(-100, 0, 0), new Vect3(1, 0, 0)));
		assertTrue(i.getDistance() == 95);
		
		i = sphere.getIntersection(new Ray(new Point(-1, 0, 0), new Vect3(1, 0, 0)));
		System.out.println(i.getDistance());
		assertTrue(i.getDistance() == 6);
		
		
	}

	@Test
	public void testGetIntersections() {
		Sphere sphere = new Sphere(new Point(0,0,0), 5, new FlatMaterial(new Color(0,0,0)));
		List<Intersection> li = sphere.getIntersections(new Ray(new Point(-1, 0, 0), 
																new Vect3(1, 0, 0)));
		
		List<Double> ld= new ArrayList<Double>();
		for(Intersection i : li) {
			ld.add(i.getDistance());
		}
		assertTrue(ld.contains(-4.));
		assertTrue(ld.contains(6.));
		
		
	}

}
