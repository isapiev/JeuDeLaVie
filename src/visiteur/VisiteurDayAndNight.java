package jeuDeLaVie.src.visiteur;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;

public class VisiteurDayAndNight extends Visiteur {

    public VisiteurDayAndNight(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        if (nbVoisines == 0 || nbVoisines == 1 || nbVoisines == 2 || nbVoisines == 5) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        if (nbVoisines == 3 || nbVoisines == 6 || nbVoisines == 7 || nbVoisines == 8) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}