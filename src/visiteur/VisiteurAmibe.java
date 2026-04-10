package jeuDeLaVie.src.visiteur;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;

public class VisiteurAmibe extends Visiteur {

    public VisiteurAmibe(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisines == 0 || nbVoisines == 2 || nbVoisines == 4 || nbVoisines == 6 || nbVoisines == 7) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisines == 3 || nbVoisines == 5 || nbVoisines == 7) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}