package skel;

import java.util.ArrayList;
import java.util.Vector;

import processing.core.*;

public class Boid {
	private PApplet graphicalContext;
	private Flock myFlock;
	private PVector pos;
	private PVector vel;
	private Float vitesseMax;
	private Float forceMax;
	private int borneMax = 5;
	private int borneMin = -5;
	private int borneMaxForce = 10;
	private int borneMinForce = -10;
	
	public Boid(PApplet graphicalContext,int x,int y){
		this.graphicalContext = graphicalContext;
		this.vitesseMax = 3f;
		this.forceMax = 0.5f;
		this.pos = new PVector(x,y);
		this.vel = new PVector(
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin)
									);
			this.myFlock = new Flock(0, graphicalContext);
		
	}
	
	public void setGroup (Flock f){
		this.myFlock=f ; 
	}
	
	public Boid(PApplet graphicalContext,int x,int y,Float vitesseMax, Float forceMax){
		this.graphicalContext = graphicalContext;
		this.vitesseMax = vitesseMax;
		this.forceMax = forceMax;
		this.pos = new PVector(x,y);
		this.vel = new PVector(
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				0.0f
									);
		this.myFlock = new Flock(0, graphicalContext);
	}
	
	public PVector randomForce(){
		return  new PVector(
				(int) (Math.random()*(borneMaxForce-(borneMinForce))+borneMinForce),
				(int) (Math.random()*(borneMaxForce-(borneMinForce))+borneMinForce));
	}
	
	public void updatePosition(){
		PVector forces = new PVector(0,0);
		forces.add(align());
		forces.add(cohesion());
		PVector tempsep = new PVector(0,0);
		tempsep.add(this.separate());
		tempsep.mult(3/2);
		
		forces.add(tempsep);
		
//		this.pos.set(
//				this.pos.x + (this.vel.x + forces.x),
//				this.pos.y + (this.vel.y + forces.y),
//				0
//				);
		this.vel.add(forces);
		//this.vel.limit(this.vitesseMax);
		this.pos.add(this.vel);
		

	}
	
	public void run() {
	    this.updatePosition();
	    this.borders();
	    this.render();
	}

	public void render() {
	    // Draw a triangle rotated in the direction of velocity
	    float r = (float) 2.0;
	    float theta = this.vel.heading2D() + PConstants.PI / 2;
	    graphicalContext.fill(200, 100);
	    graphicalContext.stroke(255);
	    graphicalContext.pushMatrix();
	    graphicalContext.translate(this.pos.x, this.pos.y);
	    graphicalContext.rotate(theta);
	    graphicalContext.beginShape(PConstants.TRIANGLE_FAN);
	    graphicalContext.vertex(0, -r * 2);
	    graphicalContext.vertex(-r, r * 2);
	    graphicalContext.vertex(r, r * 2);
	    graphicalContext.endShape();
	    graphicalContext.popMatrix();
	    
	}

	public void borders() {
	    float r = (float) 2.0;
	    if (this.pos.x < -r) {
		this.pos.x = graphicalContext.width + r;
	    }
	    
	    if (this.pos.y < -r) {
		this.pos.y = graphicalContext.height + r;
	    }
	    
	    if (this.pos.x > graphicalContext.width + r) {
		this.pos.x = -r;
	    }
	    
	    if (this.pos.y > graphicalContext.height + r) {
		this.pos.y = -r;
	    }
	}
	
	public double distance(Boid b){
		return PVector.dist(this.pos, b.pos);
		
	}
	
	public PVector steer(PVector target, boolean slowDown) {
	    PVector desired = PVector.sub(target, this.pos);
	    if (desired.mag() <= 0) {
		return new PVector(0, 0);
	    }
	    
	    desired.normalize();
	    if (slowDown && desired.mag() < 100.0) {
		desired.mult((float) (this.vitesseMax * (desired.mag() / 100.0)));
	    } else {
		desired.mult(this.vitesseMax);
	    }
	    
	    PVector steeringVector = PVector.sub(desired, this.vel);
	    steeringVector.limit((float) this.forceMax);
	    return steeringVector;
	}
	
	public PVector align(){
		PVector fAlignement;
		ArrayList<Boid> voisinage = this.myFlock.neighbors(this, 25);
		if (voisinage.size()==0) return new PVector (0,0);
		
		
		PVector tmp = new PVector(0,0);

		for(Boid b : voisinage){
			tmp.add(b.vel);
			
		}
		fAlignement = new PVector(0,0);
		tmp.div(voisinage.size());
		tmp.normalize();
		tmp.mult(vitesseMax);
		tmp.sub(vel);
		tmp.limit(forceMax);
		
		return tmp;
		
	}
	
	public PVector separate() {
		ArrayList<Boid> voisinage = this.myFlock.neighbors(this, 20);
		
		if (voisinage.size()==0) return new PVector(0,0);
		
		PVector fSeparate = new PVector(0,0);
		
		
		
		for (Boid boid : voisinage) {
			PVector fTemp = PVector.sub(this.pos, boid.pos);
			
			if(this.distance(boid)!=0){
			fTemp.div((float)this.distance(boid));
			}
			fSeparate.add(fTemp); 
		}
		fSeparate.div(voisinage.size());
//		fSeparate.normalize();
//		fSeparate.mult(this.vitesseMax);
//		fSeparate.sub(this.vel);
//		fSeparate.limit(this.forceMax);
		return fSeparate;
	}
	
	public PVector cohesion() {
		ArrayList<Boid> voisinage =this.myFlock.neighbors(this, 25);
		if ( voisinage.size()==0) return this.steer(new PVector(0,0), false);
		PVector fCohesion = new PVector(0, 0);
		
		for (Boid boid : voisinage) {
			fCohesion.add(boid.pos);
		}
		
			fCohesion.div(voisinage.size());
			
		return steer(fCohesion, true);
	}
	

		
	
	

}
