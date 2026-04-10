package jeuDeLaVie.src.decorateur;

import jeuDeLaVie.src.visiteur.Visiteur;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.CommandeVit;

public class VisiteurZombie extends VisiteurDecorateur {

    public VisiteurZombie(Visiteur visiteurDecore) {
        super(visiteurDecore);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        visiteurDecore.visiteCelluleVivante(cellule);
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);
        
        // Si la cellule morte a exactement 1 voisine vivante, elle a 5% de chance d'être infectée
        if (nbVoisines == 1 && Math.random() < 0.05) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        } else {
            visiteurDecore.visiteCelluleMorte(cellule);
        }
    }
}