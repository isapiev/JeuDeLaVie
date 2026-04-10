package jeuDeLaVie.src.visiteur;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;

public class VisiteurSeeds extends Visiteur {

    public VisiteurSeeds(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        jeu.ajouteCommande(new CommandeMeurt(cellule));
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        if (nbVoisines == 2) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}