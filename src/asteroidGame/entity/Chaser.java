package asteroidGame.entity;

public class Chaser extends Enemy {
	public Chaser(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit) {
		super(x, y, radius, minVelocity, maxVelocity, hitsLeft, numSplit);
	}
	
	public void move(Entity ship) {
		// How the Chaser moves. 
	}
}
