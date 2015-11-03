package skel;
import java.awt.Window;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import processing.core.*;

public class Flock {
	private PApplet graphicalContext;
	private ArrayList<Boid> listBoids = new ArrayList<Boid>();
	
	public Flock(int n,PApplet graphicalContext){
		
		this.graphicalContext = graphicalContext;
		for(int i = 0;i<n;i++){
			//this.addBoid((int) (Math.random()*(1200-(0))+0), (int) (Math.random()*(800-(0))+0));
			this.addBoid(600, 400);
			this.listBoids.get(i).setGroup(this);
		}
		
	}
	
	public void addBoid(int x, int y){
		this.listBoids.add(new Boid(graphicalContext, x, y));
	}
	
	public void run(){
		for(Boid e : listBoids){
			e.run();
		}
	}
	
	public ArrayList<Boid> neighbors(Boid b, double neighborDist){
		ArrayList<Boid> voisins = new ArrayList<Boid>() ;
		
		for (Boid boid : listBoids){
			
			if ((b.distance(boid)<neighborDist) && (boid != b))
				voisins.add(boid);
		}
		return voisins;
	}

}
