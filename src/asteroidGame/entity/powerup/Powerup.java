package asteroidGame.entity.powerup;

import asteroidGame.entity.Entity;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;

public class Powerup extends Entity {
	
	BufferedImage img;
	int width, height;
	int life;
	
	public Powerup(double x, double y, String img) {
		super(x, y, 0);
		
		try {
			this.img = ImageIO.read(Powerup.class.getResource(img));
			this.img = Thumbnails.of(this.img).size(20, 20).asBufferedImage();
			this.width = this.img.getWidth();
			this.height = this.img.getHeight();
			shape = new Rectangle2D.Double((int) x, (int) y, width, height);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	public void draw(Graphics g) {
		life --;
		if (life == 0) {
			this.remove();
		}
		g.drawImage(img, (int) x, (int) y, null);
	}
}
