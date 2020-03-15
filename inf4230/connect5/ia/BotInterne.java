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
import inf4230.connect5.GrilleVerificateur;
import inf4230.connect5.Joueur;
import inf4230.connect5.Position;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;

public class BotInterne implements Joueur {
	private GrilleVerificateur gv= new GrilleVerificateur();
	private boolean searchCutoff = false;
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
    	/*ArrayList<Integer> casesvides = new ArrayList<Integer>();
        int nbcol = grille.getData()[0].length;
        for(int l=0;l<grille.getData().length;l++)
            for(int c=0;c<nbcol;c++) {
                if(grille.getData()[l][c]==0)
                    casesvides.add(l*nbcol+c);
            }
        int choix = random.nextInt(casesvides.size());
        choix = casesvides.get(choix);
        return new Position(choix / nbcol, choix % nbcol);*/
    	//long startTime = System.currentTimeMillis();
		//long stopTime = delais+startTime;
    	int j=joueur(grille);
    	//System.out.println("playing as:"+j);
    	State s = new State(grille,j);
    	State res=iterativeDeepeningSearch(s,delais,j);
    	//System.out.println(res);
    	//long start = System.currentTimeMillis();
       

    	/*
    	// VERSION Minimax classique
    	List<State> initialS=s.succ();
    	for(State s1: initialS) {
    		s1.setV(minimax(s1,1,false,inverseP(j)));
    	}
    	//System.out.println(initialS);
    	State res=initialS.get(0);
    	for(State s1: initialS) {
    		res=max(res,s1);
    	}
    	System.out.println(res);
    	//return v.getA().getP();
    	return res.getA().getP();
    	*/
    	
    	/* VERSION Minimax avec �lagage alpha beta
    	Action a = alphaBeta(s, inverseP(j));
    	System.out.println("First action : " + a);
    	while (a.getParent() != null) {
    		a = a.getParent();
    	}
    	//System.out.println(grille);
    	return a.getP();
    	*/
    	
    	// VERSION Minimax avec negamax
    	
