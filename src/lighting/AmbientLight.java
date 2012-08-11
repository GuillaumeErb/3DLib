package lighting;

import common.Point;

import raytracer.Color;
import raytracer.Scene;

public class AmbientLight extends Light{

	public AmbientLight(Color color, double intensity) {
		super(color, intensity);
	}

	@Override
	public boolean lightsPoint(Point point, Scene scene) {
		return true;
	}


}
