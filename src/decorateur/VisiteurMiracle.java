package jeuDeLaVie.src.decorateur;

import jeuDeLaVie.src.visiteur.Visiteur;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.CommandeVit;

public class VisiteurMiracle extends VisiteurDecorateur {

    public VisiteurMiracle(Visiteur visiteurDecore) {
        super(visiteurDecore);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        visiteurDecore.visiteCelluleVivante(cellule);
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        // 0.1% de chance qu'une case vide prenne vie
        if (Math.random() < 0.001) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        } else {
            visiteurDecore.visiteCelluleMorte(cellule);
        }
    }
}