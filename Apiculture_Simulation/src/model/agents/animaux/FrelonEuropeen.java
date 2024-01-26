package model.agents.animaux;

import java.awt.Point;

import model.agents.Sexe;

public class FrelonEuropeen extends Frelon {
	
	public FrelonEuropeen(Sexe sexe, Point coord) {
		super(sexe, coord);
		// TODO Auto-generated constructor stub
	}
	
	public Object clone() {
		return new FrelonEuropeen(getSexe(), new Point(getCoord().getX(),getCoord().getY()));
	}
}
