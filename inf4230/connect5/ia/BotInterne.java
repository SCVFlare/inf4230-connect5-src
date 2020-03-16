package inf4230.connect5.ia;
/*
 * Point de départ pour un Bot écrit en Java.
 *
 * Vous pouvez ajouter d'autres classes sous le package inf4230.connect5.ia.
 *
 * Boyan BECHEV   (BECB28049807)
 * Jules JEHANNO  (JEHJ22129905)
 */

import java.util.List;

import inf4230.connect5.Grille;

import inf4230.connect5.Joueur;
import inf4230.connect5.Position;


public class BotInterne implements Joueur {
	private boolean searchCutoff = false;
	private int j=0;
    //private final Random random = new Random();

    /**
     * Voici la fonction à modifier.
     * Évidemment, vous pouvez ajouter d'autres fonctions dans BotInterne.
     * Vous pouvez aussi ajouter d'autres classes, mais elles doivent être
     * ajoutées dans le package inf4230.connect5.ia.
     * Vous ne pouvez pas modifier les autres fichiers dans les autres packages,
     * car ils seront écrasés.
     * 
     * @param grille Grille reçue (état courrant).
     * @param delais Délais de rélexion maximum (en secondes).
     * @return Retourne la meilleur meilleure actions.
     */
    @Override
    public Position getAction(Grille grille, int delais) {
    	
    	if(j==0) {
    		j=joueur(grille);
    	}
    	
    	System.out.println("playing as:"+j);
    	State s = new State(grille,j);
    	State res=iterativeDeepeningSearch(s,delais,j);
    	
    	return res.getA().getP();
    }

    @Override
    public String getAuteurs() {
        return "Boyan BECHEV (BECB28049807)  et  Jules JEHANNO (JEHJ22129905)";
    }
   
    private int inverseP(int player) {
    	if (player == 1) {
    		return 2;
    	}
    	else return 1;
    }
    
    
   
    //Iterative deepening
    private State iterativeDeepeningSearch(State state, long timeLimit,int player) {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeLimit;
		int depth = 1;
		State score = state;
		searchCutoff = false;
		while (true) {
			long currentTime = System.currentTimeMillis();
			if (currentTime >= endTime) {
				break;
			}
			State searchResult = negamax(state, Integer.MIN_VALUE, Integer.MAX_VALUE,player, depth, currentTime, endTime - currentTime);

			if (!searchCutoff) {	
				score=searchResult;
			}
			
			depth++;
		}
		//System.out.println(score);
		//System.out.println("res = " + score.getV() + " / pos = " + score.getA().getP() + " / patterns = " + score.getPaterns());
		return score;
	}
    
    
    
    
    // NEGAMAX
    private State negamax(State s, int alpha, int beta, int player, int depth, long start, long limit) {
    	long current = System.currentTimeMillis();
		long elapsed = (current - start);
		//System.out.println("looking at"+s+"for player"+player+"depth"+depth);
    	if (elapsed >= limit) {
			searchCutoff = true;
		}
    	if (depth ==0 || s.terminal()||searchCutoff) {
    		
    		int res = s.eval(player);
    		//System.out.println("and is terminal"+res+"because"+s.terminal()+searchCutoff);
    		s.setV(res);
    		return s;
    	}
    	else {
    		int best = Integer.MIN_VALUE;
    		State bestS=s;
    		bestS.setV(best);
    		State v;
    		List<State> succs = s.succ();
    		
    		for (State succ : succs) {
    			v = minus(negamax(succ, -beta, -alpha, inverseP(player), depth-1,start,limit));
    			if (v.getV() > best) {
    				
    				best = v.getV();
    				bestS=v;
    				if (best > alpha) {
    					alpha = best;
    					if (alpha >= beta) {
    						return bestS;
    					}
    				}
    			}
    		}
    		return bestS;
    	}
    }
    public State minus(State s) {
		State newS=s;
		newS.setV(-s.getV());
		return newS;
	}
	
    
    private int joueur(Grille g) {
    	int lignes =g.getData().length;
    	int col = g.getData()[0].length;
    	int nb1=0;
    	int nb2=0;
    	for(int i=0;i<lignes;i++) {
    		for(int j=0;j<col;j++) {
    			if(g.get(i, j)==1) {
    				nb1++;
    			}
    			if(g.get(i, j)==2) {
    				nb2++;
    			}
    		}
    	}
    	if(nb1>nb2) {
    		return 2;
    	}
    	else {
    		return 1;
    	}
    }
    
    
}
