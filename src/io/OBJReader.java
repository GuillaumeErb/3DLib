package io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import objects.Mesh;
import objects.Triangle;

import common.Point;
import common.Vect3;

public class OBJReader {

	
	private List<Point> points;
	private List<Vect3> normals;
	
	private Mesh mesh;
	
	private String fileName;
	
	public OBJReader(String fileName) {
		this.points = new ArrayList<Point>();
		this.normals = new ArrayList<Vect3>();
		this.mesh = new Mesh();
		this.fileName = fileName;
	}
	
	public Mesh extractMesh() {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(fileName + ".obj");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int nLine = 1;
			while ((strLine = br.readLine()) != null)   {
//				System.out.println(strLine);
				String[] line = strLine.trim().toLowerCase().split(" +");
				if(line.length>0) {
					switch(line[0].trim()) {
						case "v":
							v(line, nLine);
							break;
						case "#":
						case "":
							comment(line, nLine);
							break;
						case "vt":
						case "vn":
							vn(line, nLine);
							break;
						case "f":
							f(line, nLine);
							break;
						default:
							System.out.println(strLine);
							System.out.println("Not implemented");
							
					}
				}
				nLine ++;
			}
			in.close();
			return this.mesh;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private void v(String[] line, int nLine) {
		if(line.length == 4) {
			this.points.add(new Point(Double.parseDouble(line[1]),
									  Double.parseDouble(line[2]),
									  Double.parseDouble(line[3])));
			
		} else {
			System.out.println("malformed point definition line " + nLine);
		}
	}
	
	private void vn(String[] line, int nLine) {
		if(line.length == 4) {
			this.normals.add(new Vect3(Double.parseDouble(line[1]),
									   Double.parseDouble(line[2]),
									   Double.parseDouble(line[3])));
			
		} else {
			System.out.println("malformed point definition line " + nLine);
		}
	}

	private void f(String[] line, int nLine) {
		if(line.length == 4) {			
			
			String[] pointsStrings = {line[1], line[2], line[3]};
			
			List<Integer> pointIndex = new ArrayList<Integer>();
//			List<Integer> textureIndex = new ArrayList<Integer>();
			List<Integer> normalIndex = new ArrayList<Integer>();
			
			for(String point : pointsStrings) {
				String[] pointSplit = point.split("/");
				if(pointSplit.length == 1) {
					pointIndex.add(Integer.parseInt(pointSplit[0]));
				} else if(pointSplit.length == 2) {
//					pointIndex.add(Integer.parseInt(pointSplit[0]));
//					textureIndex.add(Integer.parseInt(pointSplit[1]));
					System.out.println("textures not handled");
				} else if(pointSplit.length == 3) {
					pointIndex.add(Integer.parseInt(pointSplit[0]));
					if(pointSplit[1] == "") {
						normalIndex.add(Integer.parseInt(pointSplit[2]));
					} else {
//						textureIndex.add(Integer.parseInt(pointSplit[1]));
						normalIndex.add(Integer.parseInt(pointSplit[2]));
					}
				} else {
					System.out.println("malformed point definition line " + nLine);
				}
			}
			
			Point x = this.points.get(pointIndex.get(0) - 1);
			Point y = this.points.get(pointIndex.get(1) - 1);
			Point z = this.points.get(pointIndex.get(2) - 1);
			
			this.mesh.addTriangle(new Triangle(x, y, z));
			
			Vect3 xn = null;
			Vect3 yn = null;
			Vect3 zn = null;
			
			if(normalIndex.size() == 3) {
				xn = this.normals.get(normalIndex.get(0) - 1);
				yn = this.normals.get(normalIndex.get(1) - 1);
				zn = this.normals.get(normalIndex.get(2) - 1);
				
				this.mesh.addNormal(x, xn);
				this.mesh.addNormal(y, yn);
				this.mesh.addNormal(z, zn);
				
			} else {
				System.out.println("normal must be given for each vertex of a face");
			}
			
		} else {
			System.out.println("malformed point definition line " + nLine);
		}
	}
	
	private void comment(String[] line, int nLine) {
		
	}

}
