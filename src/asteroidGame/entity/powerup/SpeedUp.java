package asteroidGame.entity.powerup;

import asteroidGame.World;
import java.awt.Graphics;

public class SpeedUp extends Powerup {

	public SpeedUp(double x, double y) {
		super(x, y, "speedup.png");
		
		life = -1;
	}
        
}
