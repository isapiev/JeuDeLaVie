package jeuDeLaVie.src.visiteur;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;


public abstract class Visiteur {
    public JeuDeLaVie jeu;

    public Visiteur(JeuDeLaVie jeu) {
        this.jeu = jeu;
    }

    public abstract void visiteCelluleVivante(Cellule cellule);
    public abstract void visiteCelluleMorte(Cellule cellule);
}