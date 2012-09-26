package raytracer;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import lighting.DirectionalLight;
import lighting.Light;
import materials.HallMaterial;
import objects.HalfSpace;
import objects.Object3D;
import objects.ObjectCSG;
import objects.OperationCSG;
import objects.Quadric;
import objects.Sphere;

import common.Point;
import common.Vect3;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Set<Object3D> objects = new HashSet<Object3D>();
		Set<Light> lights = new HashSet<Light>();
		Camera camera = new Camera(new Point(-100, 0, 0), 
								   new Vect3(1, 0, 0), 
								   new Vect3(0, 0, 1), 
								   2, 0.4, 0.4, 600, 600);
		
//		objects.add(new Sphere(new Point(0,-2,0), 4, 
//				               new HallMaterial(new Color(0.8, 0.4, 0.2),
//				            		   			0.1, 
//				            		   			2, 
//				            		   			0.8, 
//				            		   			100,
//				            		   			new Color(0, 0, 1),
//				            		   			0.7,
//				            		   			1,
//				            		   			1,
//				            		   			1,
//				            		   			100)));
//		objects.add(new Sphere(new Point(-2,5,0), 2, 
//	               			   new HallMaterial(new Color(0, 0, 1),
//	               					   			0.1, 
//	               					   			2, 
//	               					   			0.7, 
//	               					   			200,
//				            		   			new Color(1, 0, 0),
//				            		   			0.5,
//				            		   			1,
//				            		   			1,
//				            		   			1,
//				            		   			100)));
//
		
		
		
		 
//		objects.add(new Sphere(new Point(0,0,0), 10, 
//	               new HallMaterial(new Color(0.8, 0.4, 0.2),
//	            		   			0.6,
//	            		   			0.2,
//	            		   			0.1, 
//	            		   			2, 
//	            		   			0.8, 
//	            		   			100,
//	            		   			new Color(0, 0, 0),
//	            		   			0,
//	            		   			0,
//	            		   			0,
//	            		   			0,
//	            		   			1)));
//
//		
//		
		Quadric q = new Quadric(1, 15, -20, 0, 0, 0, 0, 0, 0, 20, 
				new HallMaterial(new Color(1, 0, 0),
						   			0.2,
						   			0.6,
						   			0.4, 
						   			4, 
						   			0.8, 
						   			100,
						   			new Color(0, 0, 0),
						   			1,
						   			1,
						   			0,
						   			0,
						   			1));
//		objects.add(q);
		
		
		Sphere s1 = new Sphere(new Point(0,0,0), 7, 
							   new HallMaterial(new Color(0.1, 0.8, 0.2),
									   			0.2,
									   			0.6,
									   			0.4, 
									   			2, 
									   			0.8, 
									   			100,
									   			new Color(0, 0, 0),
									   			1,
									   			1,
									   			0,
									   			0,
									   			1, 
									   			0));
		
//		objects.add(new ObjectCSG(OperationCSG.INTERSECTION, 
//				new ObjectCSG(q), new ObjectCSG(s1)));
		
		
		Sphere s2 = new Sphere(new Point(3,-3,0), 10, 
				   new HallMaterial(new Color(0.9, 0.9, 1),
						   
						   			0.2,
						   			0.6,
						   			0, 
						   			4, 
						   			0.8, 
						   			100,
						   			new Color(0, 0, 0),
						   			1,
						   			1,
						   			0,
						   			0,
						   			1));
		
//		objects.add(s1);
		
		HalfSpace plane = new HalfSpace(
				new Point(0, 0, 0), 
				new Vect3(1, 1, 1), 
				new HallMaterial(new Color(0.8, 0.8, 0.8),
						   		 0.2,
						   		 0.6,
						   		 0.4, 
						   		 2, 
						   		 0.8, 
						   		 100,
						   		 new Color(0, 0, 0),
						   		 1,
						   		 1,
						   		 0,
						   		 0,
						   		 1)); 
//		
		ObjectCSG spheres = new ObjectCSG(OperationCSG.INTERSECTION, new ObjectCSG(s2), new ObjectCSG(s1));
//		
		objects.add(spheres);
		
//		objects.add(new Box(new HallMaterial(new Color(0.9, 0.4, 0.2),
//						   		 0.2,
//						   		 0.6,
//						   		 0.4, 
//						   		 2, 
//						   		 0.8, 
//						   		 100,
//						   		 new Color(0, 0, 0),
//						   		 1,
//						   		 1,
//						   		 0,
//						   		 0,
//						   		 1)));
		
