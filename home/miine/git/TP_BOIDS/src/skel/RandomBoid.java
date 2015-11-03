package skel;

import processing.core.*;

public class RandomBoid {
	private PApplet graphicalContext;
	private PVector pos;
	private PVector vel;
	private Float vitesseMax;
	private Float forceMax;
	private int borneMax = 5;
	private int borneMin = -5;
	private int borneMaxForce = 10;
	private int borneMinForce = -10;
	
	public RandomBoid(PApplet graphicalContext,int x,int y){
		this.graphicalContext = graphicalContext;
		this.vitesseMax = 2f;
		this.forceMax = 0.05f;
		this.pos = new PVector(x,y);
		this.vel = new PVector(
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin)
									);
		
		
	}
	
	public RandomBoid(PApplet graphicalContext,int x,int y,Float vitesseMax, Float forceMax){
		this.graphicalContext = graphicalContext;
		this.vitesseMax = vitesseMax;
		this.forceMax = forceMax;
		this.pos = new PVector(x,y);
		this.vel = new PVector(
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin),
				(int) (Math.random()*(borneMax-(borneMin))+borneMin)
									);
	}
	
	public PVector randomForce(){
		return  new PVector(
				(int) (Math.random()*(borneMaxForce-(borneMinForce))+borneMinForce),
				(int) (Math.random()*(borneMaxForce-(borneMinForce))+borneMinForce),
				(int) (Math.random()*(borneMaxForce-(borneMinForce))+borneMinForce));
	}
	
	public void updatePosition(){
		PVector forces = randomForce();
		this.pos.set(
				this.pos.x +( this.vel.x + forces.x),
				this.pos.y + (this.vel.y + forces.y),
				this.pos.z + (this.vel.z + forces.z));
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
	    graphicalContext.beginShape(PConstants.TRIANGLES);
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

		
	
	

}
