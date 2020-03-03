package inf4230.connect5.ia;

import inf4230.connect5.Position;

public class Action {
	private int j;
	private Position p;
	
	public Action(int j, Position p) {
		this.j = j;
		this.p = p;
	}

	public int getJ() {
		return j;
	}
	
	public Position getP() {
		return p;
	}

	@Override
	public String toString() {
		return "Action [j=" + j + ", p=" + p + "]";
	}
	
}
