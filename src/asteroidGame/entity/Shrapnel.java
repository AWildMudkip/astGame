package asteroidGame.entity;

import asteroidGame.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import sl.shapes.RegularPolygon;

public class Shrapnel extends Enemy {

	double lifeTime = 3 * World.fps; // Shrapnels last for 3 seconds.
	static final int points = 10;
	static final int radius = 5;
	
	public Shrapnel(double x, double y, double minVelocity, double maxVelocity, double angle, Color color) {
		super(x, y, minVelocity, maxVelocity, angle, 1, color, points);
		
		shape = new RegularPolygon((int) x, (int) y, radius, 3, angle);
	}

	@Override
    public void move()
    {
		lifeTime --;
		if (lifeTime == 0) {
			this.remove();
		}
		
		x += xVelocity;
		y += yVelocity;

		if (x < 0 - radius)
			x += scrnWidth + 2 * radius;
		else if (x > scrnWidth + radius)
			x -= scrnWidth + 2 * radius;
		
		if (y < 0 - radius)
			y += scrnHeight + 2 * radius;
		else if (y > scrnHeight + radius)
			y -= scrnHeight + 2 * radius;

		shape = new RegularPolygon((int) x, (int) y, radius, 3, angle);
    }
	
	@Override
    public void draw(Graphics g)
    {
        g.setColor(color);
		g.drawPolygon((Polygon) shape);
    }
	
	public void hit() {
		hitsLeft --;
	}
}
