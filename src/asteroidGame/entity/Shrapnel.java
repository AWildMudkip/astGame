package asteroidGame.entity;

import asteroidGame.World;
import java.awt.Color;

public class Shrapnel extends Enemy {

	static final double lifeTime = 2 * World.fps;
	static final int points = 10;
	
	public Shrapnel(double x, double y, double minVelocity, double maxVelocity, double angle, Color color) {
		super(x, y, minVelocity, maxVelocity, angle, 1, color, points);
		
		
	}
	
}
