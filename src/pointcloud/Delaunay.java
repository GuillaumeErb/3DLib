package pointcloud;

import java.util.Collection;

import common.Point;

import io.OBJBuilder;
import io.OBJObject;
import io.OBJReader;

public class Delaunay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "/home/guillaume/programming/3DLib/diam";
//		if(args.length == 1) {
			OBJReader objReader = new OBJReader(name);
			objReader.read();
			Collection<Point> points = objReader.getPoints();
			PointCloud cloud = new PointCloud(points);
			OBJObject objObject = cloud.toSimplices().getOBJObject(name);
			//OBJObject objObject = cloud.toMesh().getOBJObject(name);
			OBJBuilder objBuilder = new OBJBuilder(name);
			objBuilder.addObject(objObject);
			objBuilder.save();
//		}

	}

}
