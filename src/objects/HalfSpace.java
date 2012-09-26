package objects;

import java.util.ArrayList;
import java.util.List;

import materials.Material;

import common.Point;
import common.Vect3;

import raytracer.Intersection;
import raytracer.Ray;

public class HalfSpace extends Primitive {

	private Point point;
	private Vect3 normal;

	public HalfSpace(Point point, Vect3 normal, Material material) {
		super(material);
		this.point = point;
		this.normal = normal;
	}
	
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Vect3 getNormal() {
		return normal;
	}

	public void setNormal(Vect3 normal) {
		this.normal = normal;
	}

	
	@Override
	public Intersection getIntersection(Ray ray) {
		double nu = ray.getDirection().scalar(normal); 
		if(nu == 0) {
			return null;
		}
		
		return new Intersection(this, this.normal, 
				point.toVect3().
				minus(ray.getOrigin().toVect3()).
				scalar(normal)/nu); 
	}

	@Override
	public List<Intersection> getIntersections(Ray ray) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		intersections.add(this.getIntersection(ray));
		return intersections;
	}

}
