package model.agents.animaux;

import java.awt.Point;

import model.agents.Animal;
import model.agents.Etat;
import model.agents.Sexe;

public class FrelonAsiatique extends Frelon{

	public FrelonAsiatique(Sexe sexe, Point coord) {
		super(sexe, coord);
		// TODO Auto-generated constructor stub
	}
	
	public void renconter(Animal a) {
		super.rencontrer(a);
		if(a instanceof FrelonEuropeen) {
			this.setNiveauSante(Etat.EnDetresse);
		}
	}
	
	public Object clone() {
		return new FrelonAsiatique(getSexe(), new Point(getCoord().getX(),getCoord().getY()));
	}

}
