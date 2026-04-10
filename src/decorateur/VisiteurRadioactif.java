package jeuDeLaVie.src.decorateur;

import jeuDeLaVie.src.visiteur.Visiteur;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.CommandeMeurt;

public class VisiteurRadioactif extends VisiteurDecorateur {

    public VisiteurRadioactif(Visiteur visiteurDecore) {
        super(visiteurDecore);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        // 5% de chance qu'une cellule vivante meure de radiation
        if (Math.random() < 0.05) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        } else {
            visiteurDecore.visiteCelluleVivante(cellule);
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        visiteurDecore.visiteCelluleMorte(cellule);
    }
}