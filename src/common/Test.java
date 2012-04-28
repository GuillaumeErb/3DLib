package common;

import io.OBJBuilder;
import io.OBJObject;

public class Test {
	public static void main(String[] argv) {
//		Sphere s = new Sphere(new Point(0,0,0), 10);
//		OBJBuilder ob = new OBJBuilder("/home/guillaume/programming/3DLib/sphere");
//		OBJObject oo = s.toMesh(10, 10).getOBJObject("/home/guillaume/programming/3DLib/sphere", 1);
//		ob.addObject(oo);
//		System.out.println(ob.getBuilder());
//		ob.save();
		double nan = Math.asin(42);
		double d = Double.MAX_VALUE;
		System.out.println(nan < 2345);
		System.out.println(nan > 6543);
		System.out.println(nan==nan);
	}
}
