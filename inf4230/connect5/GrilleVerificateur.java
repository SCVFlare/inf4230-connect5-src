/*
 * INF4230 - Intelligence artificielle
 * TP2 - Algorithme minimax avec élagage alpha-beta appliqué au jeu Connect5GUI
 *
 * (C) Éric Beaudry 2020.
 * UQAM - Département d'informatique
 */

package inf4230.connect5;

public class GrilleVerificateur {

    public int getGagnant(Grille grille) {
        gagnant = precvaleur = 0; // reset status

        // horizontal
        for (int l = 0; l < grille.data.length; l++) {
            for (int c = 0; c < grille.data[0].length; c++) {
                verifier(grille.data[l][c]);
            }
            verifier(0);
        }

        // vertical
        for (int c = 0; c < grille.data[0].length; c++) {
            for (int l = 0; l < grille.data.length; l++) {
                verifier(grille.data[l][c]);
            }
            verifier(0);
        }

        // Diagonal \\\\\\\
        for (int c = -grille.data.length; c < grille.data[0].length; c++) {
            int c2 = c;
            int l = 0;
            if (c2 < 0) {
                l = -c2;
                c2 = 0;
            }
            for (; c2 < grille.data[0].length && l < grille.data.length; c2++, l++) {
                verifier(grille.data[l][c2]);
            }
            verifier(0);
        }

        // Diagonal //////
        for (int c = -grille.data.length; c < grille.data[0].length; c++) {
            int c2 = c;
            int l = grille.data.length - 1;
            if (c2 < 0) {
                l += c2;
                c2 = 0;
            }
            for (; c2 < grille.data[0].length && l >= 0; c2++, l--) {
                verifier(grille.data[l][c2]);
            }
            verifier(0);
        }

        return gagnant;
    }

    private void verifier(int valeur) {
        if (valeur == precvaleur) {
            compteur++;
        } else {
            if (precvaleur > 0 && (compteExact ? compteur == nombreGagnant : compteur >= nombreGagnant)) {
                gagnant = precvaleur;
            }
            compteur = 1;
            precvaleur = valeur;
        }
    }
    private int nombreGagnant = 5;
    private boolean compteExact = false;
    private int precvaleur = 0;
    private int compteur = 0;
    private int gagnant = 0;
   
}
