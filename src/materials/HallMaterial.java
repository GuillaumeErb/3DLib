package materials;

import objects.Object3D;
import lighting.AmbientLight;
import lighting.Light;
import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

import common.Point;
import common.Vect3;

public class HallMaterial implements Material {
	
	private Color color;
	private double reflectivity;
	private double transparency;
	private double ambientReflectanceCoefficient;
	private double diffuseReflectanceCoefficient;
	private double specularReflectanceCoefficient;
	private double specularReflectanceHighlightCoefficient;
	private Color transparencyColor;
	private double TransmissionIndexIn;
	private double TransmissionIndexOut;
	private double diffuseTransmissiveCoefficient;
	private double specularTransmissiveCoefficient;
	private double specularTransmissiveHighlightCoefficient;

	public HallMaterial(Color color,
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
		this.color = color;
		this.reflectivity = reflectivity;
		this.transparency = transparency;
		this.ambientReflectanceCoefficient = ambientReflectanceCoefficient;
		this.diffuseReflectanceCoefficient = diffuseReflectanceCoefficient;
		this.specularReflectanceCoefficient = specularReflectanceCoefficient;
		this.specularReflectanceHighlightCoefficient = specularReflectanceHighlightCoefficient;
		this.transparencyColor = transparencyColor;
		this.TransmissionIndexIn = TransmissionIndexIn;
		this.TransmissionIndexOut = TransmissionIndexOut;
		this.diffuseTransmissiveCoefficient = diffuseTransmissiveCoefficient;
		this.specularTransmissiveCoefficient = specularTransmissiveCoefficient;
		this.specularTransmissiveHighlightCoefficient = specularTransmissiveHighlightCoefficient;
	}
	
	@Override
	public Color renderRay(Ray ray, Intersection intersection, Scene scene, int recursionsLeft) {
		Color result = new Color(0, 0, 0);
		result = result.plus(this.getEmmitedContribution());
		result = result.plus(this.getAmbientContribution(scene.getAmbientLight(), ray, intersection, scene));
		for(Light light : scene.getLights()) {
			result = result.plus(this.getReflectedDiffuseContribution(light, ray, intersection, scene));
			result = result.plus(this.getReflectedSpecularContribution(light, ray, intersection, scene));
			result = result.plus(this.getRefractedDiffuseContribution(light, ray, intersection, scene));
//			result = result.plus(this.getRefractedSpecularContribution(light, ray, intersection, scene));
		}
//		for(SimpleObject object : scene.getObjects()) {
//			result = result.plus(this.getObjectReflectionContribution(object));
//			result = result.plus(this.getObjectTransmissionContribution(object));
//		}
		result = result.plus(this.getRecursiveReflectionContribution(ray, intersection, scene, recursionsLeft));
		result = result.plus(this.getRecursiveRefractionContribution(ray, intersection, scene, recursionsLeft));
		return result;
	}
	
	private Color getEmmitedContribution() {
		return new Color(0, 0, 0);
	}
	
	private Color getAmbientContribution(AmbientLight ambient, Ray ray, Intersection intersection, Scene scene) {
		double noiseCoef = 0;
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		Color luminance = ambient.getColor().times(ambient.getIntensity());
//		Color result = new Color(0, 0, 0);
//		Color color1 = new Color(0.6, 0.1, 0.3);
//		Color color2 = new Color(0.9, 0.2, 0.1);
//		for(int level=1; level<10; level++) {
//			noiseCoef += (1.0/level)*Math.abs(
//					ImprovedNoise.noise(level*0.05*point.getX(), 
//										level*0.05*point.getY(),
//										level*0.05*point.getZ()));
//			System.out.println(noiseCoef);
////			noiseCoef = 0.5f * Math.sin( (point.getX() + point.getY()) * 0.05 + noiseCoef) + 0.5f;
//		    result = result.plus(luminance.times(this.ambientReflectanceCoefficient).times(
//		        color1.times(noiseCoef).plus(color2.times(1.0 - noiseCoef))));
//			
////			result = result.plus(luminance.times(this.ambientReflectanceCoefficient).times(
////								color1.times(noiseCoef).plus(color2).times(1.0-noiseCoef)));
//		}
//		System.out.println(result);
//		return result;
		return this.color.times(luminance).times(this.ambientReflectanceCoefficient);
	}
	
