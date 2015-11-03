package skel;
import java.util.ArrayList;

import processing.core.*;

public class RandomFlock {
	private PApplet graphicalContext;
	private ArrayList<RandomBoid> boids;
	
	public RandomFlock(int n,PApplet graphicalContext){
		boids = new ArrayList<RandomBoid>(n);
		this.graphicalContext = graphicalContext;
		
	}
	
	public void addBoid(int x, int y){
		this.boids.add(new RandomBoid(graphicalContext, x, y));
	}
	
	public void run(){
		for(RandomBoid e : boids){
			e.run();
		}
	}

}
