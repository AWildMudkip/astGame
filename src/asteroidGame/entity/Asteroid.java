package asteroidGame.entity;

public class Asteroid extends Enemy {

	public Asteroid(double x, double y, double radius, double minVelocity, double maxVelocity, int hitsLeft, int numSplit) {
		super(x, y, radius, minVelocity, maxVelocity, hitsLeft, numSplit);
	}

}
