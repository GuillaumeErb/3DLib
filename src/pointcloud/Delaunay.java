package pointcloud;

import io.OBJBuilder;
import io.OBJObject;
import io.OBJReader;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.Plane;
import common.Point;
import common.Simplex;
import common.Sphere;
import common.Triangle;
import common.Vect3;

public class Delaunay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "/home/guillaume/programming/3DLib/cube";
		OBJReader objReader = new OBJReader(name);
		objReader.read();
		Collection<Point> points = objReader.getPoints();
//		PointCloud cloud = new PointCloud(points);
//		DelaunayNaive ds = cloud.toSimplices();
		
		Set<Triangle> afl = new HashSet<Triangle>();
		Set<Simplex> simplices = deWall(new HashSet<Point>(points),afl);
		
//		System.out.println(ds.checkDelaunayCriteria());
		OBJObject objObject = getOBJObject(name,1,simplices);
//		OBJObject sphere = ds.getCurrent().circumSphere().toMesh(10, 10).getOBJObject("sphere", objObject.iPoints);
//		OBJObject objObject = cloud.toMesh().getOBJObject(name);
		OBJBuilder objBuilder = new OBJBuilder(name);

		objBuilder.addObject(objObject);
		objBuilder.save();
	}

	public static Set<Simplex> deWall(Set<Point> p, Set<Triangle> afl) {
		return Delaunay.deWall(p, afl, 0);
	}
	
	private static Set<Simplex> deWall(Set<Point> p, Set<Triangle> afl, int axis) {
		
		System.out.println("deWall");
		
		Triangle f0;
		Set<Triangle> aflAlpha = new HashSet<Triangle>();
		Set<Triangle> afl1 = new HashSet<Triangle>();
		Set<Triangle> afl2 = new HashSet<Triangle>();
		Simplex t;
		Set<Simplex> sigma = new HashSet<Simplex>();
		Plane alpha = new Plane();
		
		Set<Point> p1 = new HashSet<Point>();
		Set<Point> p2 = new HashSet<Point>();
		pointSetPartition(p, alpha, p1, p2, (axis+1)%3);
		
		/* Simplex Wall construction */
		if(afl.isEmpty()) {
			t = makeFirstSimplex(p, p1, p2, alpha);
			afl.addAll(t.getFaces());
			sigma.add(t);
		}
		
		for(Triangle f : afl) {
			if(alpha.intersects(f)) {
				aflAlpha.add(f);
			}
			if(p1.containsAll(f.getPoints())) {
				afl1.add(f);
			}
			if(p2.containsAll(f.getPoints())) {
				afl2.add(f);
			}
		}
		
		while(!aflAlpha.isEmpty()) {
			f0 = extract(aflAlpha);
			System.out.println("f0: " + f0);
			t = makeSimplex(f0, p);
			System.out.println("t: " + t);
			if(t != null) {
				sigma.add(t);
				for(Triangle f1 : t.getFaces()) {
					if(!f1.equals(f0)) {
						if(alpha.intersects(f1)) {
							update(f1, aflAlpha);
						}
						if(p1.containsAll(f1.getPoints())) {
							update(f1, afl1);
						}
						if(p2.containsAll(f1.getPoints())) {
							update(f1, afl2);
						}
					}
				}
			}
			System.out.println(aflAlpha.size());
			System.out.println(aflAlpha);
		}
		
		System.out.println(afl1.size() + " " + afl2.size());
		
		/*Recursive Triangulation*/
		if(!afl1.isEmpty()) {
			sigma.addAll(deWall(p1, afl1));
		}
		if(!afl2.isEmpty()) {
			sigma.addAll(deWall(p2, afl2));
		}
		
		return sigma;
	}

	
	private static void pointSetPartition(Set<Point> p, Plane alpha,
			Set<Point> p1, Set<Point> p2, int axis) {
		
		Vect3 v = new Vect3(0,0,0);
		for(Point po : p) {
			v.plus(po.toVect3());
		}
		Point center = new Point(v.dividedBy(p.size()));
		
		switch(axis) {
		case 0:
			//alpha.setFirstVector(new Vect3(1,0,0));
			//alpha.setSecondVector(new Vect3(0,1,0));
			alpha.setNormal(new Vect3(0,0,1));
		case 1:
			//alpha.setFirstVector(new Vect3(1,0,0));
			//alpha.setSecondVector(new Vect3(0,0,1));
		case 2:
			//alpha.setFirstVector(new Vect3(0,1,0));
			//alpha.setSecondVector(new Vect3(0,0,1));
		}
		
		alpha.setPoint(center);
		 
		for(Point po : p) {
			if(alpha.getNormal().scalar(po.toVect3().minus(alpha.getPoint().toVect3())) > 0) {
				p1.add(po);
			} else {
				p2.add(po);
			}
		}
		
	}

	private static Simplex makeFirstSimplex(Set<Point> p, Set<Point> p1, Set<Point> p2, Plane alpha) {
		
		System.out.println("makeFirstSimplex");
		
		if(p.size()<=0 || p1.size()<=0 || p2.size()<=0) {
			return null;
		} else {
			
			Point pa = null;
			double d = Double.MAX_VALUE;
			for(Point point : p) {
				double tmp = alpha.distance(point); 
				if(tmp<d) {
					pa = point;
					d = tmp;
				}
			}
			
			Point pb = null;
			d = Double.MAX_VALUE;
			
			if(p1.contains(pa)) {
				for(Point point : p2) {
					double tmp = point.toVect3().minus(pa.toVect3()).norm();
					if(tmp<d) {
						pb = point;
						d = tmp;
					}
				}
			} else {
				for(Point point : p1) {
					double tmp = point.toVect3().minus(pa.toVect3()).norm();
					if(tmp<d) {
						pb = point;
						d = tmp;
					}
				}
			}
			
			
			Point pc = null;
			d = Double.MAX_VALUE;
			for(Point point : p) {
				if(!point.equals(pa) && !point.equals(pb)) {
					double tmp = new Triangle(pa,pb,point).getCircumRadius();
					if(tmp<d) {
						pc = point;
						d = tmp;
					}
				}
			}
					
			Point pd = null;
			d = Double.MAX_VALUE;
			for(Point point : p) {
				if(!point.equals(pa) && !point.equals(pb) && !point.equals(pc)) {
				double tmp = new Simplex(pa,pb,pc,point).getCircumRadius();
					if(tmp<d) {
						pd = point;
						d = tmp;
					}
				}
			}
			
			return new Simplex(pa,pb,pc,pd);
		}
	}

	private static Triangle extract(Set<Triangle> aflAlpha) {
		
		System.out.println("extract");
		
		if(aflAlpha.size() >= 0) {
			return (Triangle) aflAlpha.toArray()[0];
		} else {
			return null;
		}
	}

	/**
	 * Delaunay Distance
	 * @param f triangle representing a face of a simplex
	 * @param p a point in the tree dimentional space
	 * @return the delaunay distance between f and p
	 */
	public static double dd(Triangle f, Point p) {
		Simplex simplex = new Simplex(f,p);
		Sphere sphere = simplex.circumSphere();
		Vect3 triangleNormal = f.getA().toVect3().minus(f.getB().toVect3())
					.cross(f.getC().toVect3().minus(f.getB().toVect3()));
		
		double sign = p.toVect3().minus(f.getB().toVect3()).scalar(triangleNormal);
		
		Vect3 normalP = triangleNormal.times(sign);
		
		if(normalP.scalar(sphere.getCenter().toVect3().minus(f.getB().toVect3())) >= 0) {
			return sphere.getRadius();
		} else {
			return -sphere.getRadius();
		}
		
	}
	
	private static Simplex makeSimplex(Triangle f, Set<Point> p) {
		
		System.out.println("makeSimplex");
		
		// We want to minimize the delaunay distance betwen a point and this triangle
		if(p.size() >= 0) {
			Point point = null;
			double distance = Double.MAX_VALUE;
			for(Point pp : p) {
				if(!f.hasAsVertex(pp)) {
					double tmpDistance = dd(f,pp);
					if(tmpDistance <= distance/* && tmpDistance > 0*/) {
						distance = tmpDistance;
						point = pp;
					}
				}
			}
			assert !f.planeContains(point);
			return new Simplex(f,point);
		}
		return null;
	}

	private static void update(Triangle f, Set<Triangle> aflAlpha) {
		
		System.out.println("update");
		
		if(aflAlpha.contains(f)) {
			aflAlpha.remove(f);
		} else {
			aflAlpha.add(f);
		}
	}
	
	
	public static OBJObject getOBJObject(String name, int iInit, Collection<Simplex> simplices) {
		OBJObject obj = new OBJObject(name);
		HashMap<Point, Integer> pointsToInt = new HashMap<Point, Integer>();
		
		Set<Point> points = new HashSet<Point>();
		
		for(Simplex simplex : simplices) {
			for(Point p : simplex.getPoints()) {
				points.add(p);
			}
		}
		
		int i = iInit;
		for(Point point : points) {
			pointsToInt.put(point, i);
			obj.addVertex(point.getX(), point.getY(), point.getZ());
			i++;
		}
		
		int[] face = {0,0,0};
		for(Simplex simplex : simplices) {
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getB());
			face[2] = pointsToInt.get(simplex.getC());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getB());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
			face[0] = pointsToInt.get(simplex.getA());
			face[1] = pointsToInt.get(simplex.getC());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
	
			face[0] = pointsToInt.get(simplex.getB());
			face[1] = pointsToInt.get(simplex.getC());
			face[2] = pointsToInt.get(simplex.getD());
			obj.addFace(face);
		}
		
		obj.iPoints = i;
		return obj;
	}
	
}
