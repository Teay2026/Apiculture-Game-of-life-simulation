package model.agents.vegetaux;

import java.awt.Point;
import java.util.LinkedHashSet;

import model.agents.Agent;
import model.agents.Animal;
import model.agents.animaux.AbeilleDomestique;
import model.agents.animaux.AbeilleSolitaire;
import model.agents.animaux.Frelon;
import model.comportements.Hebergeur;

public class Arbre extends Vegetal implements Hebergeur{
	
	private LinkedHashSet<Animal> population;

	public Arbre(Point point, double taille) {
		super(point);
		this.taille = taille;
	}

	private double taille = 1.0;
	
	@Override
	public boolean peutAccueillir(Animal a) {
		return (a instanceof AbeilleSolitaire || a instanceof Frelon) && getMaxHeberges()>population.size() && !(population.contains(a));
	}

	private int getMaxHeberges() {
		return (int)(Math.pow(taille,2));
	}

	@Override
	public boolean accueillir(Animal a) {
		boolean ret = false;
		if(peutAccueillir(a)) {
			population.add(a);
			ret=true;
		}
		return ret;
	}

	@Override
	public void produire() {
		qteNectar += Math.pow(2, taille);		
	}

	public Object clone() {
		return new Arbre(new Point(getCoord().getX(),getCoord().getY()), this.taille);
	}

}
