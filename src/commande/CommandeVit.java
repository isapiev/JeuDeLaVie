package jeuDeLaVie.src.commande;

import jeuDeLaVie.src.cellule.Cellule;

public class CommandeVit extends Commande {
    
    public CommandeVit(Cellule c) {
        this.cellule = c;
    }

    @Override
    public void executer() {
        cellule.vit();
    }
}