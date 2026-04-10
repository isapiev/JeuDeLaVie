package jeuDeLaVie.src.commande;

import jeuDeLaVie.src.cellule.Cellule;

public abstract class Commande {
    protected Cellule cellule;

    public abstract void executer();
}