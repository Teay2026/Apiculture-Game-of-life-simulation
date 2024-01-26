package model.agents.animaux;

import java.awt.Point;

import model.agents.Sexe;
import model.agents.Agent;
import model.agents.Animal;
import model.agents.PointPositif;

/**
 * 
 * @author bruno
 *
 */
public class Varroa extends Animal {
	
	public Varroa(Sexe s, Point p) {
		super(s,p);
	}
	
	public void resetHebergeur() {
		this.hebergeur = null;
	}
	
	@Override
	public void rencontrer(Agent a) {
		if(a instanceof Abeille) {
			Abeille candidateAuParasitage = (Abeille) a;
			this.sInstaller(candidateAuParasitage);
		}
	}

	@Override
	protected void maj() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * si le Varroa a un hébergeur, il se déplace avec l'abeille
	 */
	public void seDeplacer() {
		if(hebergeur != null) {
			PointPositif coord_hebergeur = ((Animal)hebergeur).getCoord();
			this.setCoord(coord_hebergeur.getX(), coord_hebergeur.getY());
			if(faim) {
				seNourrir();
			}
		} else {
			super.seDeplacer();
		}
	}
	
	@Override
	public Object clone() {
		return new Varroa(getSexe(), new Point(getCoord().getX(),getCoord().getY()));
	}
}
