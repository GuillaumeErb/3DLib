package raytracer;

import io.ImageWriter;
import io.OBJReader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lighting.DirectionalLight;
import lighting.Light;
import lighting.PointLight;
import materials.FlatMaterial;
import materials.HallMaterial;

import common.Mesh;
import common.Plane;
import common.Point;
import common.Vect3;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<SimpleObject> objects = new HashSet<SimpleObject>();
		Set<Light> lights = new HashSet<Light>();
		Camera camera = new Camera(new Point(-100, 0, 0), 
								   new Vect3(1, 0, 0), 
								   new Vect3(0, 0, 1), 
								   2, 0.8, 0.8, 1000, 1000);
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
		
		
		
		 
//		objects.add(new Sphere(new Point(0,-2,0), 4, 
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
//		objects.add(new Sphere(new Point(8,-5,0), 5, 
//	               new HallMaterial(new Color(0.1, 0.8, 0.2),
//	            		   			0.2,
//	            		   			0.6,
//	            		   			0.4, 
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
		
		
		
		OBJReader reader = new OBJReader("d:\\programming\\3DLib\\sphere");
		Mesh mesh = reader.extractMesh();
		int l = 0;
		for(common.Triangle t : mesh.getTriangles()) {
			objects.add(new Triangle(t.getA(), t.getB(), t.getC(),
					new HallMaterial(new Color(1, 0.8, 0.2),
							0,
							0,
							0.2, 
							1, 
							0, 
							0,
							new Color(0, 0, 0),
							0,
							0,
							0,
							0,
							1)));
			l++;
		}
		
		lights.add(new DirectionalLight(new Color(1,1,1), 1,
										(new Vect3(0,-1,0)).normalize()));

		lights.add(new DirectionalLight(new Color(1,1,1), 0.3,
									  	(new Vect3(5,-1,3)).normalize()));
		Scene scene = new Scene(objects, lights, camera);
		
		
		ImageWriter iw = new ImageWriter(camera.getXResolution(), camera.getYResolution());
		
		for(int i=0; i<scene.getCamera().getXResolution(); i++) {
			for(int j=0; j<scene.getCamera().getYResolution(); j++) {
				Color color = scene.renderPixel(i, j);
				iw.setRGB(i, j, color);
			}
		}
		try {
			iw.write("d:\\out.png");
			System.out.println("OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
