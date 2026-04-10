package jeuDeLaVie.src.cellule;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.visiteur.Visiteur;

public class CelluleEtatMort implements CelluleEtat {

    private static CelluleEtatMort instance;
    
    private CelluleEtatMort() {}

    public static CelluleEtatMort getInstance() {
        if (instance == null) {
            instance = new CelluleEtatMort();
        }
        return instance;
    }

    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleMorte(cellule);
    }

    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivant.getInstance();
    }

    @Override
    public CelluleEtat meurt() {
        return this;
    }

    @Override
    public boolean estVivante() {
        return false;
    }
}