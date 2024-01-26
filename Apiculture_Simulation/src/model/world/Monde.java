package model.world;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Timer;

import model.agents.Agent;
import model.agents.Animal;
import model.agents.Etat;
import model.agents.PointPositif;
import model.agents.Sexe;
import model.agents.animaux.Abeille;
import model.agents.animaux.AbeilleDomestique;
import model.agents.animaux.AbeilleSolitaire;
import model.agents.animaux.FrelonAsiatique;
import model.agents.animaux.FrelonEuropeen;
import model.agents.animaux.Varroa;
import model.agents.vegetaux.Arbre;
import model.agents.vegetaux.Fleur;
import model.decor.Champ;
import model.decor.Decor;
import model.decor.Ruche;

public class Monde implements MondeAnimable {
	/**
	 * population d'agents dans le monde
	 */
	private Set<Agent>agents;
	/**
	 * Decor
	 */
	private Set<Decor>decors;
	/**
	 * map de probabilité pour trouver un agent
	 */
	private Map<Integer,Agent> proba;
	/**
	 * constante: largeur du monde
	 */
	private static int LARGEUR = 30;
	/**
	 * constante: longueur du monde
	 */
	private static int LONGUEUR = 20;
	
	private boolean jour = true;
	private int cycle_cpt = 0;
	
	private double rayon = 10.0;
	
	private Timer timer;
	
	/**
	 * 
	 * @param nbAgents
	 */
	
	public Monde(int nbAgents) {
		this(nbAgents,1000);
	}
	
	public Monde(int nbAgents, int delai) {
		proba = probaAgent();
		agents = generateAgents(nbAgents);
		timer=new Timer(delai, this);
	}
	
	/**
	 * @return the lARGEUR
	 */
	public static int getLARGEUR() {
		return LARGEUR;
	}

	/**
	 * @return the lONGUEUR
	 */
	public static int getLONGUEUR() {
		return LONGUEUR;
	}
	
	/**
	/**
	 * Méthode utilitaire statistique pour produire la table de proba
	 * d'apparition d'un agent
	 * @return
	 */
	private Map<Integer, Agent> probaAgent() {
		/*
		 * par commodité: la map n'est plus statique pour permettre le paramétrage
		 * par l'interface graphique des probabilités d'apparition d'agents.
		 */
		decors = new HashSet<Decor>();
		Ruche r1 =new Ruche(new Point(4,10));
		Ruche r2 =new Ruche(new Point(10,25));
		
		decors.add(r1);
		decors.add(r2);		
		Map<Integer,Agent> myMap = new LinkedHashMap<Integer,Agent>();
	    myMap.put(20,new AbeilleDomestique(Sexe.Assexue,new Point(0,0),r1));
	    myMap.put(40,new AbeilleDomestique(Sexe.Assexue,new Point(0,0),r2));
	    myMap.put(50,new AbeilleSolitaire(Sexe.Assexue,new Point(0,0)));
	    myMap.put(55,new FrelonEuropeen(Sexe.Assexue,new Point(0,0)));
	    myMap.put(60,new FrelonAsiatique(Sexe.Assexue,new Point(0,0)));
	    myMap.put(70,new Varroa(Sexe.Assexue,new Point(0,0)));
	    myMap.put(80,new Arbre(new Point(0,0),1.0));
	    myMap.put(85,new Arbre(new Point(0,0),2.0));
	    myMap.put(100,new Fleur(new Point(0,0)));
	    return myMap;
	}
	
	/**
	 * fabrication aléatoire d'un Agent par tirage dans la Map
	 * et positionnement aléatoire
	 * @param alea
	 * @return
	 */
	private Agent tirage(int alea) {
		Agent agent=null;
		if(alea<100 && alea>=0) {
			boolean trouve = false;
			Iterator<Integer> it = proba.keySet().iterator();
			while(!trouve && it.hasNext()) {
				Integer clef = it.next();
				if(clef>=alea) {
					agent = proba.get(clef);
					trouve=true;
				}
			}
		}
		else {
			agent = new Fleur(new Point(0,0));
		}
		//positionnement aléatoire entre Longueur et Largeur
		int aleaX = (int)(Math.random()*LONGUEUR);
		int aleaY = (int)(Math.random()*LARGEUR);
		agent.setCoord(aleaX, aleaY);
		return agent;
	}
	
	private TreeSet<Agent> generateAgents(int nbAgents) {
			/*
			 * NE PAS TOUCHER!
			 */
		TreeSet<Agent> ts = new TreeSet<Agent>();
		for(int i=0;i<nbAgents;i++) {
			int alea = (int)(Math.random()*100);
			ts.add((Agent)tirage(alea).clone());
		}
		return ts;
	}

	public String toString() {
		String ret="";
		ret+="******************************\n";
		ret+="Le monde contient "+agents.size()+" agents:\n";
		
		Set<Agent> coordSet = new TreeSet<Agent>(new CoordComparator());
		coordSet.addAll(agents);
		
		for(Agent a : coordSet) {
			ret += "\t" + a + "\n";
		}
		return ret;
	}

	/**
	 * génère un cycle de vie dans le monde
	 */
	public void cycle() {
		HashMap<Agent, HashSet<Agent>> hm = gererRencontre();
		
		cycle_cpt++;
		if(cycle_cpt > 3) {
			cycle_cpt = 0;
			jour = !jour;
		}
		
		hm.forEach((agent, voisins) -> {
			if(agent instanceof Animal) {
				((Animal)agent).rentrerHebergeur = !jour;
				
				for(Decor decor : decors) {
					if(decor instanceof Champ) {
						((Animal)agent).empoisone((Champ)decor);
					}
				}
			}
			
			agent.cycle();
			
			for (Agent voisin : voisins) {
				agent.rencontrer(voisin);
			}
			
			if(agent instanceof Animal) {
				if (((Animal)agent).getNiveauSante() == Etat.Mourant) {
					supprimer(agent);
				}
			}
		});
	}
	
	public static String remplir(Integer indice, PointPositif coord) {
		int cible = coord.getX()*LARGEUR + coord.getY()-indice;
		indice=cible+1;
		return String.format("%" + cible+"s","");		
	}
	
	private final void supprimer(Agent a) {
		agents.remove(a);
		if(a instanceof Abeille) {
			((Abeille) a).mourrir();
		}
		a = null;
	}
	
	private final HashMap<Agent, HashSet<Agent>> gererRencontre() {
		HashMap<Agent, HashSet<Agent>> hm = new HashMap<Agent, HashSet<Agent>>();
		
		for (Agent agent1 : agents) {
			PointPositif coord1 = agent1.getCoord();
			HashSet<Agent> hs = new HashSet<Agent>();
			for (Agent agent2 : agents) {
				if(!agent1.equals(agent2)){
					PointPositif coord2 = agent2.getCoord();
					double distance = Math.sqrt(Math.pow(coord1.getX() - coord2.getX(), 2) + Math.pow(coord1.getY() - coord2.getY(), 2));
					if(distance < rayon) {
						hs.add(agent2);
					}
				}
				
			}
			hm.put(agent1, hs);
		}
		return hm;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		cycle();		
		System.out.println("cycle");
	}

	@Override
	public void lancerAnimation() {
		timer.start();
	}

	@Override
	public void stopperAnimation() {
		timer.stop();	
	}

	@Override
	public List<Dessinable> getElementsMonde() {
		List<Dessinable> ret = new ArrayList<Dessinable>();
		ret.addAll(decors);
		ret.addAll(agents);
		return ret;
	}


}
