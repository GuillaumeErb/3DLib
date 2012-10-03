package raytracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import common.Vect3;

public class Renderer {

	private final static boolean ANTIALIASING = true;
	private final static double SUPERSAMPLING_THRESHOLD_START = 0.2;
	private final static double SUPERSAMPLING_THRESHOLD_END = 0.01;
	private final static int SUPERSAMPLING_MAX_RAYS = 1;
	private final static double SUPERSAMPLING_SHOW_LIMIT = 16;
	private final static boolean SUPERSAMPLING_SHOW = true;
	private final static int THREADS = 4;
	private final static boolean MULTITHREADING = true;
	private final static boolean refining = true;
	private final static int refiningLevel = 4;
	
	private Scene scene;
	private Camera camera;
	private Screen screen;
	
	public Renderer(Scene scene, Camera camera, Screen screen) {
		this.scene = scene;
		this.camera = camera;
		this.screen = screen;
		this.screen.addKeyListener(new KeyListenerImpl());
	}
	
	private class KeyListenerImpl implements KeyListener {

		public KeyListenerImpl() {
			super();
		}
		
		@Override
		public void keyPressed(KeyEvent event) {
			switch(event.getKeyCode()) {
				case 90: //Z
					camera.translateFront();
					break;
				case 83: //S
					camera.translateBack();
					break;
				case 81: //Q
					camera.translateLeft();
					break;
				case 68: //D
					camera.translateRight();
					break;
				case 65: //A
					camera.translateDown();
					break;
				case 69: //E
					camera.translateUp();
					break;
				case 38: //^
					camera.rotateUp();
					break;
				case 40: //v
					camera.rotateDown();
					break;
				case 37: //<
					camera.rotateLeft();
					break;
				case 39: //>
					camera.rotateRight();
					break;
				case 0: //ù
					camera.rotateTrigo();
					break;
				case 151: //*
					camera.rotateClock();
					break;
				case 79: //o
					camera.rotateCenterUp();
					break;
				case 76: //l
					camera.rotateCenterDown();
					break;
				case 75: //k
					camera.rotateCenterLeft();
					break;
				case 77: //m
					camera.rotateCenterRight();
					break;
				default:
					System.out.println(event.getKeyCode());
					break;
				
			}
			
			screen.display(render());
		}

		
		@Override
		public void keyReleased(KeyEvent event) {}

		@Override
		public void keyTyped(KeyEvent event) {}

	}
		
	public BufferedImage render() {

		long start = (new Date()).getTime();
		
		BufferedImage ib = null;
		
		if(MULTITHREADING) {
			ib = renderMultithreading();
		} else {
			ib = renderBasic();
		}
		
		long time = (new Date()).getTime() - start;
		
		System.out.println("Execution Time: " + ((Long) time).toString() + " miliseconds");
		
		return ib;
	}
	
	
	public BufferedImage renderBasic() {
		BufferedImage buffer = new BufferedImage(camera.getXResolution(), camera.getYResolution(), BufferedImage.TYPE_3BYTE_BGR); //5
		
		for(int i=0; i<scene.getCamera().getXResolution(); i++) {
			for(int j=0; j<scene.getCamera().getYResolution(); j++) {
				Color color = renderPixel(i, j);
				buffer.setRGB(i, j, color.getRGB());
			}
		}
		
		return buffer;
	}
	
	public static void save(BufferedImage image, String output) {
		try {
		    File outputfile = new File(output);
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private BufferedImage renderMultithreading() {
		BufferedImage buffer = new BufferedImage(camera.getXResolution(), camera.getYResolution(), BufferedImage.TYPE_3BYTE_BGR);

		ArrayList<RenderThread> threads = new ArrayList<RenderThread>();
		RenderThread thread;
		int t=0;
		for(t=0; t<THREADS-1; t++) {
			thread = new RenderThread(t*scene.getCamera().getYResolution()/THREADS, 
									  (t+1)*scene.getCamera().getYResolution()/THREADS);
			thread.start();
			threads.add(thread);
		}
		thread = new RenderThread(t*scene.getCamera().getYResolution()/THREADS, 
				  				  scene.getCamera().getYResolution());
		thread.start();
		threads.add(thread);
		
		for(RenderThread rt : threads) {
			try {
				rt.join();
				for(int i=0; i<scene.getCamera().getXResolution(); i++) {
					for(int j=rt.begin; j<rt.end; j++) {
						buffer.setRGB(i, j, rt.lines.get(j-rt.begin).get(i).getRGB());
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return buffer;
		
	}
	
	public List<Color> renderLine(int l) {
		List<Color> colors = new ArrayList<Color>();
		for(int i=0; i<scene.getCamera().getXResolution(); i++) {
			colors.add(renderPixel(i, l));
		}
		return colors;
	}
	
	private Color renderPixel(int x, int y) {
		if(ANTIALIASING) {
			return renderPixel(x, y, 1);
		} else {
			return scene.renderPixel(x, y);
		}
	}
	
	
	private Color renderPixel(double x, double y, int subdivision) {

//		System.out.println(subdivision);
		
        ArrayList<Color> colors = new ArrayList<Color>();
        Color meanColor = new Color(0, 0, 0);
        Color oldMeanColor;

        ArrayList<Vect3> directions = camera.getFourDirections(x, y, subdivision);
        for (Vect3 direction : directions) {
            Ray ray = new Ray(camera.getPosition(), direction);
            Color res = scene.renderRay(ray, 10); //TODO Create a global recursion limit for reflections/refractions
            colors.add(res);
            meanColor = meanColor.plus(res);
        }
        
        meanColor = meanColor.dividedBy(4);
		
        boolean doSupersampling = false;
//        for (Color color : colors) {
//        	if(Math.abs(meanColor.minus(color).sum()) > SUPERSAMPLING_THRESHOLD_START)
//                doSupersampling = true;
//        }
        
        if(doSupersampling) {
        	oldMeanColor = meanColor;
			if((subdivision < SUPERSAMPLING_MAX_RAYS)
                    && (Math.abs(oldMeanColor.minus(meanColor).sum()) > SUPERSAMPLING_THRESHOLD_END)) {
				
            	double c = 1./(subdivision+1);
            	meanColor = renderPixel(x+c, y+c, subdivision+1);
            	meanColor = meanColor.plus(renderPixel(x+c, y+c, subdivision+1));
            	meanColor = meanColor.plus(renderPixel(x+c, y+c, subdivision+1));
            	meanColor = meanColor.plus(renderPixel(x+c, y+c, subdivision+1));
            }
			meanColor = meanColor.dividedBy(4);
			return meanColor;
        } else {
        	return meanColor;
        }
        
		
	}
	
	private class RenderThread extends Thread {
		
		public int begin;
		public int end;
		public List<List<Color>> lines;
		
		public RenderThread(int begin, int end) {
			this.begin = begin;
			this.end = end;
			this.lines = new ArrayList<List<Color>>();
		}

		public void run() {
			for(int l=this.begin; l<end; l++) {
				this.lines.add(renderLine(l));
			}
		}
		
	}
}
