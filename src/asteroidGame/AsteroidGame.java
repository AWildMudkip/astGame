package asteroidGame;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

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

	ArrayList<Asteroid> asteroids = new ArrayList<>();
	ArrayList<Shot> shots = new ArrayList<>();
	
	double astRadius, minAstVel, maxAstVel; //values used to create
	//asteroids
	int astNumHits, astNumSplit;

	int level; //the current level number

	@Override
	public void init() {
		resize(900, 900);

		level = 0; //will be incremented to 1 when first level is set up
		astRadius = 30; //values used to create the asteroids
		minAstVel = .5;
		maxAstVel = 5;
		astNumHits = 3;
		astNumSplit = 2;
		endTime = 0;
		startTime = 0;
		framePeriod = 25;
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
		ship = new Ship(250, 250, 0, .35, .98, .1, 2, new Color(250, 250, 250));

		spawnone = new Spawn(100, 100, 40, Color.RED);
		spawntwo = new Spawn(400, 100, 40, Color.BLUE);
		spawnthree = new Spawn(250, 300, 40, Color.GREEN);
                
		//no shots on the screen at beginning of level
		paused = false;
		shooting = false;
		//create an array large enough to hold the biggest number
		//of asteroids possible on this level (plus one because
		//the split asteroids are created first, then the original
		//one is deleted). The level number is equal to the
		//number of asteroids at it's start.
		
		asteroids = new ArrayList<>();
		shots = new ArrayList<>();
		
		//create asteroids in random spots on the screen
		for (int i = 0; i < level; i++)
			asteroids.add(	new Asteroid(Math.random() * dim.width,
							Math.random() * dim.height, astRadius, minAstVel,
							maxAstVel, astNumHits, astNumSplit));
	}

	@Override
	public void paint(Graphics gfx) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 900, 900);

		// Draw all shots.
		Iterator itr0 = shots.iterator();
		while (itr0.hasNext()) {
			Entity shot = (Entity) itr0.next();
			if (shot.shouldremove())
				itr0.remove();
			shot.draw(g);
		}
	
		// Draw all asteroids.
		Iterator itr1 = asteroids.iterator();
		while (itr1.hasNext()) {
			Entity asteroid = (Entity) itr1.next();
			if (asteroid.shouldremove())
				itr1.remove();
			asteroid.draw(g);
		}

		ship.draw(g); //draw the ship
		spawnone.draw(g);
		spawntwo.draw(g);
		spawnthree.draw(g);

		g.setColor(Color.cyan); //Display level number in top left corner
		g.drawString("Level " + level, 20, 20);

		gfx.drawImage(img, 0, 0, this);
	}

	@Override
	public void update(Graphics gfx) {	
		paint(gfx);
	}
	
	public void step() {
		// Update ship.
		ship.move();
		
		for (Entity shot : shots) {
			shot.move();
		}
		
		// Update asteroids.
		ArrayList<Asteroid> temp = new ArrayList<>();
		
		for (Asteroid asteroid : asteroids) {
			asteroid.move();
			if (asteroid.collision(ship)) {
				level --;
				setUpNextLevel();
				break;
			}
			for (Entity shot : shots) {
				if (asteroid.collision(shot)) {
					shot.remove();
					if (asteroid.getHitsLeft() > 1) {
						for (int k = 0; k < asteroid.getNumSplit(); k++)
							temp.add(asteroid.createSplitAsteroid(minAstVel, maxAstVel));
					}
					asteroid.remove();
					break;
				}
			}
		}
		
		// Join temporary into current.
		asteroids.addAll(temp);
	}
	
	@Override
	public void run() {
		for (;;) {
			startTime = System.currentTimeMillis();

			//start next level when all asteroids are destroyed
			if (asteroids.size() <= 0)
				setUpNextLevel();

			if (!paused) {
				step();
				
				updateRed();
				updateBlue();
				updateGreen();

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

	private void updateRed() {
		if (spawnone.contact(ship)) {
			Color cee = new Color(255, 0, 0);
			ship.colorReset(cee);
		}
	}
	private void updateGreen() {
		if (spawnthree.contact(ship)) {
			Color cee = new Color(0, 255, 0);
			ship.colorReset(cee);
		}
	}
	private void updateBlue() {
		if (spawntwo.contact(ship)) {
			Color cee = new Color(0, 0, 255);
			ship.colorReset(cee);
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