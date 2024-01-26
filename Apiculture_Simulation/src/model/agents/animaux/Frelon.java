package model.agents.animaux;

import java.awt.Point;
import java.util.ArrayList;

import model.agents.Agent;
import model.agents.Animal;
import model.agents.Etat;
import model.agents.Sexe;

public abstract class Frelon extends Animal {
	/**
	 * list d'objets de type "Class"
	 * Ces types Class sont paramétrés par <? extends Animal>
	 * Cela signifie que la classe représentée par Class doit hériter de la classe Animal
	 */
	protected ArrayList<Class<? extends Animal>> proies;
	
	public Frelon(Sexe s,Point p) {
		super(s,p);
		proies = new ArrayList<Class<? extends Animal>>();
		
		proies.add(Abeille.class);
		proies.add(AbeilleSolitaire.class);
		proies.add(AbeilleDomestique.class);
	}
	
	@Override
	public void rencontrer(Agent a) {
		try {
			gestionProie((Animal)a);
		} catch (ClassCastException cce) {
			System.err.println(a+" n'est pas un Animal");
		}
		
	}
	
	protected void gestionProie(Animal a) {
		if(faim && proies.contains(a.getClass())) {
			this.seNourrir();
		}
	}
	
	@Override
	protected void maj() {
		// TODO Auto-generated method stub
		
	}
	
}
