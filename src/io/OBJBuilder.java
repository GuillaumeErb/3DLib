package io;

import java.io.FileWriter;
import java.io.IOException;

public class OBJBuilder {

	private StringBuilder builder;
	
	private String fileName;
	
	public OBJBuilder(String fileName) {
		this.builder = new StringBuilder();
		this.fileName = fileName;
	}
	
	public void addObject(OBJObject o) {
		builder.append(o.toOBJ());
	}
	
	public void save() {
		FileWriter writer;
		try {
			writer = new FileWriter(fileName + ".obj");
			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
