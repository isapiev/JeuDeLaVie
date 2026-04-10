package jeuDeLaVie.src.commande;

import jeuDeLaVie.src.cellule.Cellule;

public class CommandeMeurt extends Commande {
    
    public CommandeMeurt(Cellule c) {
        this.cellule = c;
    }

    @Override
    public void executer() {
        cellule.meurt();
    }
}