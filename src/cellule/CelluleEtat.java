package jeuDeLaVie.src.cellule;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.visiteur.Visiteur;

public interface CelluleEtat {
    CelluleEtat vit();
    CelluleEtat meurt();
    boolean estVivante();
    void accepte(Visiteur visiteur, Cellule cellule);
}