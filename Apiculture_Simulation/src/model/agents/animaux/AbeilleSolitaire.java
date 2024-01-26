package model.agents.animaux;

import java.awt.Point;

import model.agents.Sexe;

public class AbeilleSolitaire extends Abeille{

	public AbeilleSolitaire(Sexe sexe, Point coord) {
		super(sexe, coord);
		// TODO Auto-generated constructor stub
	}
	public Object clone() {
		return new AbeilleSolitaire(getSexe(), new Point(getCoord().getX(),getCoord().getY()));
	}
}
