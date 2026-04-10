package jeuDeLaVie.src.cellule;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.visiteur.Visiteur;

public class Cellule {
    private int x;
    private int y;
    private CelluleEtat etat;


    public Cellule(int x, int y, CelluleEtat etat) {
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    public void accepte(Visiteur visiteur) {
        etat.accepte(visiteur, this);
    }

    public void vit() {
        this.etat = etat.vit();
    }

    public void meurt() {
        this.etat = etat.meurt();
    }

    public boolean estVivante() {
        return etat.estVivante();
    }

    public int nombreVoisinesVivantes(JeuDeLaVie jeu) {
        int nbVoisinesVivantes = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                
                if (i == 0 && j == 0) {
                    continue; 
                }

                int voisinX = this.x + i;
                int voisinY = this.y + j;

                Cellule voisine = jeu.getGrilleXY(voisinX, voisinY);

                if (voisine != null && voisine.estVivante()) {
                    nbVoisinesVivantes++;
                }
            }
        }
        return nbVoisinesVivantes;
    }
}