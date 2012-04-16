package common;

import java.util.ArrayList;
import java.util.Collection;

public class Simplices extends ArrayList<Simplex> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 160711842028786685L;

	public Simplices() {
		super();
	}

	public Simplices(Collection<? extends Simplex> c) {
		super(c);
	}

	public Simplices(int initialCapacity) {
		super(initialCapacity);
	}

	public Simplex getContainingSimplex(Point point) {
		for(Simplex s : this) {
			if(s.contains(point)) {
				return s;
			}
		}
		return null;
	}
	
	public int remove(Point point) {
		int nbSimplexRemoved = 0;
		for(Simplex s : this) {
			if(s.hasAsVertex(point)) {
				this.remove(s);
				nbSimplexRemoved++;
			}
		}
		return nbSimplexRemoved;
	}
	
	
}
