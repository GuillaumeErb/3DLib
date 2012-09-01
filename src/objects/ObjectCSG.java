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


	public Intersection getIntersection(Ray ray) {
		if(this.primitive != null) {
			return this.primitive.getIntersection(ray);
		} else if(this.operation == OperationCSG.UNION) {
			return this.union(ray);
		} else if(this.operation == OperationCSG.INTERSECTION) {
			return this.intersection(ray);
		} else if(this.operation == OperationCSG.DIFFERENCE) {
			return this.difference(ray);
		}
		return null;
	}
	
	private Intersection union(Ray ray) {
		return this.csgIntersection(ray, false, false);
	}
	
	private Intersection intersection(Ray ray) {
		return this.csgIntersection(ray, true, true);
	}
	
	private Intersection difference(Ray ray) {
		return this.csgIntersection(ray, true, false);
	}
	
	private Intersection csgIntersection(Ray ray, boolean enter1, boolean enter2) {
		ArrayList<Intersection> tempIntersection = new ArrayList<Intersection>();
		int i;
		
		Intersection left = leftSubtree.getIntersection(ray);
		if(left!=null) {
			tempIntersection.add(left);
		}
		Intersection right = rightSubtree.getIntersection(ray);
		if(right!=null) {
			tempIntersection.add(right);
		}
		
		
		if(tempIntersection.size() == 0) {
			return null;
		}
		
		i = 0;
		
		while(i<tempIntersection.size() && tempIntersection.get(i).getDistance()<0) {
			if(tempIntersection.get(i).getPrimitive().equals(leftSubtree)) {
				enter1 = ! enter1;
			} else {
				enter2 = ! enter2;
			}
			i++;
		}
		
		i=0;
		
		while(i<tempIntersection.size()) {
			if(enter1 == false && enter2 == false) {
				return tempIntersection.get(i);
			}
			if(tempIntersection.get(i).getPrimitive().equals(rightSubtree)) {
				enter1 = ! enter1;
			} else {
				enter2 = ! enter2;
			}
			if(enter1 == false && enter2 == false) {
				return tempIntersection.get(i);
			}
			i++;
		}
		
		return null;
	}

}

