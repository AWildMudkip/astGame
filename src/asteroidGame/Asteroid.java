package asteroidGame;

import java.awt.*;

public class Asteroid extends Entity {
	int hitsLeft, numSplit;

	public Asteroid(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit) {
		super(x, y, 2 * Math.PI * Math.random(), radius);
		
		this.hitsLeft = hitsLeft;
		this.numSplit = numSplit;
		
		double	vel = minVelocity + Math.random() * (maxVelocity - minVelocity),
				dir = 2 * Math.PI * Math.random(); // Random direction.
		xVelocity = vel * Math.cos(dir);
		yVelocity = vel * Math.sin(dir);
	}
	
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
	}

	public void draw(Graphics g) {
		g.setColor(Color.gray); // set color for the asteroid
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
		
		return new Asteroid(x, y, radius / Math.sqrt(numSplit), minVelocity, maxVelocity, hitsLeft - 1, numSplit);
	}

	public int getHitsLeft() {
		/*	Used by AsteroidsGame to determine whether the asteroid should
			be split up into smaller asteroids or destroyed completely. */
		return hitsLeft;
	}

	public int getNumSplit() {
		return numSplit;
	}
}