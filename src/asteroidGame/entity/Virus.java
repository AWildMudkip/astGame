package asteroidGame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import sl.shapes.StarPolygon;

public class Virus extends Enemy {
    private final static int numProjectiles = 10; // Match spikes.
	private final static int points = 400; // Static score for blowing one up.
	private final static int life = 5;
	private final static int innerRadius = 15, outerRadius = 20, spikes = 10;
	private final static int rotation = 5;
	
    public Virus(double x, double y, double minVelocity, double maxVelocity) {
		super(x, y, minVelocity, maxVelocity, 2 * Math.PI * Math.random(), life, Color.PINK, points);
		
		shape = new StarPolygon((int) x, (int) y, outerRadius, innerRadius, spikes, 0);
    }
	
	@Override
    public void move()
    {
		x += xVelocity;
		y += yVelocity;
		angle += rotation;

		if (x < 0 - outerRadius)
			x += scrnWidth + 2 * outerRadius;
		else if (x > scrnWidth + outerRadius)
			x -= scrnWidth + 2 * outerRadius;
		
		if (y < 0 - outerRadius)
			y += scrnHeight + 2 * outerRadius;
		else if (y > scrnHeight + outerRadius)
			y -= scrnHeight + 2 * outerRadius;
		
		if (angle >= 360)
			angle -= 360;
		
		shape = new StarPolygon((int) x, (int) y, outerRadius, innerRadius, spikes, angle);
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
	
    public ArrayList<Shrapnel> virusShrapnels()
    {
		ArrayList<Shrapnel> shrapnels = new ArrayList<>();
		double a = 360 / numProjectiles;
        for (int i = 0; i < numProjectiles; i++) {
			shrapnels.add(new Shrapnel(x, y, velocity, velocity, i * a, color));
		}
		return shrapnels;
    }   
}
