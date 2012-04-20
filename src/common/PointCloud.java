package common;

import io.OBJReader;

import java.util.Collection;

public class PointCloud {

	private Collection<Point> pointCloud;
	
	public PointCloud(String fileName) {
		OBJReader reader = new OBJReader(fileName);
		reader.read();
		this.pointCloud = reader.getPoints();
	}
	
	/**
	 * @param pointCloud
	 */
	public PointCloud(Collection<Point> pointCloud) {
		super();
		this.pointCloud = pointCloud;
	}
	
	public DelaunaySimplices toSimplices() {
		int nbPoint = pointCloud.size();
		if(nbPoint <= 3) {
			return null;
		} else {
			Simplex superSimplex = this.getSurroundingSimplex();
			DelaunaySimplices result = new DelaunaySimplices(superSimplex);
			result.triangulate(pointCloud);
			return result;
		}
	}

	public Mesh toMesh() {
		int nbPoint = pointCloud.size();
		if(nbPoint <= 3) { //TODO handle the 3 case
			return null;
		} else {
			Simplex superSimplex = this.getSurroundingSimplex();
			DelaunaySimplices result = new DelaunaySimplices(superSimplex);
			return result.transformToMesh(pointCloud);
		}
	}
	
	public Simplex getSurroundingSimplex() {
		
		double xu = ((Point) pointCloud.toArray()[0]).getX();
		double yu = ((Point) pointCloud.toArray()[0]).getY();
		double zu = ((Point) pointCloud.toArray()[0]).getZ();
		
		double xd = ((Point) pointCloud.toArray()[0]).getX();
		double yd = ((Point) pointCloud.toArray()[0]).getY();
		double zd = ((Point) pointCloud.toArray()[0]).getZ();
				
		for(Point p : pointCloud) {
			if(p.getX() < xd) {
				xd = p.getX();
			}
			if(p.getX() > xu) {
				xu = p.getX();
			}
			if(p.getY() < yd) {
				yd = p.getY();
			}
			if(p.getY() > yu) {
				yu = p.getY();
			}
			if(p.getZ() < zd) {
				zd = p.getZ();
			}
			if(p.getZ() > zu) {
				zu = p.getZ();
			}
		}
		
		// We transform rectangles into a square
//		double l = Math.max(Math.max(xu-xd, yu-yd), zu-zd);
//		xu = xd + l;
//		yu = yd + l;
//		zu = zd + l;
		
		
		Vect3 ddd = new Vect3(xd,yd,zd);
		Vect3 ddu = new Vect3(xd,yd,zu);
		Vect3 dud = new Vect3(xd,yu,zd);
		Vect3 duu = new Vect3(xd,yu,zu);		
		Vect3 udd = new Vect3(xu,yd,zd);
		Vect3 udu = new Vect3(xu,yd,zu);
		Vect3 uud = new Vect3(xu,yu,zd);
		Vect3 uuu = new Vect3(xu,yu,zu);
		
		Vect3 g = (ddd.plus(ddu).plus(dud).plus(duu).plus(udd)
					  .plus(udu).plus(uud).plus(uuu)).dividedBy(8);
		
		double radius = Math.max(ddd.minus(g).norm(),
						Math.max(ddu.minus(g).norm(),
						Math.max(dud.minus(g).norm(), 
						Math.max(duu.minus(g).norm(),
						Math.max(udd.minus(g).norm(),
						Math.max(udu.minus(g).norm(), 
						Math.max(uud.minus(g).norm(), uuu.minus(g).norm())))))));
		
		Vect3 x = new Vect3(1,0,0);
		Vect3 y = new Vect3(0,1,0);
		Vect3 z = new Vect3(0,0,1);
		
		Vect3 o = g.plus(z.times(radius)); // the top of the sphere
		Vect3 m = o.minus(y.times(radius));
		Vect3 a1 = m.minus(x.times(radius*Math.sqrt(3.)));
		Vect3 b1 = m.plus(x.times(radius*Math.sqrt(3.)));
		Vect3 c1 = m.plus(y.times(3*radius));
		
		Vect3 d = o.plus(z.times(3*radius));
		Vect3 a = d.plus(a1.minus(d).times(2));
		Vect3 b = d.plus(b1.minus(d).times(2));
		Vect3 c = d.plus(c1.minus(d).times(2));
		
		return new Simplex(new Point(a), new Point(b), new Point(c), new Point(d));
	}
}
