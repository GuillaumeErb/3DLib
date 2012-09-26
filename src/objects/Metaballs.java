package objects;

import java.util.HashMap;
import java.util.List;

import common.Point;
import common.Vect3;

import materials.Material;
import raytracer.Intersection;
import raytracer.Ray;

public class Metaballs extends Primitive {

	private HashMap<Point, Double> metaballs;
	private double threshold;
	
	public Metaballs(Material material) {
		super(material);
		this.metaballs = new HashMap<Point, Double>();
		this.threshold = 1;
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Intersection> getIntersections(Ray ray) {
		Vect3 paramDir = ray.getDirection().dividedBy(ray.getDirection().getZ());
		double rr = paramDir.scalar(paramDir);
		Vect3 miv;
		double mi2 = 0;
		double zmi = 0;
		for(Point p : metaballs.keySet()) {
			zmi = p.toVect3().scalar(paramDir)/rr;
			miv = p.toVect3().minus(paramDir.times(zmi));
			mi2 = miv.scalar(miv);
		}
		return null;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public HashMap<Point, Double> getMetaballs() {
		return metaballs;
	}

	public void setMetaballs(HashMap<Point, Double> metaballs) {
		this.metaballs = metaballs;
	}
	
	public void addMetaball(Point point, double intensity) {
		this.metaballs.put(point, intensity);
	}

}
