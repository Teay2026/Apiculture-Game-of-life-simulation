package model.agents.animaux;

import java.awt.Point;

import model.agents.Agent;
import model.agents.Animal;
import model.agents.Etat;
import model.agents.PointPositif;
import model.agents.Sexe;
import model.agents.vegetaux.Fleur;
import model.agents.vegetaux.Vegetal;
import model.comportements.Hebergeur;
import model.decor.Decor;
import model.decor.Ruche;
/**
 * Abeille est un hébergeur pour ses parasites (Varroa par exemple)
 * @author bruno
 *
 */
public abstract class Abeille extends Animal implements Hebergeur {
	/**
	 * parasite éventuel de l'abeille
	 * si l'abeille est parasitée, passe à true
	 */
	private boolean estParasite = false;
	private Varroa parasite;
	/**
	 * quantité de miel transporté par l'abeille
	 */
	private int qteMiel = 0;
	/**
	 * constante donnant la quantité maximal de miel que l'abeille peut transporter
	 */
	private static final int qteMax = 10;
	private boolean rapporterMiel = false;
	
	public Abeille(Sexe s, Point p) {
		super(s, p);
	}

	@Override
	public void rencontrer(Agent a) {
		if(a instanceof Vegetal && qteMiel<Abeille.qteMax) {
			Vegetal v = (Vegetal)a;
			qteMiel = qteMiel + v.getPortionNectar();
			if(faim) { seNourrir(); }
		} else if(a instanceof Frelon && getNiveauSante()!=Etat.Mourant) {
			setNiveauSante(Etat.EnDetresse);
			if (a.aFaim()) { setNiveauSante(Etat.Mourant); }
		} else if(a instanceof Varroa && !estParasite) {
			this.aggraverEtat();
		}
	}
	
	@Override
	public void seDeplacer() {
		if(rapporterMiel && hebergeur != null) {
			PointPositif coord_hebergeur = ((Decor)hebergeur).getCoord();
			if(!coord_hebergeur.equals(coord)) {
				int directionx = ((coord_hebergeur.getX() - coord.getX()) > 0) ? 1 : -1;
				int directiony = ((coord_hebergeur.getY() - coord.getY()) > 0) ? 1 : -1;
				this.setCoord((int)(coord.getX()+directionx),(int)(coord.getY()+directiony));
			} else {
				donnerMiel();
			}
		} else {
			if (qteMiel>=qteMax && this instanceof AbeilleDomestique) {
				rapporterMiel = true;
			}
			super.seDeplacer();
		}
	}

	@Override
	public boolean peutAccueillir(Animal a) {
		/*
		 * l'abeille n'a pas de parasite et l'animal est un Varroa
		 */
		return a instanceof Varroa && !estParasite;
	}

	@Override
	public boolean accueillir(Animal a) {
		boolean ret = false;
		if(peutAccueillir(a)) {
			estParasite = true;
			parasite = (Varroa)a;
			aggraverEtat();
			ret = true;
		}
		return ret;
	}
	
	private void donnerMiel() {
		((Ruche)hebergeur).addMiel(qteMiel);
		qteMiel = 0;
		rapporterMiel = false;
	}	
	
	public void mourrir() {
		if(estParasite) {
			parasite.resetHebergeur();
		}
	}
	

	@Override
	protected void maj() {
		
		
	}

}
