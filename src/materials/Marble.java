package materials;

import lighting.AmbientLight;

import common.Point;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public class Marble extends HallMaterial {

	private Color color2;
	
	public Marble(
			Color color1, 
			Color color2, 
			double reflectivity, 
			double transparency,
			double ambientReflectanceCoefficient,
			double diffuseReflectanceCoefficient,
			double specularReflectanceCoefficient,
			double specularReflectanceHighlightCoefficient,
			Color transparencyColor, 
			double TransmissionIndexIn,
			double TransmissionIndexOut, 
			double diffuseTransmissiveCoefficient,
			double specularTransmissiveCoefficient,
			double specularTransmissiveHighlightCoefficient) {
		super(color1, 
			  reflectivity, 
			  transparency, 
			  ambientReflectanceCoefficient,
			  diffuseReflectanceCoefficient, specularReflectanceCoefficient,
			  specularReflectanceHighlightCoefficient, transparencyColor,
			  TransmissionIndexIn, TransmissionIndexOut,
			  diffuseTransmissiveCoefficient, specularTransmissiveCoefficient,
			  specularTransmissiveHighlightCoefficient);
		this.color2 = color2;
	}

	@Override
	protected Color getColor(Ray ray, Intersection intersection) {
		
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		Double t = 0.;
		for(int level=1; level<100; level++) {
		t += (1./level)*ImprovedNoise.noise(level*0.05*point.getX(), 
											level*0.05*point.getY(),
											level*0.05*point.getZ());
		}
		t = Math.abs(t);
		t = Math.min(t, 1);

		double noiseCoef = Math.cos(point.getY()+t);
		return color.times(1-noiseCoef).plus(color2.times(noiseCoef));

	}
	
}