	private Color getReflectedDiffuseContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));

		if(light.lightsPoint(point, scene)) {
			Color luminance = light.getColor().times(light.getIntensity());
			double cosangle = light.fromPointToLight(point).normalize().scalar(intersection.getNormal());
			if(cosangle<0) {
				return new Color(0, 0, 0);
			}
			return this.color.times(luminance).times(this.diffuseReflectanceCoefficient*cosangle);
		} else {
			return new Color(0, 0, 0);
		}
	}
	
	private Color getReflectedSpecularContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		if(light.lightsPoint(point, scene)) {
			Color luminance = light.getColor().times(light.getIntensity());
			Vect3 normalH = light.fromPointToLight(point).normalize().plus(ray.getDirection().times(-1)).normalize();
			double cosangle = normalH.scalar(intersection.getNormal());
			return luminance.times(this.specularReflectanceCoefficient*
								   Math.pow(cosangle, this.specularReflectanceHighlightCoefficient));
		} else {
			return new Color(0, 0, 0);
		}
	}
	
	private Color getRefractedDiffuseContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		if(light.lightsPoint(point, scene)) {
			Color luminance = light.getColor().times(light.getIntensity());
			double cosminusangle = light.fromPointToLight(point).normalize().scalar(intersection.getNormal().times(-1));
			return luminance.times(this.transparencyColor).times(cosminusangle*this.diffuseTransmissiveCoefficient);
		} else {
			return new Color(0, 0, 0);
		}
	}
	
	private Color getRefractedSpecularContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		if(light.lightsPoint(point, scene)) {
			Color luminance = light.getColor().times(light.getIntensity());
			double n = this.TransmissionIndexIn/this.TransmissionIndexOut;
			Vect3 normalH1 = light.fromPointToLight(point).normalize().plus(ray.getDirection().times(-n)).times(1/(n-1));
			double cosminusangle = normalH1.scalar(intersection.getNormal());
			return luminance.times(this.transparencyColor).
							 times(this.specularTransmissiveCoefficient*
							       Math.pow(cosminusangle, this.specularTransmissiveHighlightCoefficient));
		} else {
			return new Color(0, 0, 0);
		}
	}
	
	private Color getObjectReflectionContribution(Object3D object) {
		return new Color(0, 0, 0);
	}
	
	private Color getObjectTransmissionContribution(Object3D object) {
		return new Color(0, 0, 0);
	}
	
	private Color getRecursiveReflectionContribution(Ray ray, Intersection intersection, Scene scene, int recursionsLeft) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		Ray reflectedRay = new Ray(point,
								   ray.getDirection().times(-1).symmetry(intersection.getNormal()));
		return scene.renderRay(reflectedRay, recursionsLeft).times(this.reflectivity);
	}
	
	private Color getRecursiveRefractionContribution(Ray ray, Intersection intersection, Scene scene, int recursionsLeft) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));

		double n;
		if(ray.getDirection().scalar(intersection.getNormal()) > 0) {
			n = this.TransmissionIndexIn/this.TransmissionIndexOut;
		} else {
			n = this.TransmissionIndexOut/this.TransmissionIndexIn;
		}
		
		double cosI;
		double sinI2;
		Vect3 direction = null;
		
		if(this.TransmissionIndexIn == 0 || this.TransmissionIndexOut == 0) {
			System.out.println("WARNING TransmissionIndex equals zero");
		}
		
		if(intersection.getNormal().scalar(ray.getDirection()) < 0) {
		    n = this.TransmissionIndexIn/this.TransmissionIndexOut;
		    
		    cosI = ray.getDirection().scalar(intersection.getNormal());
		    sinI2 = 1-cosI*cosI;
		    if(sinI2 <= 1) {
			direction = ray.getDirection().times(n).plus(intersection.getNormal().times(n*cosI+Math.sqrt(1-n*n*sinI2)));
		    }
		} else {
		    n = this.TransmissionIndexOut/this.TransmissionIndexIn;
		    cosI = ray.getDirection().scalar(intersection.getNormal())*(-1);
		    sinI2 = 1 - cosI*cosI;
		    if(sinI2 <= 1) {
		    direction = ray.getDirection().times(n).plus(intersection.getNormal().times(-n*cosI-Math.sqrt(1-n*n*sinI2)));
		    }
		}

		Ray refractedRay = ray;

		if(sinI2 <= 1) {

		    refractedRay = new Ray(new Point(point.toVect3().plus(direction.times(0.1))),
				       direction);
		} else {
		    refractedRay = new Ray(point, (ray.getDirection().times(-1)).symmetry(intersection.getNormal()));
		}

		return scene.renderRay(refractedRay, recursionsLeft).times(this.transparency);
	}
	
}
