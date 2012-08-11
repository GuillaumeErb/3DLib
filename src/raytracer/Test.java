package raytracer;

import io.ImageWriter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lighting.DirectionalLight;
import lighting.Light;
import lighting.PointLight;
import materials.FlatMaterial;

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
		Camera camera = new Camera(new Point(-6, 1, 0), 
								   new Vect3(1, 0, 0), 
								   new Vect3(0, 0, 1), 
								   2, 10, 10, 500, 500);
		objects.add(new Sphere(new Point(0,-2,0), 4, 
				               new FlatMaterial(new Color(1, 0, 0))));
		objects.add(new Sphere(new Point(-2,5,0), 2, 
	               new FlatMaterial(new Color(0, 0, 1))));
		lights.add(new DirectionalLight(new Color(0.5,1,0.5), 1, 
										new Vect3(0,-1,0)));
		Scene scene = new Scene(objects, lights, camera);
		
		
		ImageWriter iw = new ImageWriter(camera.getXResolution(), camera.getYResolution());
		
		for(int i=0; i<scene.getCamera().getXResolution(); i++) {
			for(int j=0; j<scene.getCamera().getYResolution(); j++) {
				Color color = scene.renderPixel(i, j);
				iw.setRGB(i, j, color);
//				System.out.print(color.getR());
			}
//			System.out.println();
		}
		try {
			iw.write("/home/guillaume/out.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
