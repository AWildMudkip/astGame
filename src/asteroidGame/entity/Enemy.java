package asteroidGame.entity;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
	int hitsLeft, numSplit;
	Color color;

	public Enemy(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit, Color color) {
		super(x, y, 2 * Math.PI * Math.random(), radius);
		
		this.hitsLeft = hitsLeft;
		this.numSplit = numSplit;
		this.color = color;
		
		double	vel = minVelocity + Math.random() * (maxVelocity - minVelocity),
				dir = 2 * Math.PI * Math.random(); // Random direction.
		xVelocity = vel * Math.cos(dir);
		yVelocity = vel * Math.sin(dir);
	}

	public int getHitsLeft() {
		/*	Used by AsteroidsGame to determine whether the asteroid should
			be split up into smaller asteroids or destroyed completely. */
		return hitsLeft;
	}

	public int getNumSplit() {
		return numSplit;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void move() {}
	public void draw(Graphics g) {}
}