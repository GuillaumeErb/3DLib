package materials;

import raytracer.Color;

public class Marble extends HallMaterial {

	public Marble(Color color1, Color color2, double reflectivity, double transparency,
			double ambientReflectanceCoefficient,
			double diffuseReflectanceCoefficient,
			double specularReflectanceCoefficient,
			double specularReflectanceHighlightCoefficient,
			Color transparencyColor, double TransmissionIndexIn,
			double TransmissionIndexOut, double diffuseTransmissiveCoefficient,
			double specularTransmissiveCoefficient,
			double specularTransmissiveHighlightCoefficient) {
		super(color1, reflectivity, transparency, ambientReflectanceCoefficient,
				diffuseReflectanceCoefficient, specularReflectanceCoefficient,
				specularReflectanceHighlightCoefficient, transparencyColor,
				TransmissionIndexIn, TransmissionIndexOut,
				diffuseTransmissiveCoefficient, specularTransmissiveCoefficient,
				specularTransmissiveHighlightCoefficient);
		// TODO Auto-generated constructor stub
	}

}
