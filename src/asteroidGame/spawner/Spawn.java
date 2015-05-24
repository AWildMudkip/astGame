package asteroidGame.spawner;

import asteroidGame.entity.Ship;
import java.awt.*;

public class Spawn {
    private final double x, y, radius;
    private final Color color;
    
    public Spawn(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawOval((int)(this.x - this.radius + .5), (int)(this.y - this.radius + .5), (int)(2 * this.radius), (int)(2 * this.radius));
    }
    
    public boolean contact(Ship ship) {
        return Math.pow(this.radius + ship.getRadius(), 2) > Math.pow(ship.getX() - this.x, 2) + Math.pow(ship.getY() - y, 2) && ship.isActive();
    }
}