    	//List<State> initialS=s.succ();
    	//for(State s1: initialS) {
    	//	s1.setV(iterativeDeepeningSearch(s1,delais,inverseP(j)));
    	//}
    	//System.out.println(initialS);
    	//State res=initialS.get(0);
    	//for(State s1: initialS) {
    	//	res=max(res,s1);
    	//}
    	//System.out.println(res);
    	//return v.getA().getP();
    	return res.getA().getP();
    }

    @Override
    public String getAuteurs() {
        return "Boyan BECHEV (BECB28049807)  et  Jules JEHANNO (JEHJ22129905)";
    }
    private int minimax(State s,int depth,boolean maxP, int player) {
    	if(depth==0 || s.terminal()) {
    		int res =s.eval(player); //s.eval();
    		return res;
    	}
    	if(maxP) {
    		Integer v=Integer.MIN_VALUE;
    		//State newS=s;
    		//newS.setV(Integer.MIN_VALUE);
    		List<State> succs = s.succ();
    		for(State suc: succs) {
        		v= Math.max(v,minimax(suc,depth-1,false,inverseP(player)));
        	}
    		return v;
    	}
    	else {
    		Integer v=Integer.MAX_VALUE;
    		//State newS=s;
    		//newS.setV(Integer.MAX_VALUE);
    		List<State> succs = s.succ();
    		for(State suc: succs) {
    			v= Math.min(v,minimax(suc,depth-1,true,inverseP(player)));
        	}
    		return v;
    	}
    }
    private State max(State s1,State s2) {
    	if(s1.getV()>=s2.getV()) {
    		return s1;
    	}
    	else {
    		return s2;
    	}
    }
    private State min(State s1,State s2) {
    	if(s1.getV()<=s2.getV()) {
    		return s1;
    	}
    	else {
    		return s2;
    	}
    }
    private int inverseP(int player) {
    	if (player == 1) {
    		return 2;
    	}
    	else return 1;
    }
    
    
    // AVEC ELAGAGE ALPHA BETA
    private Action alphaBeta(State s, int player) {
    	int v = maxValue(s,Integer.MIN_VALUE, Integer.MAX_VALUE, player, 0);
    	System.out.println("maxValue found : " + v);
    	
    	for (State succ : s.succ()) {
    		if (succ.getV() == v) {
    			return succ.getA();
    		}
    	}
    	
    	System.out.println("Case null");
    	
    	State max = (s.succ()).get(0);
    	for (State succ : s.succ()) {
    		if (succ.getV() > max.getV()) {
    			max = succ;
    		}
    	}
    	
    	return max.getA();
    }
    
    private int maxValue(State s, int alpha, int beta, int player, int depth) {
    	if (depth > 3 || s.terminal()) {
    		//System.out.println("State is terminal (max) : " + s.eval(player));
    		return s.eval(player);
    	}
    	int v = Integer.MIN_VALUE;
    	
    	for (State succ : s.succ()) {
    		v = Integer.max(v, minValue(succ, alpha, beta, inverseP(player), depth+1));
    		succ.setV(v);
    		if (v >= beta) {
    			//System.out.println("�lagage done");
    			return v;
    		}
    		alpha = Integer.max(alpha,v);
    	}
    	return v;
    }
    
    private int minValue(State s, int alpha, int beta, int player, int depth) {
    	if (depth > 3 || s.terminal()) {
    		//System.out.println("State is terminal (min) : " + s.eval(player));
    		return s.eval(player);
    	}
    	int v = Integer.MAX_VALUE;
    	
    	for (State succ : s.succ()) {
    		v = Integer.min(v, maxValue(succ, alpha, beta, inverseP(player), depth+1));
    		succ.setV(v);
    		if (v <= alpha) {
    			//System.out.println("�lagage done");
    			return v;
    		}
    		beta = Integer.min(beta,v);
    	}
    	return v;
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
		return score;
	}
    
    
    
    
    // NEGAMAX
    private State negamax(State s, int alpha, int beta, int player, int depth, long start, long limit) {
    	long current = System.currentTimeMillis();
		long elapsed = (current - start);
    	if (elapsed >= limit) {
			searchCutoff = true;
		}
    	if (depth ==0 || s.terminal()||searchCutoff) {
    		int res = s.eval(player);
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
	
    /*private Position minimax(State s) {
    	int v = maxvalue(s);
    	System.out.println("valueV : " + v);
    	for(State suc: s.succ()) {
    		if (suc.getUtility()==v) {
    			System.out.println("Position : " + suc.getA().getP());
    			return suc.getA().getP();
    		}
    	}
    	System.out.print("test");
    	return null;
    }
    
    private int maxvalue(State s) {
    	System.out.println(ns);
    	boolean terminal =s.terminal();
    	//System.out.println("get Gagnat: "+gv.getGagnant(s.getG()));
    	//System.out.println("Looking at maxvalue: "+s);
    	//System.out.println("terminnal?: "+s.terminal());
    	//System.out.println(s.getG());
    	if(terminal) {
    		ns--;
    		int res=s.eval();
    		//System.out.println("Terminal:"+s);
        	//System.out.println(s.getG());
    		//System.out.println(res);
    		return res;
    	}
    	Integer v = Integer.MIN_VALUE;
    	List<State> succs = s.succ();
    	ns+=succs.size();
    	for(State suc: succs) {
    		//System.out.println("MAX succesors of"+s);
        	//System.out.println(s.getG());
        	//System.out.println(s.succ());
    		v= Math.max(v,minvalue(suc));
    		ns--;
    	}
    	//System.out.println("maxValue : " + v);
    	return v;
    }
    private int minvalue(State s) {
    	System.out.println(ns);
    	boolean terminal =s.terminal();
    	//System.out.println("get Gagnat: "+gv.getGagnant(s.getG()));
    	//System.out.println("Looking at minvalue: "+s);
    	if(terminal) {
    		ns--;
    		int res=s.eval();
    		//System.out.println("Terminal:"+s);
        	//System.out.println(s.getG());
        	//System.out.println(res);
    		return res;
    	}
    	Integer v = Integer.MAX_VALUE;
    	List<State> succs = s.succ();
    	ns+=succs.size();
    	for(State suc: succs) {
    		//System.out.println("MIN succesors of"+s);
        	//System.out.println(s.getG());
        	//System.out.println(s.succ());
    		v= Math.min(v,maxvalue(suc));
    		ns--;
    	}
    	//System.out.println("minValue : " + v);
    	return v;
    	
    }*/
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
