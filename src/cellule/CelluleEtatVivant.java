package jeuDeLaVie.src.cellule;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.visiteur.Visiteur;

public class CelluleEtatVivant implements CelluleEtat {

    private static CelluleEtatVivant instance;

    private CelluleEtatVivant() {}

    public static CelluleEtatVivant getInstance() {
        if (instance == null) {
            instance = new CelluleEtatVivant();
        }
        return instance;
    }

    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }

    @Override
    public CelluleEtat vit() {
        return this;
    }

    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    @Override
    public boolean estVivante() {
        return true;
    }
}