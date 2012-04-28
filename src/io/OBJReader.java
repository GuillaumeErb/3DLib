package io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import common.Point;

public class OBJReader {

	
	private Collection<Point> points;
	private String fileName;
	
	public OBJReader(String fileName) {
		this.points = new ArrayList<Point>();
		this.fileName = fileName;
	}
	
	public void read() {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(fileName + ".obj");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int nLine = 1;
			while ((strLine = br.readLine()) != null)   {
				String[] line = strLine.trim().toLowerCase().split(" ");
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
						case "f":
						default:
							System.out.println("Not implemented");
							
					}
				}
				nLine ++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	private void comment(String[] line, int nLine) {
		
	}
	
	public Collection<Point> getPoints() {
		return points;
	}
	
}
