package asteroidGame;

import asteroidGame.entity.Asteroid;

import asteroidGame.entity.Spawn;
import asteroidGame.entity.Shot;
import asteroidGame.entity.Enemy;
import asteroidGame.entity.Ship;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsteroidGame extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	Thread thread;
	Dimension dim;
	Image img;
	Graphics g;

	long endTime, startTime, framePeriod;

	Ship ship;
	boolean paused; // True if the game is paused. Enter is the pause key

	Spawn spawnone, spawntwo, spawnthree;

	boolean shooting;

	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Shot> shots = new ArrayList<>();
	ArrayList<Spawn> spawners = new ArrayList<>();
	
	double astRadius, minAstVel, maxAstVel; //values used to create
	//asteroids
	int astNumHits, astNumSplit;

	int level; //the current level number
	int lives; // Are you a cat? Do you have 9 lives? No? Great.
	int score; // The score

	@Override
	public void init() {
		resize(World.scrnWidth, World.scrnHeight);

		level = 0; //will be incremented to 1 when first level is set up
		lives = 3;
		score = 0;
		
		astRadius = 30; //values used to create the asteroids
		minAstVel = .5;
		maxAstVel = 5;
		astNumHits = 3;
		astNumSplit = 2;
		endTime = 0;
		startTime = 0;
		framePeriod = World.framePeriod;
		addKeyListener(this); //tell it to listen for KeyEvents
		dim = getSize();
		img = createImage(dim.width, dim.height);
		g = img.getGraphics();
		thread = new Thread(this);
		thread.start();
	}

	public void setUpNextLevel() { //start new level with one more asteroid
		level++;
		// create a new, inactive ship centered on the screen
		// I like .35 for acceleration, .98 for velocityDecay, and
		// .1 for rotationalSpeed. They give the controls a nice feel.
		ship = new Ship(250, 250, 0, .35, .98, .1, 0.33, new Color(250, 250, 250));
                
		//no shots on the screen at beginning of level
		paused = true;
		shooting = false;
		//create an array large enough to hold the biggest number
		//of asteroids possible on this level (plus one because
		//the split asteroids are created first, then the original
		//one is deleted). The level number is equal to the
		//number of asteroids at it's start.
		
		enemies = new ArrayList<>();
		shots = new ArrayList<>();
		spawners = new ArrayList<>();
		
		// Add the spawners!
		spawners.add(new Spawn(100, 100, 40, Color.RED));
		spawners.add(new Spawn(250, 300, 40, Color.GREEN));
		spawners.add(new Spawn(400, 100, 40, Color.BLUE));
		
		//create asteroids in random spots on the screen
		for (int i = 0; i < level; i++)
			enemies.add(new Asteroid(Math.random() * dim.width,
							Math.random() * dim.height, astRadius, minAstVel,
							maxAstVel, astNumHits, astNumSplit, Asteroid.randomColor()));
			
	}
	
	public void resetShip() {	
		ship = new Ship(250, 250, 0, .35, .98, .1, 0.33, new Color(250, 250, 250));
		ship.setActive(true);
		ship.blink();
	}

	@Override
	public void paint(Graphics gfx) {
		g.setColor(Color.black);
		g.fillRect(0, 0, World.scrnWidth, World.scrnHeight);

		// Draw all shots.
		Iterator itr0 = shots.iterator();
		while (itr0.hasNext()) {
			Shot shot = (Shot) itr0.next();
			if (shot.shouldremove())
				itr0.remove();
			shot.draw(g);
		}
	
		// Draw all asteroids.
		Iterator itr1 = enemies.iterator();
		while (itr1.hasNext()) {
			Enemy enemy = (Enemy) itr1.next();
			if (enemy.shouldremove())
				itr1.remove();
			enemy.draw(g);
		}
		
		// Draw the spawners.
		for (Spawn spawn : spawners) {
			spawn.draw(g);
		}

		ship.draw(g); // Draw the ship.

		g.setColor(Color.cyan); //Display level number in top left corner
		g.drawString("Level " + level, 20, 20);
		g.drawString("Score " + score, 120, 20);
		g.drawString("Lives " + lives, 220, 20);

		gfx.drawImage(img, 0, 0, this);
	}

	@Override
	public void update(Graphics gfx) {	
		paint(gfx);
	}
	
	public void step() {
		// Update ship.
		ship.move();
		
		for (Shot shot : shots) {
			shot.move();
		}
		
		// Update asteroids.
		ArrayList<Enemy> temp = new ArrayList<>();
		
		for (Enemy enemy : enemies) {
			if (enemy instanceof Asteroid) {
				enemy = (Asteroid) enemy;
				enemy.move();
			}
			
			if (enemy.collision(ship) && !ship.isInvincible()) {
				lives --;
				if (lives == 0) {					
					break;
				} else {
					resetShip();
					break;
				}
			}
			for (Shot shot : shots) {
				if (enemy.collision(shot)) {
					shot.remove();
					
					// This takes care of the asteroid enemy type.
					if (enemy instanceof Asteroid && enemy.getColor() == shot.getColor()) {
						if (enemy.getHitsLeft() > 1) {
							Asteroid asteroid = (Asteroid) enemy;
							for (int k = 0; k < asteroid.getNumSplit(); k++)
								temp.add(asteroid.createSplitAsteroid(minAstVel, maxAstVel));
						}
						score += enemy.getScore();
						enemy.remove();
					}
					break;
				}
			}
		}
		
		// Join temporary into current.
		enemies.addAll(temp);
		
		// Check the spawners.
		for (Spawn spawn : spawners) {
			if (spawn.collision(ship))
				ship.colorReset(spawn.getColor());
		}
	}
	
	@Override
	public void run() {
		for (;;) {
			startTime = System.currentTimeMillis();

			//start next level when all asteroids are destroyed
			if (enemies.size() <= 0)
				setUpNextLevel();

			if (!paused) {
				step();
			
				if (shooting && ship.canShoot()) {
					// Add a shot.
					shots.add(ship.shoot());
				}
			}

			repaint();
			
			try {
				endTime = System.currentTimeMillis();
				if (framePeriod - (endTime - startTime) > 0)
					Thread.sleep(framePeriod - (endTime - startTime));
			} catch (InterruptedException e) {}

		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//These first two lines allow the asteroids to move
			//while the player chooses when to enter the game.
			//This happens when the player is starting a new life.
			if (!ship.isActive() && !paused)
				ship.setActive(true);
			else {
				paused = !paused; //enter is the pause button
				if (paused) // grays out the ship if paused
					ship.setActive(false);
				else

					ship.setActive(true);
			}
		} else if (paused || !ship.isActive()) //if the game is
			return; //paused or ship is inactive, do not respond
		//to the controls except for enter to unpause
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			ship.setAccelerating(true);
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setTurningLeft(true);
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.setTurningRight(true);
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
			shooting = true; //Start shooting if ctrl is pushed
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			ship.setAccelerating(false);
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			ship.setTurningLeft(false);
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.setTurningRight(false);
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
			shooting = false;
	}

	public void keyTyped(KeyEvent e) {}
}