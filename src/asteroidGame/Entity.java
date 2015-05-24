package asteroidGame;

import java.awt.*;

public abstract class Entity {
	protected double x, y, angle, xVelocity, yVelocity, radius;
	private boolean remove;
	
	protected final int scrnWidth = 900;
	protected final int scrnHeight = 900;
	
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
		
		return Math.pow(entity.getRadius() + this.getRadius(), 2) > Math.pow(entity.getX() - this.getX(), 2) + Math.pow(entity.getY() - this.getY(), 2);
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
	
	public void draw(Graphics g) {}
	public void move() {}
}
