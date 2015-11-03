package skel;

import processing.core.*;

@SuppressWarnings("serial")
public class OneBoidGUI extends PApplet {

	private RandomBoid b;

	public void setup() {
		this.size(1200, 800);
		b = new RandomBoid(this, 600, 400);
	}

	public void draw() {
		this.background(50);
		this.b.run();
	}

}