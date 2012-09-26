package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import materials.FlatMaterial;
import materials.Material;
import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;

import common.Point;
import common.Vect3;

public class Quadric extends Primitive {

	//ax²+by²+cz²+2dxy+2eyz+2fxz+2gx+2hy+2jz+k=0
	private double a;
	private double b;
	private double c;
	private double d;
	private double e;
	private double f;
	private double g;
	private double h;
	private double j;
	private double k;
	
	public Quadric(double a,
				   double b,
				   double c,
				   double d,
				   double e,
				   double f,
				   double g,
				   double h,
				   double j,
				   double k,
				   Material material) {
		
		super(material);
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.j = j;
		this.k = k;
	}
	
	public Quadric(double a,
			       double b,
			       double c,
			       double d,
			       double e,
			       double f,
			       double g,
			       double h,
			       double j,
			       double k) {
		
		this(a, b, c, d, e, f, g, h, j, k, new FlatMaterial(new Color(0.5, 0.5, 0.5)));
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		List<Intersection> intersections = this.getIntersections(ray);
		if(intersections == null || intersections.size() <= 0) {
			return null;
		} else {
			Collections.sort(intersections);			
			return intersections.get(0);
		}
		
	}

	@Override
	public List	<Intersection> getIntersections(Ray ray) {

		Vect3 rayDirection = ray.getDirection(); 
		Vect3 rayOrigin = ray.getOrigin().toVect3();
		
		Vect3 V1 = rayDirection.times(rayDirection);
		Vect3 V2 = (new Vect3(rayDirection.getX()*rayDirection.getY(),
							  rayDirection.getY()*rayDirection.getZ(),
							  rayDirection.getX()*rayDirection.getZ())).times(2);
		Vect3 V3 = rayDirection.times(rayOrigin).times(2);
		Vect3 V4 = (new Vect3 (rayDirection.getX()*rayOrigin.getY() + rayOrigin.getX()*rayDirection.getY(),
							   rayDirection.getY()*rayOrigin.getZ() + rayOrigin.getY()*rayDirection.getZ(),
							   rayDirection.getX()*rayOrigin.getZ() + rayOrigin.getX()*rayDirection.getZ())).times(2);
		Vect3 V5 = rayDirection.times(2);
		Vect3 V6 = rayOrigin.times(rayOrigin);
		Vect3 V7 = (new Vect3(rayOrigin.getX()*rayOrigin.getY(),
							  rayOrigin.getY()*rayOrigin.getZ(),
							  rayOrigin.getX()*rayOrigin.getZ())).times(2);
		Vect3 V8 = rayOrigin.times(2);
		
		Vect3 Q1 = new Vect3(a, b, c);
		Vect3 Q2 = new Vect3(d, e, f);
		Vect3 Q3 = new Vect3(g, h, j);
			
		double A = Q1.scalar(V1) + Q2.scalar(V2);
		double B = Q1.scalar(V3) + Q2.scalar(V4) + Q3.scalar(V5);
		double C = Q1.scalar(V6) + Q2.scalar(V7) + Q3.scalar(V8) + k;
		
		double delta = B*B-4*A*C;
		
		double t;
		Point point;

		if(delta < 0) {
			return null;
		} else if(delta == 0) {
			ArrayList<Intersection> result = new ArrayList<Intersection>();
			t = -B/2*A;
			point = new Point(rayOrigin.plus(rayDirection.times(t)));
			result.add(new Intersection(this, this.getNormal(point), t));
			return result;
		} else {
			ArrayList<Intersection> result = new ArrayList<Intersection>();
			t = (-B - Math.sqrt(delta))/2*A;
			point = new Point(rayOrigin.plus(rayDirection.times(t)));
			result.add(new Intersection(this, this.getNormal(point), t));
			t = (-B + Math.sqrt(delta))/2*A;
			point = new Point(rayOrigin.plus(rayDirection.times(t)));
			result.add(new Intersection(this, this.getNormal(point), t));
			return result;
		}
	}

	
	private Vect3 getNormal(Point point) {
		return (new Vect3(a*point.getX() + d*point.getY() + f*point.getZ() + g,
						  d*point.getX() + b*point.getY() + e*point.getZ() + h,
						  f*point.getX() + e*point.getY() + c*point.getZ() + j)).normalize();
	}
	
}
