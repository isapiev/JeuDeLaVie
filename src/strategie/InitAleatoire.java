package jeuDeLaVie.src.strategie;

import jeuDeLaVie.src.JeuDeLaVie;

public class InitAleatoire implements StrategieInitialisation {
    @Override
    public void initialiser(JeuDeLaVie jeu, double proba) {
        for (int x = 0; x < jeu.getXMax(); x++) {
            for (int y = 0; y < jeu.getYMax(); y++) {
                if (Math.random() < proba) {
                    jeu.getGrilleXY(x, y).vit();
                } else {
                    jeu.getGrilleXY(x, y).meurt();
                }
            }
        }
    }
}