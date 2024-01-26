package model.decor;

import java.awt.Point;

import model.agents.PointPositif;
import model.world.Dessinable;

public abstract class Decor implements Dessinable{
	/**
	 * coordonnées de l'élément de décor
	 */
	private PointPositif coord;
	
	private int width = 50;
	private int height = 50;

	public Decor(Point p) {
		coord = new PointPositif(p);
	}
	
	public PointPositif getCoord() {
		return (PointPositif)coord.clone();
	}

	public String getImage() { 
		return "images/"+getClass().getSimpleName()+".gif";
	}
	public int getWidth() { 
		return width;
	}
	public int getHeight() { 
		return height;
	}
}
