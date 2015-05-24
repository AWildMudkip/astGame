package asteroidGame;

public class Chaser extends Asteroid {
	public Chaser(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit) {
		super(x, y, radius, minVelocity, maxVelocity, hitsLeft, numSplit);
	}
	
	@Override
	public void move() {
		// How the Chaser moves. 
	}
}
