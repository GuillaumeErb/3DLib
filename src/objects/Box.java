package objects;

import materials.Material;
import common.Point;
import common.Vect3;

public class Box extends ObjectCSG {

	public Box(Point center, 
			double length,
			double width, 
			double height,
			Vect3 lengthVector,
			Vect3 widthVector,
			Vect3 heightVector,
			Material material) {
		
		
		lengthVector = lengthVector.normalize();
		widthVector = widthVector.normalize();
		heightVector = heightVector.normalize();
		
		HalfSpace front = new HalfSpace(
				new Point(center.toVect3().plus(widthVector.times(width/2))),
				widthVector,
				material);
		
		HalfSpace back = new HalfSpace(
				new Point(center.toVect3().minus(widthVector.times(width/2))),
				widthVector.times(-1),
				material);
		
		HalfSpace left = new HalfSpace(
				new Point(center.toVect3().plus(lengthVector.times(length/2))),
				lengthVector,
				material);
		
		HalfSpace right = new HalfSpace(
				new Point(center.toVect3().minus(lengthVector.times(length/2))),
				lengthVector.times(-1),
				material);
		
		HalfSpace up = new HalfSpace(
				new Point(center.toVect3().plus(heightVector.times(height/2))),
				heightVector,
				material);
		
		HalfSpace down = new HalfSpace(
				new Point(center.toVect3().minus(heightVector.times(height/2))),
				heightVector.times(-1),
				material);
		
		ObjectCSG p1 = new ObjectCSG(OperationCSG.INTERSECTION, new ObjectCSG(front), new ObjectCSG(back));
		ObjectCSG p2 = new ObjectCSG(OperationCSG.INTERSECTION, new ObjectCSG(left), new ObjectCSG(right));
		ObjectCSG p3 = new ObjectCSG(OperationCSG.DIFFERENCE, new ObjectCSG(up), p1);
		ObjectCSG p4 = new ObjectCSG(OperationCSG.DIFFERENCE, new ObjectCSG(down), p2);
		
		this.setOperation(OperationCSG.INTERSECTION);
		this.setLeftSubtree(p3);
		this.setRightSubtree(p4);
		
//		this.setLeftSubtree(new ObjectCSG(back));
//		this.setRightSubtree(new ObjectCSG(right));
		
//		this.setLeftSubtree(new ObjectCSG(down));
//		this.setRightSubtree(new ObjectCSG(OperationCSG.DIFFERENCE, new ObjectCSG(right), new ObjectCSG(front)));
		
	}
	
	public Box(Material material) {
		this(new Point(0, 0, 0),
			 1, 1, 1, new Vect3(0, 1, 0), new Vect3(1, 0, 0), new Vect3(0, 0, 1), material);
	}
	
}
