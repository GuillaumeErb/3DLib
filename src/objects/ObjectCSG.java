package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	protected ObjectCSG() {
		super();
		this.primitive = null;
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

	public boolean contains(Primitive primtive) {
		if(this.primitive != null) {
			return this.primitive.equals(primtive);
		} else {
			return this.rightSubtree.contains(primtive) ||
					this.rightSubtree.contains(primtive);
		}
	}
	
	public Intersection getIntersection(Ray ray) {
		
		List<Intersection> intersections = this.getIntersections(ray);
		if(intersections != null) {
			Collections.sort(intersections);
			for(Intersection i : intersections) {
				if(i.getDistance() >= 0) {
					return i;
				}
			}
		}
		return null;
	}
	
//	public List<Intersection> getAllIntersections(Ray ray) {
//		List<Intersection> li = new ArrayList<Intersection>();
//		if(this.primitive != null) {
//			li.addAll(this.primitive.getIntersections(ray));
//		} else {
//			li.addAll(this.getLeftSubtree().getAllIntersections(ray));
//			li.addAll(this.getRightSubtree().getAllIntersections(ray));
//		}
//		
//		if(li.isEmpty()) {
//			return null;
//		} else {
//			return li;
//		}
//		
//	}
	
	public List<Intersection> getIntersections(Ray ray) {
		if(this.primitive != null) {
			return this.primitive.getIntersections(ray);
		} else if(this.operation == OperationCSG.UNION) {
			return this.union(ray);
		} else if(this.operation == OperationCSG.INTERSECTION) {
			return this.intersection(ray);
		} else if(this.operation == OperationCSG.DIFFERENCE) {
			return this.difference(ray);
		}
		return null;
	}
	
	private List<Intersection> union(Ray ray) {
		return this.csgIntersections(ray, false, false);
	}
	
	private List<Intersection> intersection(Ray ray) {
		return this.csgIntersections(ray, true, true);
	}
	
	private List<Intersection> difference(Ray ray) {
		return this.csgIntersections(ray, true, false);
	}
	
	private List<Intersection> csgIntersections(Ray ray, boolean enter1, boolean enter2) {
		ArrayList<Intersection> tempIntersection = new ArrayList<Intersection>();
		int i;
		
		List<Intersection> left = leftSubtree.getIntersections(ray);
		if(left != null) {
			for(Intersection leftElement : left) {
				if(leftElement != null) {
					tempIntersection.add(leftElement);
				}
			}
		}
		
		List<Intersection> right = rightSubtree.getIntersections(ray);
		if(right != null) {
			for(Intersection rightElement : right) {
				if(rightElement != null) {
					tempIntersection.add(rightElement);
				}
			}
		}
		
		Collections.sort(tempIntersection);
		
		if(tempIntersection.size() == 0) {
			return null;
		}
		
		i = 0;
		
		while(i<tempIntersection.size() && tempIntersection.get(i).getDistance()<0) {
			if(leftSubtree.contains(tempIntersection.get(i).getPrimitive())) {
				enter1 = ! enter1;
			} else {
				enter2 = ! enter2;
			}
			i++;
		}
		
		i=0;
		
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		for(Intersection tempInter : tempIntersection) {
			if(enter1 == false && enter2 == false) {
				intersections.add(tempInter);
			}
			if(rightSubtree.contains(tempInter.getPrimitive())) {
				enter1 = ! enter1;
			} else {
				enter2 = ! enter2;
			}
			if(enter1 == false && enter2 == false) {
				intersections.add(tempInter);
			}
		}
		
		return intersections;
	}

}

