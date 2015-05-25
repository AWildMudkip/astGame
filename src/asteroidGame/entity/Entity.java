package asteroidGame.entity;

import java.awt.*;
import java.awt.geom.Area;
import asteroidGame.World;

public abstract class Entity {
	protected double x, y, angle, xVelocity, yVelocity, radius;
	private boolean remove;
	protected Shape shape;
	
	protected final int scrnWidth = World.scrnWidth;
	protected final int scrnHeight = World.scrnHeight;
	
	public Entity(double x, double y, double angle, double radius) {
		// This is the generic class shared across entities.
		
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.radius = radius;
		this.remove = false;
	}
	
	public boolean collision(Entity entity) {
		// Detects if any entity is in collision with any other entity.
		Area s1 = new Area(this.shape);
		Area s2 = new Area(entity.shape);	
		
		// Magic
		s1.intersect(s2);
		
		return !s1.isEmpty();
	}
	
	public void remove() {
		this.remove = true;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public double getVelocityX() {
		return this.xVelocity;
	}
	
	public double getVelocityY() {
		return this.yVelocity;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public boolean shouldremove() {
		/*	This is called by the update function to remove an entity when
			it has served its purpose in the game. Rest in pieces.
		*/
		
		return this.remove;
	}
}
