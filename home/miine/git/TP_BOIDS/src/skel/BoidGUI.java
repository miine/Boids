package skel;

import processing.core.*;

@SuppressWarnings("serial")
public class BoidGUI extends PApplet {

	private Flock f;

	public void setup() {
		this.size(1200, 800);
		this.f = new Flock(1, this);
		
	}

	public void draw() {
		this.background(50);
		this.f.run();
	}

	public void mousePressed() {
			this.f.addBoid(mouseX, mouseY);
	}
}