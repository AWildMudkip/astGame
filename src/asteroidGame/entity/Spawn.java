package asteroidGame.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Spawn extends Entity {
    private final Color color;
    
    public Spawn(double x, double y, double radius, Color color) {
		super(x, y, 0, radius); // Look, no angle!
        this.color = color;
		shape = new Ellipse2D.Double((int)(this.x - this.radius + .5), (int)(this.y - this.radius + .5), (int)(2 * this.radius), (int)(2 * this.radius));
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawOval((int)(this.x - this.radius + .5), (int)(this.y - this.radius + .5), (int)(2 * this.radius), (int)(2 * this.radius));
    }
	
	public Color getColor() {
		// Return color, be happy.
		return this.color;
	}
}
