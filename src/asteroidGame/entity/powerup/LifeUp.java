package asteroidGame.entity.powerup;

import asteroidGame.World;

public class LifeUp extends Powerup {

	public LifeUp(double x, double y) {
		super(x, y, "lifeup.jpg");
		
		life = 5 * World.fps;
	}
	
}
