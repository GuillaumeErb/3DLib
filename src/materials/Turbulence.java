package materials;

import lighting.AmbientLight;

import common.Point;

import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

public class Turbulence extends HallMaterial {

	protected Color color2;
	private Color color3;
	private double level1;
	private double level2;
	private double level3;
	
	public Turbulence(
			Color color, 
			Color color2,
			Color color3,
			double level1,
			double level2,
			double level3,
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
		super(color, 
			  reflectivity, 
			  transparency, 
			  ambientReflectanceCoefficient,
			  diffuseReflectanceCoefficient, 
			  specularReflectanceCoefficient,
			  specularReflectanceHighlightCoefficient, 
			  transparencyColor,
			  TransmissionIndexIn, 
			  TransmissionIndexOut,
			  diffuseTransmissiveCoefficient,
			  specularTransmissiveCoefficient,
			  specularTransmissiveHighlightCoefficient);
		
		this.color2 = color2;
		this.color3 = color3;
		this.level1 = level1;
		this.level2 = level2;
		this.level3 = level3;
	}
	

	@Override
	protected Color getAmbientContribution(AmbientLight ambient, Ray ray, Intersection intersection, Scene scene) {
		double noiseCoef = 0;
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		Color luminance = ambient.getColor().times(ambient.getIntensity());
		Color result = new Color(0, 0, 0);
		for(int level=1; level<5; level++) {
			noiseCoef += (1.0/level)*Math.abs(
					ImprovedNoise.noise(level*0.05*point.getX(), 
										level*0.05*point.getY(),
										level*0.05*point.getZ()));

//		    result = result.plus(luminance.times(ambientReflectanceCoefficient).times(
//		        color.times(noiseCoef).plus(color2.times(1.0 - noiseCoef))));
//			
//			result = result.plus(luminance.times(this.ambientReflectanceCoefficient).times(
//								color.times(noiseCoef).plus(color2).times(1.0-noiseCoef)));
		}
		
		if(noiseCoef<=level1) {
			result = color;
		} else if(noiseCoef>level1 && noiseCoef<level2) {
			result = color.times((noiseCoef-level1)/(level2-level1)).plus(
					 color2.times((level2-noiseCoef)/(level2-level1)));
		} else if(noiseCoef>=level2 && noiseCoef<level3) {
			result = color2.times((noiseCoef-level2)/(level3-level2)).plus(
					 color3.times((level3-noiseCoef)/(level3-level2)));
		} else {
			result = color3;
		}

		System.out.println(noiseCoef);
		System.out.println(result);
		
		return luminance.times(this.ambientReflectanceCoefficient).times(result);
		
		
	}


}
