package asteroidGame.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import sl.shapes.StarPolygon;

public class Virus extends Enemy {
	private final int innerRadius, outerRadius, spikes;
	private final static double rotation = Math.PI / 100;
	
    public Virus(double x, double y, double minVelocity, double maxVelocity, int innerRadius, int outerRadius, int life, int spikes) {
		super(x, y, minVelocity, maxVelocity, 2 * Math.PI * Math.random(), life, Color.PINK, spikes * 40);
		
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.spikes = spikes;
		
		shape = new StarPolygon((int) x, (int) y, outerRadius, innerRadius, spikes, angle);
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
		
		if (angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;
		
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
		double a = 2 * Math.PI / spikes;
        for (int i = 0; i < spikes; i++) {
			shrapnels.add(new Shrapnel(x, y, velocity, velocity, a * i + angle, color));
		}
		return shrapnels;
    }   
}
