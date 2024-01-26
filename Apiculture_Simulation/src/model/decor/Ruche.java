package model.decor;

import java.awt.Point;
import java.util.LinkedHashSet;

import model.agents.Animal;
import model.agents.Sexe;
import model.agents.animaux.AbeilleDomestique;
import model.comportements.Hebergeur;

public class Ruche extends Decor implements Hebergeur{
	
	/**
	 * Liste des abeilles de la ruche 
	 */
	//private TODO population;
	/**
	 * constante taille maximale de la ruche
	 */
	private static int populationMax = 1000;
	private LinkedHashSet<AbeilleDomestique> population;
	private int qteMiel = 10;
	
	public Ruche(Point p) {
		super(p);
		population = new LinkedHashSet<AbeilleDomestique>();
	}

	@Override
	public boolean peutAccueillir(Animal a) {
		return (a instanceof AbeilleDomestique && populationMax>population.size() && !(population.contains(a)));
	}

	@Override
	public boolean accueillir(Animal a) {
		boolean ret = false;
		if(peutAccueillir(a)) {
			population.add((AbeilleDomestique)a);
			ret=true;
		}
		return ret;
	}
	
	public int getQteMiel() {
		return qteMiel;
	}
	
	public void addMiel(int qte) {
		qteMiel += qte;
	}
	
	public void prendreMiel() {
		qteMiel -= 1;
	}
	
	public void retirerAbeille(AbeilleDomestique a) {
		if(population.contains(a)) {
			population.remove(a);
		}
	}
	
	public String DisplayMiel() {
		return "Miel_Dispo:" + getQteMiel();
	}
	
	public String toString() {
		String ret = getClass().getSimpleName() + " " + getCoord() + " : population " + population.size() + " abeilles\n";
		
		for(AbeilleDomestique abeille : population) {
			ret += "\t"+abeille.toString()+"\n";
		};
		return ret;
	}
}
