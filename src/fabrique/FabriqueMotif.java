package jeuDeLaVie.src.fabrique;

import jeuDeLaVie.src.JeuDeLaVie;
import jeuDeLaVie.src.cellule.Cellule;

public class FabriqueMotif {

    public static void inserer(String motif, JeuDeLaVie jeu, int cx, int cy) {
        switch (motif) {
            case "Planeur": dessiner(jeu, cx, cy, new String[]{".O.", "..O", "OOO"}); break;
            case "Clignotant": dessiner(jeu, cx, cy, new String[]{"OOO"}); break;
            case "Bloc": dessiner(jeu, cx, cy, new String[]{"OO", "OO"}); break;
            case "Crapaud": dessiner(jeu, cx, cy, new String[]{".OOO", "OOO."}); break;
            case "Vaisseau": dessiner(jeu, cx, cy, new String[]{".O..O", "O....", "O...O", "OOOO."}); break;
            case "Canon à planeurs": dessiner(jeu, cx - 18, cy - 5, new String[]{
                "........................O...........", "......................O.O...........",
                "............OO......OO............OO", "...........O...O....OO............OO",
                "OO........O.....O...OO..............", "OO........O...O.OO....O.O...........",
                "..........O.....O.......O...........", "...........O...O....................",
                "............OO......................"}); break;
            case "Le Gland": dessiner(jeu, cx, cy, new String[]{".O.....", "...O...", "OO..OOO"}); break;
            case "Pulsar": dessiner(jeu, cx, cy, new String[]{
                "..OOO...OOO..", ".............", "O....O.O....O", "O....O.O....O",
                "O....O.O....O", "..OOO...OOO..", ".............", "..OOO...OOO..",
                "O....O.O....O", "O....O.O....O", "O....O.O....O", ".............", "..OOO...OOO.."}); break;
            case "Pentadécathlon": dessiner(jeu, cx, cy, new String[]{"..O....O..", "OO.OOOO.OO", "..O....O.."}); break;
            case "Smiley": dessiner(jeu, cx, cy, new String[]{"OO...OO", "OO...OO", ".......", "O.....O", ".OOOOO."}); break;
        }
    }

    private static void dessiner(JeuDeLaVie jeu, int startX, int startY, String[] modele) {
        startX = startX - (modele[0].length() / 2);
        startY = startY - (modele.length / 2);
        
        for (int y = 0; y < modele.length; y++) {
            String ligne = modele[y];
            for (int x = 0; x < ligne.length(); x++) {
                if (ligne.charAt(x) == 'O') {
                    if (startX + x >= 0 && startX + x < jeu.getXMax() && startY + y >= 0 && startY + y < jeu.getYMax()) {
                        Cellule c = jeu.getGrilleXY(startX + x, startY + y);
                        if (c != null && !c.estVivante()) c.vit();
                    }
                }
            }
        }
    }
}