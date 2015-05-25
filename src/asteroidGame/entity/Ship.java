package asteroidGame.entity;

import asteroidGame.World;
import java.awt.*;

public class Ship extends Entity {
	final int[] origXPts = {
			14, -10, -6, -10
		}, origYPts = {
			0, -8, 0, 8
		},
		origFlameXPts = {
			-6, -23, -6
		}, origFlameYPts = {
			-3, 0, 3
		};

	double acceleration, velocityDecay, rotationalSpeed;
	boolean turningLeft, turningRight, accelerating, active;
	int[] xPts, yPts, flameXPts, flameYPts;
	double shotDelay, shotDelayLeft;
	int flashleft;
	Color reed;
	
	Shape polyship;

	public Ship(double x, double y, double angle, double acceleration, double velocityDecay, double rotationalSpeed, double shotDelay, Color col) {
		super(x, y, angle);
		
		// Specific to the ship entity.
		this.acceleration = acceleration;
		this.velocityDecay = velocityDecay;
		this.rotationalSpeed = rotationalSpeed;
		xVelocity = 0; // not moving
		yVelocity = 0;
		turningLeft = false; // not turning
		turningRight = false;
		accelerating = false; // not accelerating
		active = false; // start off paused

		xPts = new int[4]; // allocate space for the arrays
		yPts = new int[4];
		flameXPts = new int[3];
		flameYPts = new int[3];
		this.shotDelay = shotDelay * World.fps; // Shots per second.
		shotDelayLeft = 0; // ready to shoot
		reed = col;
		
		shape = new Polygon(origXPts, origYPts, 4);
	}

	public void draw(Graphics g) {
		//rotate the points, translate them to the ship's location (by
		//adding x and y), then round them by adding .5 and casting them
		//as integers (which truncates any decimal place)
		if (accelerating && active) { // draw flame if accelerating
			for (int i = 0; i < 3; i++) {
				flameXPts[i] = (int)(origFlameXPts[i] * Math.cos(angle) -
					origFlameYPts[i] * Math.sin(angle) +
					x + .5);
				flameYPts[i] = (int)(origFlameXPts[i] * Math.sin(angle) +
					origFlameYPts[i] * Math.cos(angle) +
					y + .5);
			}
			g.setColor(Color.red); //set color of flame
			g.drawPolygon(flameXPts, flameYPts, 3); // 3 is # of points
		}
		//calculate the polgyon for the ship, then draw it
		for (int i = 0; i < 4; i++) {
			xPts[i] = (int)(origXPts[i] * Math.cos(angle) - //rotate
				origYPts[i] * Math.sin(angle) +
				x + .5); //translate and round
			yPts[i] = (int)(origXPts[i] * Math.sin(angle) + //rotate
				origYPts[i] * Math.cos(angle) +
				y + .5); //translate and round
		}
		if (active) {// active means game is running (not paused)
			if (flashleft > 0) {
				if ((int) (flashleft / 10) % 2 == 0) {
					g.setColor(reed);
				} else {
					g.setColor(Color.BLACK);
				}
				flashleft --;
			} else {
				g.setColor(reed);
			}
		} else { // draw the ship dark gray if the game is paused
			g.setColor(Color.darkGray);
		}
		
		shape = new Polygon(xPts, yPts, 4);
		g.drawPolygon((Polygon) shape); // 4 is number of points
	}
	public void colorReset(Color c) {
		reed = c;
	}

	public void move() {
		if (shotDelayLeft > 0) //move() is called every frame that the game
			shotDelayLeft--; //is run; this ticks down the shot delay
		if (turningLeft) //this is backwards from typical polar coodinates
			angle -= rotationalSpeed; //because positive y is downward.
		if (turningRight) //Because of that, adding to the angle is
			angle += rotationalSpeed; //rotating clockwise (to the right)
		if (angle > (2 * Math.PI)) //Keep angle within bounds of 0 to 2*PI
			angle -= (2 * Math.PI);
		else if (angle < 0)
			angle += (2 * Math.PI);
		if (accelerating) { //adds accel to velocity in direction pointed
			//calculates components of accel and adds them to velocity
			xVelocity += acceleration * Math.cos(angle);
			yVelocity += acceleration * Math.sin(angle);
		}
		x += xVelocity; //move the ship by adding velocity to position

		y += yVelocity;
		xVelocity *= velocityDecay; //slows ship down by percentages
		yVelocity *= velocityDecay; //(velDecay should be between 0 and 1)
		if (x < 0) //wrap the ship around to the opposite side of the screen
			x += scrnWidth; //when it goes out of the screen's bounds
		else if (x > scrnWidth)
			x -= scrnWidth;
		if (y < 0)
			y += scrnHeight;
		else if (y > scrnHeight)
			y -= scrnHeight;
	}

	public void setAccelerating(boolean accelerating) {
		this.accelerating = accelerating;
	}

	public void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}

	public void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	public Color getColor() {
		return reed;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean isInvincible() {
		return flashleft > 0;
	}

	public boolean canShoot() {
		return shotDelayLeft <= 0;
		//checks to see if the ship is ready to
		//shoot again yet or needs to wait longer
	}
	public Shot shoot() {
		shotDelayLeft = shotDelay; 
		
		// Life in seconds.
		return new Shot(x, y, angle, xVelocity, yVelocity, 5, reed);
	}
	
	public void blink() {
		flashleft = 3 * World.fps;
	}
	
	public void startSound() {
		thrusterSound.start();
	}
	
	public void stopSound() {
		thrusterSound.stop();
	}
}