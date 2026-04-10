package jeuDeLaVie.src.visiteur;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;

public class VisiteurHighLife extends Visiteur {

    public VisiteurHighLife(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        if (nbVoisines < 2 || nbVoisines > 3) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        if (nbVoisines == 3 || nbVoisines == 6) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}