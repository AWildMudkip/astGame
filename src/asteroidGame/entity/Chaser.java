package asteroidGame.entity;

import java.awt.Color;

public class Chaser extends Enemy {
	public Chaser(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit, Color color) {
		super(x, y, radius, minVelocity, maxVelocity, hitsLeft, numSplit, color);
	}
	
	public void move(Entity ship) {
		// How the Chaser moves. You can call ship coordinates here.
	}
}