//		Cylinder cylinder = new Cylinder(new Point(0, 0, 0), 
//				new Vect3(0, 0, 1), 5, new HallMaterial(new Color(0.9, 0.4, 0.2),
//				   		 0.2,
//				   		 0.6,
//				   		 0.1, 
//				   		 2, 
//				   		 0.8, 
//				   		 100,
//				   		 new Color(0, 0, 0),
//				   		 1,
//				   		 1,
//				   		 0,
//				   		 0,
//				   		 1)); 
//		
//		objects.add(cylinder);
		
		Sphere s3 = new Sphere(new Point(0,0,0), 8, 
				   new HallMaterial(new Color(0.5, 0.4, 0.7),
						   			0.2,
						   			0.6,
						   			0.4, 
						   			2, 
						   			0.8, 
						   			100,
						   			new Color(0, 0, 0),
						   			1,
						   			1,
						   			0,
						   			0,
						   			1,
						   			0));
		
//		objects.add(s3);
		
//		ObjectCSG o1 = new ObjectCSG(OperationCSG.DIFFERENCE,
//				  new ObjectCSG(cylinder),
//				  new ObjectCSG(s3)); 
//		
//		objects.add(new ObjectCSG(OperationCSG.INTERSECTION, o1,
//					new ObjectCSG(plane)
//					));
		
		
		
//		objects.add(s2);
//		
//		objects.add(new Triangle(new Point(-10, 0, 10),
//				new Point (10, 10, 0), new Point( 10, -10, -10),
//        new HallMaterial(new Color(1, 0.8, 0.2),
//			0.2,
//			0,
//			0.2, 
//			0.01, 
//			0, 
//			1,
//			new Color(0, 0, 0),
//			1,
//			1,
//			0,
//			0,
//			1)));
//		
//		objects.add(new Sphere(new Point(-2,5,0), 2, 
//    		   	   new HallMaterial(new Color(0.2, 0.2, 1),
//    		   			   			0.3,
//    		   			   			1,
//    					   			0.1, 
//    					   			2, 
//    					   			0.7, 
//    					   			200,
//	            		   			new Color(0, 0, 0),
//	            		   			1,
//	            		   			1.005,
//	            		   			0,
//	            		   			0,
//	            		   			1)));
		
		
		
//		OBJReader reader = new OBJReader("d:\\programming\\3DLib\\sphere");
//		Mesh mesh = reader.extractMesh();
//		objects.add(mesh);
//		for(Triangle t : mesh.getTriangles()) {
////			objects.add(t);
//			t.setMaterial(new HallMaterial(new Color(0.9, 0.8, 0.2),
//							0,
//							0,
//							0.05, 
//							1, 
//							0.5, 
//							200, 
//							new Color(0, 0, 0),
//							1,
//							1,
//							0,
//							0,
//							1));
//		}

//		int l = 0;
//		for(Triangle t : mesh.getTriangles()) {
//			objects.add(new Triangle(t.getA(), t.getB(), t.getC()),
//					new HallMaterial(new Color(1, 0.8, 0.2),
//							0,
//							0,
//							0.2, 
//							1, 
//							0, 
//							0,
//							new Color(0, 0, 0),
//							1,
//							1,
//							0,
//							0,
//							1)));
//			l++;
//		}
		
//		lights.add(new DirectionalLight(new Color(1,1,1), 1,
//										(new Vect3(-0.02,-1,0.2)).normalize()));

		lights.add(new DirectionalLight(new Color(1,1,1), 1,
									  	(new Vect3(3,-1,3)).normalize()));
		Scene scene = new Scene(objects, lights, camera);
		
//		ArrayList<Point> points = new ArrayList<Point>();
//		points.add(new Point(0, 0, 0));
//		points.add(new Point(1, 0, 0));
//		points.add(new Point(0, 1, 0));
//		points.add(new Point(0, 0, 1));
//		points.add(new Point(1, 1, 1));
//		points.add(new Point(1, 0, 1));
//		
//		for(Sphere sphere : new PointCloud(points).toSpheres(0.05)) {
//			objects.add(sphere);
//		}

		Screen screen = new Screen(camera.getXResolution(), camera.getYResolution());
		Renderer renderer = new Renderer(scene, camera, screen);
		BufferedImage image = renderer.render(camera, scene); 
		screen.display(image);
		
		
//		Renderer.render(camera, scene);
	}

}
