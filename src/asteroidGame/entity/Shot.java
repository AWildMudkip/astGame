package asteroidGame.entity;

import java.awt.*;

public class Shot extends Entity {
	final double shotSpeed = 12;
	int lifeLeft;

	public Shot(double x, double y, double angle, double shipXVel, double shipYVel, int lifeLeft) {
		super(x, y, angle, 0); // Thanks for the comment regarding radius = 0!
		
		/*	Add the velocity of the ship to the velocity the shot velocity
			(so the shot's velocity is relative to the ship's) */
		xVelocity = shotSpeed * Math.cos(angle) + shipXVel;
		yVelocity = shotSpeed * Math.sin(angle) + shipYVel;
		
		/*	The number of frames the shot will last for before
			disappearing if it doesn't hit anything */
		this.lifeLeft = lifeLeft;
	}

	@Override
	public void move() {
		lifeLeft--; // used to make shot disappear if it goes too long without hitting anything.
		
		// Kill the shot if it is dead lol.
		if (lifeLeft == 0)
			remove();
		
		x += xVelocity; // move the shot
		y += yVelocity;
		if (x < 0) // wrap the shot around to the opposite side of the
			x += scrnWidth; // screen if needed
		else if (x > scrnWidth)
			x -= scrnWidth;
		if (y < 0)
			y += scrnHeight;
		else if (y > scrnHeight)
			y -= scrnHeight;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED); //set shot color
		//with integer coordinates (.5 added to x-1 and y-1 for rounding)
		g.fillRect((int)(x), (int)(y), 3, 3);
	}
}