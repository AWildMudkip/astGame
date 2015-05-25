package asteroidGame.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Chaser extends Enemy {
	public Chaser(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft) {
		super(x, y, radius, minVelocity, maxVelocity, hitsLeft, Color.GRAY);
	}
	
	public void move(Entity ship) {
		// How the Chaser moves.
		double enemy_x = ship.getX();
		double enemy_y = ship.getY();
		
		if (enemy_x > this.getX())
			x += velocity;
		else
			x -= velocity;
		
		if (enemy_y > this.getY())
			y += velocity;
		else
			y -= velocity;
	}
	
	public void draw(Graphics g) {
		g.setColor(color); // set color for the asteroid
		// draw the asteroid centered at (x,y)
		g.fillOval((int)(x - radius + .5), (int)(y - radius + .5), (int)(2 * radius), (int)(2 * radius));
	}
	
	public void hit() {
		hitsLeft -= 1;
		
		if (hitsLeft == 0)
			this.remove();
	}
}
