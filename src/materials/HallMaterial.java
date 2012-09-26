package materials;

import lighting.AmbientLight;
import lighting.Light;
import objects.Object3D;
import raytracer.Color;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;

import common.Point;
import common.Vect3;

public class HallMaterial implements Material {
	
	protected Color color;
	protected double reflectivity;
	protected double transparency;
	protected double ambientReflectanceCoefficient;
	protected double diffuseReflectanceCoefficient;
	protected double specularReflectanceCoefficient;
	protected double specularReflectanceHighlightCoefficient;
	protected Color transparencyColor;
	protected double TransmissionIndexIn;
	protected double TransmissionIndexOut;
	protected double diffuseTransmissiveCoefficient;
	protected double specularTransmissiveCoefficient;
	protected double specularTransmissiveHighlightCoefficient;
	protected double bump;

	
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
		this(color,
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
				specularTransmissiveHighlightCoefficient, 0);
	}
	
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
						double specularTransmissiveHighlightCoefficient, double bump) {
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
		this.bump = bump;
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
	
	protected Vect3 getNormal(Ray ray, Intersection intersection) {
		if(this.bump != 0) {
			Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
			
			double noiseX = ImprovedNoise.noise(point.getX(), point.getY(), point.getZ());
			double noiseY = ImprovedNoise.noise(point.getY(), point.getZ(), point.getX());
			double noiseZ = ImprovedNoise.noise(point.getZ(), point.getX(), point.getY());
			
			Vect3 noise = new Vect3(noiseX, noiseY, noiseZ);
			
			Vect3 normal = intersection.getNormal().times(1-bump).plus(noise.times(bump));
			
			return normal.normalize();
		} else {
			return intersection.getNormal();
		}
		
//		return intersection.getNormal();
	}
	
	protected Color getColor(Ray ray, Intersection intersection) {
		return this.color;
	}
	
	private Color getEmmitedContribution() {
		return new Color(0, 0, 0);
	}
	
	protected Color getAmbientContribution(AmbientLight ambient, Ray ray, Intersection intersection, Scene scene) {
		Color luminance = ambient.getColor().times(ambient.getIntensity());
		return this.getColor(ray, intersection).times(luminance).times(this.ambientReflectanceCoefficient);
	}
	
	private Color getReflectedDiffuseContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));

		if(light.lightsPoint(point, scene)) {
//			System.out.println("lights");
//			System.out.println(intersection.getNormal());
//			intersection.setNormal(intersection.getNormal().times(-1));
			Color luminance = light.getColor().times(light.getIntensity());
			double cosangle = light.fromPointToLight(point).normalize().scalar(this.getNormal(ray, intersection));
			if(cosangle<0) {
				return new Color(0, 0, 0);
			}
			return this.getColor(ray, intersection).times(luminance).times(this.diffuseReflectanceCoefficient*cosangle);
		} else {
			return new Color(0, 0, 0);
		}
	}
	
	private Color getReflectedSpecularContribution(Light light, Ray ray, Intersection intersection, Scene scene) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));
		if(light.lightsPoint(point, scene)) {
			Color luminance = light.getColor().times(light.getIntensity());
			Vect3 normalH = light.fromPointToLight(point).normalize().plus(ray.getDirection().times(-1)).normalize();
			double cosangle = normalH.scalar(this.getNormal(ray, intersection));
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
			double cosminusangle = light.fromPointToLight(point).normalize().scalar(this.getNormal(ray, intersection).times(-1));
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
			double cosminusangle = normalH1.scalar(this.getNormal(ray, intersection));
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
								   ray.getDirection().times(-1).symmetry(this.getNormal(ray, intersection)));
		return scene.renderRay(reflectedRay, recursionsLeft).times(this.reflectivity);
	}
	
	private Color getRecursiveRefractionContribution(Ray ray, Intersection intersection, Scene scene, int recursionsLeft) {
		Point point = new Point(ray.getOrigin().toVect3().plus(ray.getDirection().times(intersection.getDistance())));

		double n;
		if(ray.getDirection().scalar(this.getNormal(ray, intersection)) > 0) {
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
		
		if(this.getNormal(ray, intersection).scalar(ray.getDirection()) < 0) {
		    n = this.TransmissionIndexIn/this.TransmissionIndexOut;
		    
		    cosI = ray.getDirection().scalar(this.getNormal(ray, intersection));
		    sinI2 = 1-cosI*cosI;
		    if(sinI2 <= 1) {
		    	direction = ray.getDirection().times(n).plus(this.getNormal(ray, intersection).times(n*cosI+Math.sqrt(1-n*n*sinI2)));
		    }
		} else {
		    n = this.TransmissionIndexOut/this.TransmissionIndexIn;
		    cosI = ray.getDirection().scalar(this.getNormal(ray, intersection))*(-1);
		    sinI2 = 1 - cosI*cosI;
		    if(sinI2 <= 1) {
		    	direction = ray.getDirection().times(n).plus(this.getNormal(ray, intersection).times(-n*cosI-Math.sqrt(1-n*n*sinI2)));
		    }
		}

		Ray refractedRay = ray;

		if(sinI2 <= 1) {
		    refractedRay = new Ray(new Point(point.toVect3().plus(direction.times(0.1))), direction);
		} else {
		    refractedRay = new Ray(point, (ray.getDirection().times(-1)).symmetry(this.getNormal(ray, intersection)));
		}

		return scene.renderRay(refractedRay, recursionsLeft).times(this.transparency);
	}
	
}
