package objects;

import java.util.ArrayList;

import raytracer.Intersection;
import raytracer.Ray;

public class ObjectCSG implements Object3D {

	private OperationCSG operation;
	private Primitive primitive;
	private ObjectCSG leftSubtree;
	private ObjectCSG rightSubtree;
	
	
	
	public ObjectCSG(Primitive primitive) {
		super();
		this.primitive = primitive;
		this.operation = null;
		this.leftSubtree = null;
		this.rightSubtree = null;
	}

	public ObjectCSG(OperationCSG operation, ObjectCSG leftSubtree,
			ObjectCSG rightSubtree) {
		super();
		this.primitive = null;
		this.operation = operation;
		this.leftSubtree = leftSubtree;
		this.rightSubtree = rightSubtree;
	}
	

	public OperationCSG getOperation() {
		return operation;
	}

	public void setOperation(OperationCSG operation) {
		this.operation = operation;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}

	public ObjectCSG getLeftSubtree() {
		return leftSubtree;
	}

	public void setLeftSubtree(ObjectCSG leftSubtree) {
		this.leftSubtree = leftSubtree;
	}

	public ObjectCSG getRightSubtree() {
		return rightSubtree;
	}

	public void setRightSubtree(ObjectCSG rightSubtree) {
		this.rightSubtree = rightSubtree;
	}



	@Override
	public Intersection getIntersection(Ray ray) {
		ArrayList<E> tempIntersection;
		boolean enter1;
		boolean enter2;
		int i;
		
		tempIntersection.add()
		return null;
	}

}
