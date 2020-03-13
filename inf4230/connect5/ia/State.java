package inf4230.connect5.ia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import inf4230.connect5.Grille;
import inf4230.connect5.GrilleVerificateur;
import inf4230.connect5.Position;

public class State {
	private Grille g;
	private Action a;
	private int v;
	private int j;
	
	
	public State(Grille g, int j) {
		this.g = g;
		this.a = null;
		this.j=j;
		
	}
	
	private State(Grille g, Action a) {
		this.g = g;
		this.a = a;
		if (a.getJ()==1) {
			this.j=2;
		}
		else {
			this.j=1;
		}
		
	}
	
	@Override
	public String toString() {
		return "State [a=" + a + ", j=" + j + ", v=" +v+"]"+"\n"+g+"\n";
	}

	public Action getA() {
		return a;
	}
	public Grille getG() {
		return g;
	}
	public void setA(Action a) {
		this.a = a;
	}
	public int getV() {
		return v;
	}
	public void setV(int v) {
		this.v = v;
	}
	
	
	
	public boolean terminal() {
		boolean egal=true;
		int lignes =g.getData().length;
    	int col = g.getData()[0].length;
    	for(int i=0;i<lignes;i++) {
    		for(int j=0;j<col;j++) {
    			if(g.get(i, j)==0) {
    				egal=false;
    				break;
    			}
    		}
    	}
    	GrilleVerificateur verif = new GrilleVerificateur();
    	if(verif.getGagnant(g)!=0|| egal) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	public List<State> succ() {
		int lignes =g.getData().length;
    	int col = g.getData()[0].length;
    	ArrayList<State> successeurs = new ArrayList<State>();
    	for(int i=0;i<lignes;i++) {
    		for(int j=0;j<col;j++) {
    			if(g.get(i, j)==0) {
    				Grille gnew=g.clone();
    				gnew.set(i, j, this.j);
    				State snew= new State(gnew, new Action(this.j,new Position(i,j),this.getA()));
    				
    				
    				successeurs.add(snew);
    			}
    		}
    	}
    	return successeurs;
    }
	
	
	public int eval(int player) {
		int[] nbGroupes1= {0,0,0,0,0,0,0,0,0};
		int[] nbGroupes2= {0,0,0,0,0,0,0,0,0};
		int j1=player;
		int j2;
		if(j1==1) {
			j2=2;
		}
		else {
			j2=1;
		}
		Set<Set<Integer>> patterns1= new HashSet<Set<Integer>>();
		Set<Set<Integer>> patterns2= new HashSet<Set<Integer>>();
		int lignes =g.getData().length;
    	int col = g.getData()[0].length;
		for(int i=0;i<lignes;i++) {
    		for(int j=0;j<col;j++) {
    			Set<Integer> d1_1=new HashSet<Integer>();
    			Set<Integer> d1_2 =new HashSet<Integer>();
    			getDia1(i,j,d1_1,j1);
    			getDia1(i,j,d1_2,j2);
    			if(!patterns1.contains(d1_1) && !d1_1.isEmpty()) {
    				patterns1.add(d1_1);
    			}
    			if(!patterns2.contains(d1_2) && !d1_2.isEmpty()) {
    				patterns2.add(d1_2);
    			}
    			Set<Integer> d2_1 =new HashSet<Integer>();
    			Set<Integer> d2_2 =new HashSet<Integer>();
    			getDia2(i,j,d2_1,j1);
    			getDia2(i,j,d2_2,j2);
    			if(!patterns1.contains(d2_1) && !d2_1.isEmpty()) {
    				patterns1.add(d2_1);
    			}
    			if(!patterns2.contains(d2_2) && !d2_2.isEmpty()) {
    				patterns2.add(d2_2);
    			}
    			Set<Integer> l_1 =new HashSet<Integer>();
    			Set<Integer> l_2 =new HashSet<Integer>();
    			getLine(i,j,l_1,j1);
    			getLine(i,j,l_2,j2);
    			if(!patterns1.contains(l_1) && !l_1.isEmpty()) {
    				patterns1.add(l_1);
    			}
    			if(!patterns2.contains(l_2) && !l_2.isEmpty()) {
    				patterns2.add(l_2);
    			}
    			Set<Integer> c_1 =new HashSet<Integer>();
    			Set<Integer> c_2 =new HashSet<Integer>();
    			getCol(i,j,c_1,j1);
    			getCol(i,j,c_2,j2);
    			if(!patterns1.contains(c_1) && !c_1.isEmpty()) {
    				patterns1.add(c_1);
    			}
    			if(!patterns2.contains(c_2) && !c_2.isEmpty()) {
    				patterns2.add(c_2);
    			}
    		}
    	}
		for(Set<Integer> p1:patterns1) {
			int s = p1.size();
			nbGroupes1[s-1]++;
		}
		for(Set<Integer> p2:patterns2) {

			int s = p2.size();
			nbGroupes2[s-1]++;
		}
		int s1=Math.abs(nbGroupes1[0])-Math.abs(nbGroupes2[0]);
		int s2=Math.abs(nbGroupes1[1])-Math.abs(nbGroupes2[1]);
		int s3=Math.abs(nbGroupes1[2])-Math.abs(nbGroupes2[2]);
		int s4=Math.abs(nbGroupes1[3])-Math.abs(nbGroupes2[3]);
		int s5=Math.abs(nbGroupes1[4])-Math.abs(nbGroupes2[4]);
		int s6=Math.abs(nbGroupes1[5])-Math.abs(nbGroupes2[5]);
		int s7=Math.abs(nbGroupes1[6])-Math.abs(nbGroupes2[6]);
		int s8=Math.abs(nbGroupes1[7])-Math.abs(nbGroupes2[7]);
		int s9=Math.abs(nbGroupes1[8])-Math.abs(nbGroupes2[8]);
		int res=1*s1 + 10*s2 + 100*s3 + 1000*s4 + 10000*s5;
		/*System.out.println(" evaluating"+this+"\n"+this.g+res);
    	System.out.println(patterns1);
    	System.out.println(nbGroupes1);
    	System.out.println(patterns2);
    	System.out.println(nbGroupes2);*/
		return res;
	}
	
	private void getDia1(int l,int c,Set<Integer> vide,int j) {
    	int nbl =g.getData().length;
    	int nbc = g.getData()[0].length;
    	int tile =l*nbc+c;
    	int down=(l-1)*nbc+(c-1);
    	int up=(l+1)*nbc+(c+1);
        try {
        	int t=g.get(l, c);
        	if(t==j) {
        		vide.add(tile);
        		if(down>=0 && down<nbc*nbl && !vide.contains(down)) {
        			getDia1(l-1,c-1,vide,j);
            	}
            	if(up>=0 && up<nbc*nbl && !vide.contains(up)) {
            		getDia1(l+1,c+1,vide,j);
            	}
    		}
        }
        catch(Exception e){
        }
	}
	private void getDia2(int l,int c,Set<Integer> vide,int j) {
    	int nbl =g.getData().length;
    	int nbc = g.getData()[0].length;
    	int tile =l*nbc+c;
    	int down=(l+1)*nbc+(c-1);
    	int up=(l-1)*nbc+(c+1);
        try {
        	int t=g.get(l, c);
        	if(t==j) {
        		vide.add(tile);
        		if(down>=0 && down<nbc*nbl && !vide.contains(down)) {
        			getDia2(l+1,c-1,vide,j);
            	}
            	if(up>=0 && up<nbc*nbl && !vide.contains(up)) {
            		getDia2(l-1,c+1,vide,j);
            	}
    		}
        }
        catch(Exception e){
        }
	}
	private void getLine(int l,int c,Set<Integer> vide,int j) {
    	int nbl =g.getData().length;
    	int nbc = g.getData()[0].length;
    	int tile =l*nbc+c;
    	int right=l*nbc+(c+1);
    	int left=l*nbc+(c-1);
        try {
        	int t=g.get(l, c);
        	if(t==j) {
        		vide.add(tile);
        		if(right>=0 && right<nbc*nbl && !vide.contains(right)) {
        			getLine(l,c+1,vide,j);
            	}
            	if(left>=0 && left<nbc*nbl && !vide.contains(left)) {
            		getLine(l,c-1,vide,j);
            	}
    		}
        }
        catch(Exception e){
        }
	}
	private void getCol(int l,int c,Set<Integer> vide,int j) {
    	int nbl =g.getData().length;
    	int nbc = g.getData()[0].length;
    	int tile =l*nbc+c;
    	int up=(l-1)*nbc+(c);
    	int down=(l+1)*nbc+(c);
        try {
        	int t=g.get(l, c);
        	if(t==j) {
        		vide.add(tile);
        		if(up>=0 && up<nbc*nbl && !vide.contains(up)) {
        			getCol(l-1,c,vide,j);
            	}
            	if(down>=0 && down<nbc*nbl && !vide.contains(down)) {
            		getCol(l+1,c,vide,j);
            	}
    		}
        }
        catch(Exception e){
        }
	}
}
