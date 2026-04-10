package jeuDeLaVie.src;

import jeuDeLaVie.src.decorateur.*;
import jeuDeLaVie.src.visiteur.*;
import jeuDeLaVie.src.observateurs.Observateur;
import jeuDeLaVie.src.cellule.Cellule;
import jeuDeLaVie.src.commande.*;
import jeuDeLaVie.src.fabrique.FabriqueMotif;
import jeuDeLaVie.src.strategie.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class JeuDeLaVieUI extends JFrame implements Observateur {
    
    private JeuDeLaVie jeu;
    private GrillePanel grillePanel;
    
    private Timer timer;
    private boolean enMarche = false;
    private int delai; 
    private Color couleurCellule = Color.BLACK;
    private int tailleCellule = 3;

    private double proba = 0.2;

    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        this.jeu = jeu;
        
        setTitle("Jeu de la Vie");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelInfo = new JLabel("Astuce : Cliquez ou glissez la souris pour dessiner des cellules | Molette pour zoomer");
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER); 
        labelInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(labelInfo, BorderLayout.NORTH); 


        grillePanel = new GrillePanel();
        grillePanel.setPreferredSize(new Dimension(jeu.getXMax() * tailleCellule, jeu.getYMax() * tailleCellule));
        JScrollPane scrollPane = new JScrollPane(grillePanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panneauDroite = new JPanel();
        panneauDroite.setLayout(new BoxLayout(panneauDroite, BoxLayout.Y_AXIS));
        panneauDroite.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Partie Jeu & Vitesse
        JSlider sliderVitesse = new JSlider(1, 99, 30);
        delai = 1000 / sliderVitesse.getValue(); 
        timer = new Timer(delai, e -> jeu.calculerGenerationSuivante());
        sliderVitesse.addChangeListener(e -> {
            delai = 1000 / sliderVitesse.getValue();
            timer.setDelay(delai);
        });

        JButton btnPlayPause = new JButton("Play");
        JButton btnAvancer = new JButton("Avancer");
        btnPlayPause.addActionListener(e -> {
            enMarche = !enMarche;
            if (enMarche) {
                timer.start();
                btnPlayPause.setText("Pause");
                btnAvancer.setEnabled(false);
            } else {
                timer.stop();
                btnPlayPause.setText("Play");
                btnAvancer.setEnabled(true);
            }
        });
        btnAvancer.addActionListener(e -> jeu.calculerGenerationSuivante());
        
        panneauDroite.add(creerSection("Temps", "Gérez la vitesse d'évolution de la grille.", btnPlayPause, btnAvancer, sliderVitesse));
        panneauDroite.add(Box.createVerticalStrut(10));

     

        // 2. Partie Regles du jeu
        String[] regles = {"Classique", "HighLife", "Labyrinthe", "Seeds", "Vie sans Mort", "Amibe"};
        JComboBox<String> comboRegles = new JComboBox<>(regles);
        
        JCheckBox checkRadioactif = new JCheckBox("5% de chance de mourir");
        JCheckBox checkMiracle = new JCheckBox("0.1% de chance de naître");
        JCheckBox checkZombie = new JCheckBox("5% de chance d'infecter une voisine");
        
        ActionListener updateRegles = e -> mettreAJourRegles(comboRegles, checkRadioactif, checkMiracle, checkZombie);
        comboRegles.addActionListener(updateRegles);
        checkRadioactif.addActionListener(updateRegles);
        checkMiracle.addActionListener(updateRegles);
        checkZombie.addActionListener(updateRegles);

        
        panneauDroite.add(creerSection("Régle", "Changez les régles de la survie.", comboRegles, checkRadioactif, checkMiracle, checkZombie));
        panneauDroite.add(Box.createVerticalStrut(10));


        

        // 3. Partie Motifs & Couleurs 
        JButton btnCouleur = new JButton("Changer Couleur");
        btnCouleur.addActionListener(e -> {
            Color nouvelleCouleur = JColorChooser.showDialog(this, "Choisir la couleur", couleurCellule);
            if (nouvelleCouleur != null) { couleurCellule = nouvelleCouleur; actualise(); }
        });

        String[] motifs = {"- Sélectionner un Motif -", "Planeur", "Clignotant", "Bloc", "Crapaud", "Vaisseau", "Canon à planeurs", "Le Gland", "Pulsar", "Pentadécathlon", "Smiley"};
        JComboBox<String> comboMotifs = new JComboBox<>(motifs);
        comboMotifs.addActionListener(e -> {
            if (comboMotifs.getSelectedIndex() != 0) {
                int cx = jeu.getXMax() / 2;
                int cy = jeu.getYMax() / 2;
                FabriqueMotif.inserer((String) comboMotifs.getSelectedItem(), jeu, cx, cy);
                comboMotifs.setSelectedIndex(0); 
                actualise();
            }
        });
        panneauDroite.add(creerSection("Outils Créatifs", "Colorisez ou insérez des structures (au centre de la grille).", btnCouleur, comboMotifs));
        panneauDroite.add(Box.createVerticalStrut(10));
        
        // 4. Partie Réinitialisation
        JButton btnVider = new JButton("Vider la grille");
        btnVider.addActionListener(e -> {
            for(int x=0; x<jeu.getXMax(); x++) 
                for(int y=0; y<jeu.getYMax(); y++) 
                    if(jeu.getGrilleXY(x,y).estVivante()) jeu.getGrilleXY(x,y).meurt();
            actualise();
        });

         String[] probabilites = {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9"};
        JComboBox<String> comboProbabilites = new JComboBox<>(probabilites);
        comboProbabilites.setSelectedItem(String.valueOf(proba));
        comboProbabilites.addActionListener(e -> {
            if (comboProbabilites.getSelectedIndex() != 0) {
                proba = Double.parseDouble((String) comboProbabilites.getSelectedItem());
            }
        });
        
        String[] strategies = {"Aléatoire", "Damier", "Lignes"};
        JComboBox<String> comboStrategie = new JComboBox<>(strategies);

        JButton btnAlea = new JButton("Repeupler");
        btnAlea.addActionListener(e -> { 
            String choixStrat = (String) comboStrategie.getSelectedItem();
            StrategieInitialisation strat = null;
            
            switch(choixStrat) {
                case "Aléatoire": strat = new InitAleatoire(); 
                                comboProbabilites.setEnabled(true);
                                break;
                case "Damier": strat = new InitDamier(); 
                                comboProbabilites.setEnabled(false);
                                break;
                case "Lignes": strat = new InitLignes(); 
                                comboProbabilites.setEnabled(false);
                                break;
            }
            
            if (strat != null) {
                strat.initialiser(jeu, proba);
            }
            actualise(); 
        });


        panneauDroite.add(creerSection("Génération", "Repeuplez la grille selon différentes choix.", btnVider, comboStrategie, comboProbabilites, btnAlea));
        panneauDroite.add(Box.createVerticalStrut(10));
        

        // 5. Partie Taille
        String[] tailles = {"100x100", "200x200", "300x300", "400x400", "500x500", "600x600", "700x700", "800x800", "900x900", "1000x1000"};
        JComboBox<String> comboTailles = new JComboBox<>(tailles);
        comboTailles.setSelectedItem(jeu.getXMax() + "x" + jeu.getYMax());
        comboTailles.addActionListener(e -> {
            String choix = (String) comboTailles.getSelectedItem();
            if (choix != null) {
                String[] dimensions = choix.split("x");
                jeu.redimensionner(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), proba);
                grillePanel.setPreferredSize(new Dimension(jeu.getXMax() * tailleCellule, jeu.getYMax() * tailleCellule));
                pack();
                setLocationRelativeTo(null);
            }
        });

        panneauDroite.add(creerSection("Dimensions", "Agrandissez ou réduisez la zone de jeu.", comboTailles));

        // 6. Partie Sauvegarde / Chargement
        JButton btnSave = new JButton("Sauvegarder");
        btnSave.addActionListener(e -> sauvegarderPartie());

        JButton btnLoad = new JButton("Charger");
        btnLoad.addActionListener(e -> chargerPartie());

        panneauDroite.add(creerSection("Fichiers", "Sauvegardez ou restaurez une partie.", btnSave, btnLoad));
        panneauDroite.add(Box.createVerticalStrut(10));

        JScrollPane scrollDroite = new JScrollPane(panneauDroite);
        scrollDroite.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDroite.setBorder(null);
        add(scrollDroite, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JPanel creerSection(String titre, String explication, JComponent... composants) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), titre));

        JLabel labelDesc = new JLabel("<html><div style='text-align: center; font-size: 10px; color: gray;'>" + explication + "</div></html>");
        labelDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelDesc);
        panel.add(Box.createVerticalStrut(8));


        for (JComponent comp : composants) {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            if (!(comp instanceof JCheckBox)) {
                comp.setPreferredSize(new Dimension(190, comp.getPreferredSize().height));
            }
            
            wrapper.add(comp);
            panel.add(wrapper);
        }

        return panel;
    }

    
    private void dessinerDepuisTexte(int startX, int startY, String[] modele) {
        startX = startX - (modele[0].length() / 2);
        startY = startY - (modele.length / 2);
        for (int y = 0; y < modele.length; y++) {
            String ligne = modele[y];
            for (int x = 0; x < ligne.length(); x++) {
                if (ligne.charAt(x) == 'O') {
                    Cellule c = jeu.getGrilleXY(startX + x, startY + y);
                    if (c != null && !c.estVivante()) c.vit();
                }
            }
        }
    }

    private void sauvegarderPartie() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder la grille");
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fichier = fileChooser.getSelectedFile();
            
            try (PrintWriter out = new PrintWriter(fichier)) {
                out.println(jeu.getXMax() + " " + jeu.getYMax());
                
                for (int y = 0; y < jeu.getYMax(); y++) {
                    for (int x = 0; x < jeu.getXMax(); x++) {
                        out.print(jeu.getGrilleXY(x, y).estVivante() ? "O" : ".");
                    }
                    out.println();
                }
                JOptionPane.showMessageDialog(this, "Partie sauvegardée avec succès !");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur de sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chargerPartie() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Charger une grille");
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fichier = fileChooser.getSelectedFile();
            
            try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
                String premiereLigne = br.readLine();
                if (premiereLigne != null) {
                    String[] dimensions = premiereLigne.split(" ");
                    int newX = Integer.parseInt(dimensions[0]);
                    int newY = Integer.parseInt(dimensions[1]);

                    String[] modele = new String[newY];
                    for (int y = 0; y < newY; y++) {
                        String ligne = br.readLine();
                        modele[y] = (ligne != null) ? ligne : "";
                    }
                    
                    jeu.redimensionner(newX, newY, 0);
                    dessinerDepuisTexte(newX / 2, newY / 2, modele);
                    
                    grillePanel.setPreferredSize(new Dimension(newX * tailleCellule, newY * tailleCellule));
                    pack();
                    setLocationRelativeTo(null);
                    actualise();
                    JOptionPane.showMessageDialog(this, "Partie chargée avec succès !");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fichier invalide ou corrompu : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actualise() {
        grillePanel.repaint(); 
    }

    private void mettreAJourRegles(JComboBox<String> combo, JCheckBox checkRadioactif, JCheckBox checkMiracle, JCheckBox checkZombie) {
        String choix = (String) combo.getSelectedItem();
        Visiteur visiteurDeBase = null;

        switch (choix) {
            case "Classique": visiteurDeBase = new VisiteurClassique(jeu); break;
            case "HighLife": visiteurDeBase = new VisiteurHighLife(jeu); break;
            case "Labyrinthe": visiteurDeBase = new VisiteurLabyrinthe(jeu); break;
            case "Seeds": visiteurDeBase = new VisiteurSeeds(jeu); break;
            case "Vie sans Mort": visiteurDeBase = new VisiteurVieSansMort(jeu); break;
            case "Amibe": visiteurDeBase = new VisiteurAmibe(jeu); break;
        }

        if (checkRadioactif.isSelected()) {
            visiteurDeBase = new VisiteurRadioactif(visiteurDeBase);
        }
        if (checkMiracle.isSelected()) {
            visiteurDeBase = new VisiteurMiracle(visiteurDeBase);
        }
        if (checkZombie.isSelected()) {
            visiteurDeBase = new VisiteurZombie(visiteurDeBase);
        }

        jeu.setVisiteur(visiteurDeBase);
    }

    private class GrillePanel extends JPanel {
        public GrillePanel() {
            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) { interagirAvecCellule(e.getX(), e.getY(), true); }
                @Override
                public void mouseDragged(MouseEvent e) { interagirAvecCellule(e.getX(), e.getY(), false); }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);

            addMouseWheelListener(e -> {
                if (e.getWheelRotation() < 0) tailleCellule++;
                else if (tailleCellule > 1) tailleCellule--;
                setPreferredSize(new Dimension(jeu.getXMax() * tailleCellule, jeu.getYMax() * tailleCellule));
                revalidate(); 
                repaint();
            });
        }

        private void interagirAvecCellule(int mouseX, int mouseY, boolean inverser) {
            int x = mouseX / tailleCellule;
            int y = mouseY / tailleCellule;
            Cellule c = jeu.getGrilleXY(x, y);
            if (c != null) {
                if (inverser) { if (c.estVivante()) c.meurt(); else c.vit(); } 
                else { if (!c.estVivante()) c.vit(); }
                repaint();
            }
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(couleurCellule); 
            for (int x = 0; x < jeu.getXMax(); x++) {
                for (int y = 0; y < jeu.getYMax(); y++) {
                    if (jeu.getGrilleXY(x, y).estVivante()) {
                        g.fillRect(x * tailleCellule, y * tailleCellule, tailleCellule, tailleCellule);
                    }
                }
            }
        }
    }
}