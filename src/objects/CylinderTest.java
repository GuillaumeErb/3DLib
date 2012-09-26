package objects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import materials.FlatMaterial;

import org.junit.Test;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;

import com.sun.xml.internal.bind.v2.runtime.output.C14nXmlOutput;

import common.Point;
import common.Vect3;

public class CylinderTest {

	@Test
	public void test() {
		Cylinder cylinder = new Cylinder(
				new Point(0, 0, 0), 
				new Vect3(0, 1, 0), 5,
				new FlatMaterial(new Color(1, 0.2, 0.2)));
		
		List<Intersection> inter = cylinder.getIntersections(
				new Ray(new Point(-10, 0, 0), 
						new Vect3(1, 0, 0)));
		
		ArrayList<Double> distances = new ArrayList<Double>();
		ArrayList<Vect3> normals = new ArrayList<Vect3>();
		for(Intersection i : inter) {
			distances.add(i.getDistance());
			normals.add(i.getNormal());
		}
		
		assertTrue(distances.contains(5.0));
		assertTrue(distances.contains(15.0));
//		assertTrue(normals.contains(new Vect3(1, 0, 0)));
//		assertTrue(normals.contains(new Vect3(-1, 0, 0)));
		
	}

}
