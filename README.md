# Jeu de la Vie - Projet L3 Informatique (Design Patterns)

## Contexte

Ce projet a ete realise en L3 Informatique a l'universite dans le cadre d'un travail sur les design patterns.

L'application implemente une version evoluee du **Jeu de la Vie** de Conway, avec:

- une interface graphique Swing,
- une sortie console,
- plusieurs variantes de regles,
- des effets optionnels (decorateurs),
- des strategies d'initialisation,
- des motifs predefinis.

## Design Patterns utilises

Le projet met en oeuvre les patterns suivants:

- **State + Singleton**: gestion de l'etat des cellules (vivante/morte) via `CelluleEtat`, `CelluleEtatVivant`, `CelluleEtatMort`.
- **Visitor**: application des regles de simulation sur les cellules (`VisiteurClassique`, `VisiteurHighLife`, etc.).
- **Command**: calcul puis application differes des transitions d'etat (`CommandeVit`, `CommandeMeurt`).
- **Observer**: notification des vues/apercus (`JeuDeLaVieUI`, `JeuDeLaVieConsole`).
- **Decorator**: ajout d'effets de regles (radioactif, miracle, zombie) sans modifier les visiteurs de base.
- **Strategy**: differents modes d'initialisation (`InitAleatoire`, `InitDamier`, `InitLignes`).
- **Factory**: insertion de motifs predefinis via `FabriqueMotif`.

## Fonctionnalites principales

- Simulation du Jeu de la Vie sur grille redimensionnable.
- Selection de plusieurs regles:
  - Classique
  - HighLife
  - Labyrinthe
  - Seeds
  - Vie sans Mort
  - Amibe
- Activation d'effets additionnels:
  - Radioactif
  - Miracle
  - Zombie
- Repeuplement de la grille par strategie.
- Insertion de motifs (planeur, pulsar, canon a planeurs, etc.).
- Sauvegarde et chargement d'une partie.

## Arborescence

- `src/`: code source Java
- `src/cellule/`: cellule et etats
- `src/commande/`: commandes de transition
- `src/visiteur/`: regles de jeu (visiteurs)
- `src/decorateur/`: effets additionnels sur visiteurs
- `src/strategie/`: initialisation de grille
- `src/fabrique/`: motifs predefinis
- `src/observateur/`: interfaces Observable/Observateur
- `target/`: classes compilees

## Compilation et execution

Depuis la racine du projet:

make build

Lancer l'application:

make run

Nettoyer les classes compilees:

make clean