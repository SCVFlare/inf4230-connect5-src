/*
 * INF4230 - Intelligence artificielle
 * TP2 - Algorithme minimax avec élagage alpha-beta appliqué au jeu Connect5GUI
 *
 * (C) Éric Beaudry 2020.
 * UQAM - Département d'informatique
 */

package inf4230.connect5;

import inf4230.connect5.ia.BotInterne;

/**
 * Exemple de programme pour la correction automatisée du critère C.
 *
 * @author Éric Beaudry
 */
public class DemoCritereC {

    public static void main(String args[]){

        Joueur joueur = new BotInterne();

        // Test C1
        System.out.println("Test #1");
        Grille g = new Grille(5,5);
        for(int i=0;i<4;i++){
            g.set(i, 0, 1);
            g.set(i, 2, 2);
        }
        Position coup = joueur.getAction(g, 2000);
        System.out.println((coup.ligne==4 && coup.colonne==0) ? "Réussi": "Échoué");


        // Test C2
        g = new Grille(5, 7);
        for(int i=0;i<4;i++){
            g.set(0, 2+i, 1);
            g.set(4, 2+i, 2);
        }
        coup = joueur.getAction(g, 2000);
        System.out.println("Test #1");
        System.out.println((coup.ligne==0 && (coup.colonne==1||coup.colonne==6)) ? "Réussi": "Échoué");

        // D'autres tests comparables testant les colonnes et diagonales...
    }

}
