package io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raytracer.Color;

public class ImageWriter {

	private BufferedImage image;
	
	public ImageWriter(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public void setRGB(int x, int y, 
			           int r, int g, int b) {
		
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		this.image.setRGB(x, y, rgb);
	}

	public void initialize(Color color) {
		for(int i=0; i<this.image.getWidth(); i++) {
			for(int j=0; j<this.image.getHeight(); j++) {
				this.setRGB(i, j, color);
			}
		}
	}
	
	public void initialize() {
		this.initialize(new Color(0, 0, 0));
	}
	
	public void setPoint(int x, int y, int radius) {
		this.setRGB(x, y, new Color(0, 0, 0));
	}
	
	public void write(String output) throws IOException {
		File outputFile = new File(output);
		ImageIO.write(this.image, "png", outputFile);
	}

	public void setRGB(int x, int y, Color color) {
		this.setRGB(x, y, color.getR255(), color.getG255(), color.getB255());
	}
}
