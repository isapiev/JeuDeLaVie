package jeuDeLaVie.src;

import jeuDeLaVie.src.observateurs.Observateur;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;

public class JeuDeLaVieConsole implements Observateur {
    private JeuDeLaVie jeu;
    private int numeroGeneration;

    public JeuDeLaVieConsole(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.numeroGeneration = 0;
    }

    @Override
    public void actualise() {
        numeroGeneration++;
        int cellulesVivantes = 0;

        for (int x = 0; x < jeu.getXMax(); x++) {
            for (int y = 0; y < jeu.getYMax(); y++) {
                if (jeu.getGrilleXY(x, y).estVivante()) {
                    cellulesVivantes++;
                }
            }
        }

        System.out.println("Génération n°" + numeroGeneration + " | Cellules en vie : " + cellulesVivantes);
    }
}