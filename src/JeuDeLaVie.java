package jeuDeLaVie.src;

import jeuDeLaVie.src.cellule.*;
import jeuDeLaVie.src.observateurs.Observable;
import jeuDeLaVie.src.observateurs.Observateur;
import jeuDeLaVie.src.commande.*;
import jeuDeLaVie.src.visiteur.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JeuDeLaVie implements Observable{
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    private List<Observateur> observateurs;

    private List<Commande> commandes;

    private Visiteur visiteur;

    public JeuDeLaVie(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.grille = new Cellule[xMax][yMax];
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.visiteur = new VisiteurClassique(this);
        initialiseGrille(0.2);
    }

    public void setVisiteur(Visiteur nouveauVisiteur) {
        this.visiteur = nouveauVisiteur;
    }

    public void distribueVisiteur() {
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                grille[x][y].accepte(visiteur);
            }
        }
    }

    public void ajouteCommande(Commande c) {
        commandes.add(c);
    }

    public void executeCommandes() {
        for (Commande c : commandes) {
            c.executer();
        }
        commandes.clear();
    }

    public int getXMax() { return xMax; }
    public int getYMax() { return yMax; }

    @Override
    public void attacheObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void detacheObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifieObservateurs() {
        for (Observateur o : observateurs) {
            o.actualise();
        }
    }


    public void calculerGenerationSuivante() {
        distribueVisiteur(); 
        executeCommandes();  
        notifieObservateurs(); 
    }

    public void initialiseGrille(double proba) {
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                

                CelluleEtat etatInitial;
                if (Math.random() < proba) {
                    etatInitial = CelluleEtatVivant.getInstance();
                } else {
                    etatInitial = CelluleEtatMort.getInstance();
                }
                
                grille[i][j] = new Cellule(i, j, etatInitial);
            }
        }
    }

    public Cellule getGrilleXY(int x, int y) {  
        if (x >= 0 && x < xMax && y >= 0 && y < yMax) {
            return grille[x][y];
        }
        
        return null; 
    }

    public void redimensionner(int nouveauX, int nouveauY, double proba) {
        this.xMax = nouveauX;
        this.yMax = nouveauY;
        this.grille = new Cellule[xMax][yMax];
        
        initialiseGrille(proba);        
        notifieObservateurs(); 
    }

    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie(300, 300);
        JeuDeLaVieUI ui = new JeuDeLaVieUI(jeu);
        JeuDeLaVieConsole console = new JeuDeLaVieConsole(jeu);

        jeu.attacheObservateur(ui);
        jeu.attacheObservateur(console);

        }
}