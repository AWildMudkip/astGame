package asteroidGame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import sl.shapes.StarPolygon;

public class Virus extends Enemy {
    private int numProjectiles, angle;
	private final static int points = 400; // Static score for blowing one up.
	private final static int life = 5;
	private final static int innerRadius = 15, outerRadius = 20, spikes = 10;
	private final static int rotation = 5;
	
    public Virus(double x, double y, double minVelocity, double maxVelocity) {
		super(x, y, minVelocity, maxVelocity, life, Color.PINK, points);

		this.angle = 0;
		
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
		
		if (angle > 360)
			angle = 360 - angle;
		
		shape = new StarPolygon((int) x, (int) y, outerRadius, innerRadius, spikes, angle);
    }
	@Override
    public void draw(Graphics g)
    {
        g.setColor(color);
		g.drawPolygon((Polygon) shape);
    }
    public void virusExplode()
    {
        
    }   
}
