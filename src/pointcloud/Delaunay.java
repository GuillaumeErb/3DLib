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
		String name = "/home/guillaume/programming/3DLib/cube";
		OBJReader objReader = new OBJReader(name);
		objReader.read();
		Collection<Point> points = objReader.getPoints();
		PointCloud cloud = new PointCloud(points);
		DelaunaySimplices ds = cloud.toSimplices();
		System.out.println(ds.checkDelaunayCriteria());
		OBJObject objObject = ds.getOBJObject(name,1);
//		OBJObject sphere = ds.getCurrent().circumSphere().toMesh(10, 10).getOBJObject("sphere", objObject.iPoints);
//		OBJObject objObject = cloud.toMesh().getOBJObject(name);
		OBJBuilder objBuilder = new OBJBuilder(name);
		objBuilder.addObject(objObject);
//		objBuilder.addObject(sphere);
		objBuilder.save();
	}

}
