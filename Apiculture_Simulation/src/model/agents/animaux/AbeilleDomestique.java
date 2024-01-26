package model.agents.animaux;

import java.awt.Point;

import model.decor.Ruche;
import model.agents.PointPositif;
import model.agents.Sexe;

public class AbeilleDomestique extends Abeille {

	public AbeilleDomestique(Sexe sexe, Point coord, Ruche r) {
		super(sexe, coord);
		hebergeur = r;
		r.accueillir(this);
	}
	
	public void mourrir() {
		super.mourrir();
		((Ruche)hebergeur).retirerAbeille(this);
	}
	
	@Override
	public Object clone() {
		return new AbeilleDomestique(getSexe(), new Point(getCoord().getX(),getCoord().getY()), (Ruche)hebergeur);
	}
	
	protected void maj() {
		super.maj();
		Ruche ruche = ((Ruche)hebergeur);
		PointPositif coord_hebergeur = ruche.getCoord();
		if(coord_hebergeur.equals(coord) && aFaim() && ruche.getQteMiel() > 0) {
			ruche.prendreMiel();
			this.seNourrir();
		}
	}
}
