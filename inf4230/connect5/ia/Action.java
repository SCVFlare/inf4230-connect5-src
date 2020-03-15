package inf4230.connect5.ia;

import inf4230.connect5.Position;

public class Action {
	private int j;
	private Position p;
	
	//private Action parent;
	
	public Action(int j, Position p) {
		this.j = j;
		this.p = p;
		
		//this.parent = null;
	}
	
	public Action(int j, Position p, Action pa) {
		this.j = j;
		this.p = p;
		
		//this.parent = pa;
	}

	public int getJ() {
		return j;
	}
	
	public Position getP() {
		return p;
	}
	
	/*public void setParent(Action p) {
		parent = p;
	}
	public Action getParent() {
		return parent;
	}*/

	@Override
	public String toString() {
		return "Action [j=" + j + ", p=" + p +"]";
	}
	
}
