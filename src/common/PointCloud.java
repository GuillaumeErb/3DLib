package common;

import java.util.Collection;

public class PointCloud {

	private Collection<Point> pointCloud;
	
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
			return null;
		}
	}

	
	public DelaunaySimplices initializeDelaunaySimplices() {
		
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
		
		// We are expanding the box a little
		xd--;yd--;zd--;xu++;yu++;zu++;
		
		Point ddd = new Point(xd,yd,zd);
		Point ddu = new Point(xd,yd,zu);
		Point dud = new Point(xd,yu,zd);
		Point duu = new Point(xd,yu,zu);		
		Point udd = new Point(xu,yd,zd);
		Point udu = new Point(xu,yd,zu);
		Point uud = new Point(xu,yu,zd);
		Point uuu = new Point(xu,yu,zu);
		
		return new DelaunaySimplices(ddd,ddu,dud,duu,udd,udu,uud,uuu);

	}
	
}
