package model.agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
//pour l'exemple détaillé de aggraverEtat
import java.util.List;
import java.util.ListIterator;

import model.comportements.Deplacable;
import model.comportements.Hebergeur;
import model.decor.Champ;
import model.decor.Decor;

/**
 * Cette classe modélise un Animal dans la simulation
 * @author bruno
 *
 */
/* 
 * abstract à partir du TP2 + déplacement des méthodes/attributs du TP1 concernant les agents dans la classe agent:
 * attributs de classe 
	private static int currentId = 0;
	
	attributs d'instance:
	private int id;
	protected int age;
	protected PointPositif coord; //question subsdiaire du tp2 + solution présentée au cours 4
	//protected Point coord;
	 
	méthodes:
	public Agent(Point coord)
	public Agent()
	
	equals, hascode,tostring (sans le sexe)
	getCoord, setAge, vieillir
	
	getUniqueId
	
	Attention: rencontrer(Agent a) devient abstrait 
 */
public abstract class Animal extends Agent implements Deplacable {
	/*
	 * SeDeplacer: soit abstract, soit encore mieux faire une interface Deplacable
	 */
	
	/** état de santé de l'animal */
	private Etat etat=Etat.Normal;
	/** sexe de l'animal */
	private Sexe sexe;
	/** hebergeur de l'animal */
	protected Hebergeur hebergeur;
	
	/** type de deplacement de l'animal */
	public boolean rentrerHebergeur;
	
	/* 
	 * constructeurs 
	 */
	public Animal(Sexe sexe, Point p) {
		super(p);
		this.sexe=sexe;
	}
	
	public Animal(Sexe sexe) {
		this(sexe,new Point(0,0));
	}
	
	public Animal() {
		this(Sexe.Femelle);
	}
	
	/*
	 *  Accesseurs et mutateurs
	 */
	public Sexe getSexe() {
		return sexe;
	}
	
	public Etat getNiveauSante() {return etat;}
	/**
	 * protected, car seul l'animal doit pouvoir changer son niveau de santé
	 * @return
	 */
	protected void setNiveauSante(Etat e) {etat = e;}
	/*
	 * (non-Javadoc)
	 * @see complet.model.agents.Agent#toString()
	 */
	public String toString() {
		//bien penser à réutiliser l'existant de Agent avec le super.toString()
		return super.toString()+", "+sexe;
	}
	
	/* 
	 * comportements d'instance
	 */
	/*
	 * (non-Javadoc)
	 * @see complet.model.comportements.Deplacable#seDeplacer()
	 */
	/**
	 * déplacement aléatoire avec -1<=dx<=1 et  -1<=dy<=1
	 * @see model.comportements.Deplacable#seDeplacer()
	 */
	public void seDeplacer() {
		if(rentrerHebergeur && hebergeur != null) {
			PointPositif coord_hebergeur = ((Decor)hebergeur).getCoord();
			if(!coord_hebergeur.equals(coord)) {
				int directionx = ((coord_hebergeur.getX() - coord.getX()) > 0) ? 1 : -1;
				int directiony = ((coord_hebergeur.getY() - coord.getY()) > 0) ? 1 : -1;
				this.setCoord((int)(coord.getX()+directionx),(int)(coord.getY()+directiony));
			}
		} else {
			int aleaX = (int)(Math.random()*3)-1;
			int aleaY = (int)(Math.random()*3)-1;
			this.setCoord((int)(coord.getX()+aleaX),(int)(coord.getY()+aleaY));
		}
	}
	
	/**
	 * condition d'installation d'un animal dans un hébergeur
	 * @param h
	 * @return
	 */
	protected final boolean sInstaller(Hebergeur h) {
		boolean ret = false;
		if(h != null && h.accueillir(this)) {
			hebergeur = h;
			ret = true;
		}
		return ret;
	}
	
	protected final void aggraverEtat() {
		LinkedList<Etat> liste = new LinkedList<Etat>(Arrays.asList(Etat.values()));
		ListIterator<Etat> it = liste.listIterator(liste.indexOf(etat)+1);
		if(it.hasNext()) { etat = it.next(); }
	}
	
	protected final void ameliorerEtat() {
		if(getNiveauSante() != Etat.Mourant) {
			LinkedList<Etat> liste = new LinkedList<Etat>(Arrays.asList(Etat.values()));
			ListIterator<Etat> it = liste.listIterator(liste.indexOf(etat));
			if(it.hasPrevious()) { etat = it.previous(); }
		}
	}

	public void empoisone(Champ c) {
		if(c.getPesticide()) {
			int c_x = c.getCoord().getX();
			int c_y = c.getCoord().getY();
			int a_x = getCoord().getX();
			int a_y = getCoord().getY();
			
			if((c_x <= a_x && a_x <= c_x + c.getWidth()) && (c_y <= a_y && a_y <= c_y + c.getHeight())) {
				aggraverEtat();
			}
		}
	}
	
	protected void seNourrir() {
		this.ameliorerEtat();
		this.cycles_sans_senourrir = 0;
		if(this.getNiveauSante() != Etat.PleineForme) {
			this.faim = false;
		}
	}
}
