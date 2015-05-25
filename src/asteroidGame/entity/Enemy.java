package asteroidGame.entity;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
	int hitsLeft;
	Color color;
	double velocity;

	public Enemy(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, Color color) {
		super(x, y, 2 * Math.PI * Math.random(), radius);
		
		this.hitsLeft = hitsLeft;
		this.color = color;
		
		this.velocity = minVelocity + Math.random() * (maxVelocity - minVelocity);
		xVelocity = velocity * Math.cos(angle);
		yVelocity = velocity * Math.sin(angle);
	}

	public int getHitsLeft() {
		/*	Used by AsteroidsGame to determine whether the asteroid should
			be split up into smaller asteroids or destroyed completely. */
		return hitsLeft;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public double getVelocity() {
		return this.velocity;
	}
	
	public void move() {}
	public void draw(Graphics g) {}
}