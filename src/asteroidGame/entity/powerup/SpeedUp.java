package asteroidGame.entity.powerup;

import asteroidGame.World;

public class SpeedUp extends Powerup {

	public SpeedUp(double x, double y) {
		super(x, y, "speedup.png");
		
		life = 5 * World.fps;
	}
	
}
