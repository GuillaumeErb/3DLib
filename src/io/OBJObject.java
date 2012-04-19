package io;

public class OBJObject {

	/**
	 * Name of the object
	 */
	private String objectName;
	
	/**
	 * List of Vertices, with (x,y,z[,w]) coordinates,
	 * w is optional and defaults to 1.0.
	 */
	private StringBuilder vertices;
	
	private int nbVertices;
	
	/**
	 * Texture coordinates, in (u[,v][,w]) coordinates,
	 * v and w are optional and default to 0.
	 */
	private StringBuilder textureCoordinate;
	
	private int nbTextureCoordinate;
	
	/**
	 * Normals in (x,y,z) form; normals might not be unit.
	 */
	private StringBuilder normals;
	
	private int nbNormals;
	
	/**
	 * Face Definitions
	 * Faces are defined using lists of vertex, texture and normal indices
	 */
	private StringBuilder faces;
	
	
	public OBJObject(String objectName) {
		this.objectName = objectName;
		this.vertices = new StringBuilder("# List of Vertices\n");
		this.nbVertices = 0;
		this.textureCoordinate = new StringBuilder("# Texture coordinates\n");
		this.nbTextureCoordinate = 0;
		this.normals = new StringBuilder("# Normals\n");
		this.nbNormals = 0;
		this.faces = new StringBuilder("# Face definitions\n");
	}
	
	public int addVertex(double x, double y, double z) {
		this.vertices.append("v ").append(x).append(" ")
								  .append(y).append(" ")
								  .append(z).append("\n");
		return ++this.nbVertices;
	}
	
	public int addVertex(double x, double y, double z, double w) {
		this.vertices.append("v ").append(x).append(" ")
								  .append(y).append(" ")
								  .append(z).append(" ")
								  .append(w).append("\n");
		return ++this.nbVertices;
	}
	
	
	public int addTextureCoordinate(double u) {
		this.vertices.append("vt ").append(u).append("\n");
		return ++this.nbTextureCoordinate;
	}
	
	public int addTextureCoordinate(double u, double v) {
		this.vertices.append("vt ").append(u).append(" ")
								   .append(v).append("\n");
		return ++this.nbTextureCoordinate;
	}

	public int addTextureCoordinate(double u, double v, double w) {
		this.vertices.append("vt ").append(u).append(" ")
								   .append(v).append(" ")
		   						   .append(w).append("\n");
		return ++this.nbTextureCoordinate;
	}
	
	
	public int addNormal(double x, double y, double z) {
		this.vertices.append("vn ").append(x).append(" ")
								   .append(y).append(" ")
		  						   .append(z).append("\n");
		return ++this.nbNormals;
	}
	
	
	public void addFace(int[] vertices) {
		this.faces.append("f ");
		for(int v : vertices) {
			this.faces.append(v).append(" ");
		}
		this.faces.append("\n");
	}
	
	public void addFace(int[] vertices, int[] texture, int[] normals) {
		this.faces.append("f ");
		if(texture != null && normals != null) {
			for(int i=0; i<vertices.length; i++) {
				this.faces.append(vertices[i]).append("/")
						  .append(texture[i]).append("/")
						  .append(normals[i]).append(" ");
			}
		} else if (normals == null) {
			for(int i=0; i<vertices.length; i++) {
				this.faces.append(vertices[i]).append("/")
						  .append(texture[i]).append(" ");
			}
		} else if (texture == null) {
			for(int i=0; i<vertices.length; i++) {
				this.faces.append(vertices[i]).append("//")
						  .append(normals[i]).append(" ");
			}
		}
		this.faces.append("\n");
	}
	
	
	public String toOBJ() {
		return "# " + objectName + "\n#\n\n" + 
				"g " + objectName + "\n\n" +
				vertices.toString() + "\n" +
				textureCoordinate.toString() + "\n" +
				normals.toString() + "\n" +
				faces.toString() + "\n";
	}
	
	
}

