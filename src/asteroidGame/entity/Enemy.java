package asteroidGame.entity;

import java.awt.*;

public class Enemy extends Entity {
	protected int hitsLeft, score;
	protected Color color;
	protected double velocity;
	
	public Enemy(double x, double y, double minVelocity, double maxVelocity, int hitsLeft, Color color, int score) {
		super(x, y, 2 * Math.PI * Math.random());
		
		this.hitsLeft = hitsLeft;
		this.color = color;
		this.score = score;
		
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
	
	public int getScore() {
		return this.score;
	}
	
	public void move() {}
	public void draw(Graphics g) {}
}