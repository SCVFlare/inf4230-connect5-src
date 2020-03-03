/*
 * INF4230 - Intelligence artificielle
 * TP2 - Algorithme minimax avec élagage alpha-beta appliqué au jeu Connect5GUI
 *
 * (C) Éric Beaudry 2020.
 * UQAM - Département d'informatique
 */

package inf4230.connect5;

import java.util.EventListener;

/**
 *
 * @author Eric Beaudry
 */
public interface AfficheurGrilleListener extends EventListener {

    public void caseClicked(int ligne, int colonne);

}
