package jeuDeLaVie.src.decorateur;

import jeuDeLaVie.src.visiteur.Visiteur;
import jeuDeLaVie.src.cellule.Cellule;

public abstract class VisiteurDecorateur extends Visiteur {
    
    protected Visiteur visiteurDecore;

    public VisiteurDecorateur(Visiteur visiteurDecore) {
        super(visiteurDecore.jeu); // On récupère l'instance du jeu
        this.visiteurDecore = visiteurDecore;
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        visiteurDecore.visiteCelluleVivante(cellule);
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        visiteurDecore.visiteCelluleMorte(cellule);
    }
}