package asteroidGame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Asteroid extends Enemy {
	private final int numSplit;
	private final double radius;
	private final static int points = 100; // Static score for blowing one up.

	public Asteroid(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit, Color color) {
		super(x, y, minVelocity, maxVelocity, 2 * Math.PI * Math.random(), hitsLeft, color, points);
		
		this.numSplit = numSplit;
		this.radius = radius;
		
		shape = new Ellipse2D.Double((int)(x - radius + .5), (int)(y - radius + .5), (int)(2 * radius), (int)(2 * radius));
	}
	
	@Override
	public void move() {
		x += xVelocity; //move the asteroid
		y += yVelocity;
		//wrap around code allowing the asteroid to go off the screen
		//to a distance equal to its radius before entering on the
		//other side. Otherwise, it would go halfway off the sceen,
		//then disappear and reappear halfway on the other side
		//of the screen.
		if (x < 0 - radius)
			x += scrnWidth + 2 * radius;
		else if (x > scrnWidth + radius)
			x -= scrnWidth + 2 * radius;
		if (y < 0 - radius)
			y += scrnHeight + 2 * radius;
		else if (y > scrnHeight + radius)
			y -= scrnHeight + 2 * radius;
		
		shape = new Ellipse2D.Double((int)(x - radius + .5), (int)(y - radius + .5), (int)(2 * radius), (int)(2 * radius));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color); // set color for the asteroid
		// draw the asteroid centered at (x,y)
		g.fillOval((int)(x - radius + .5), (int)(y - radius + .5), (int)(2 * radius), (int)(2 * radius));
	}

	public Asteroid createSplitAsteroid(double minVelocity, double maxVelocity) {
		/*	When this asteroid gets hit by a shot, this method is called
			numSplit times by AsteroidsGame to create numSplit smaller
			asteroids. Dividing the radius by sqrt(numSplit) makes the
			sum of the areas taken up by the smaller asteroids equal to
			the area of this asteroid. Each smaller asteroid has one
			less hit left before being completely destroyed. */
		
		return new Asteroid(x, y, radius / Math.sqrt(numSplit), minVelocity, maxVelocity, hitsLeft - 1, numSplit, color);
	}

	public static Color randomColor() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		Color color;
		
		switch (randomNum) {
			case 1: color = Color.RED;
					break;
			case 2: color = Color.GREEN;
					break;
			case 3: color = Color.BLUE;
					break;
			default: color = Color.GRAY;
					break;
		}
		
		return color;
	}
	
	public int getNumSplit() {
		return this.numSplit;
	}
	
	public final void playSound() {
		asteroidSound.playOnce();
	}
}
