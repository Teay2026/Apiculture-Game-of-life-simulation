package model.decor;

import java.awt.Point;

public class Champ extends Decor {

	private boolean pesticide = false;
	
	public Champ(Point p) {
		super(p);
	}

	public boolean getPesticide() {
		return pesticide;
	}
}
