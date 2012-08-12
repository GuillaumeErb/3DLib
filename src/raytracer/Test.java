package raytracer;

import io.ImageWriter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lighting.DirectionalLight;
import lighting.Light;
import lighting.PointLight;
import materials.FlatMaterial;
import materials.HallMaterial;

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
		Camera camera = new Camera(new Point(-50, 0, 0), 
								   new Vect3(1, 0, 0), 
								   new Vect3(0, 0, 1), 
								   2, 0.7, 0.7, 500, 500);
		objects.add(new Sphere(new Point(0,-2,0), 4, 
				               new HallMaterial(new Color(0.8, 0.4, 0.2),
				            		   			0.1, 
				            		   			2, 
				            		   			0.8, 
				            		   			100,
				            		   			new Color(0, 0, 1),
				            		   			0.7,
				            		   			1,
				            		   			1,
				            		   			1,
				            		   			100)));
		objects.add(new Sphere(new Point(-2,5,0), 2, 
	               			   new HallMaterial(new Color(0, 0, 1),
	               					   			0.1, 
	               					   			2, 
	               					   			0.7, 
	               					   			200,
				            		   			new Color(1, 0, 0),
				            		   			0.5,
				            		   			1,
				            		   			1,
				            		   			1,
				            		   			100)));
		lights.add(new DirectionalLight(new Color(1,1,1), 1,
										(new Vect3(0.5,-1,0)).normalize()));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
