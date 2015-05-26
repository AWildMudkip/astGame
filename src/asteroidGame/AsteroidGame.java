package asteroidGame;

import asteroidGame.entity.enemy.*;
import asteroidGame.entity.powerup.*;
import asteroidGame.entity.*;
import java.applet.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class AsteroidGame extends Applet implements Runnable, KeyListener {

	// System globals.
	private static final long serialVersionUID = 1L;
	Thread thread;
	Dimension dim;
	Image img;
	Graphics g;
	long endTime, startTime, framePeriod;

	// Player variables.
	int level, lives, score;
	boolean paused; // True if game is paused. Enter is the the pause key.
	boolean shooting;
	Ship ship = null; // Initialize ship.
	
	// Ship variables.
	double ship_accel, ship_decay, ship_rotspeed, ship_firerate;
	
	// Asteroid variables.
	double ast_radius, ast_minVel, ast_maxVel;
	int ast_numHits, ast_numSplit;
	
	// Virus variables.
	int vir_spikes, vir_innerr, vir_outerr, vir_life;
	double vir_minVel, vir_maxVel;
	
	// Global entity and temporaryarrays.
	List<Entity> entities = new ArrayList<>();
	List<Entity> pending = new ArrayList<>();

	@Override
	public void init() {
		resize(World.scrnWidth, World.scrnHeight);
		
		preload();
		reset();
		
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
	
	public void preload() {
		/* Instantize loading. */
		LifeUp lifeUp = new LifeUp(Math.random() * World.scrnWidth, Math.random() * World.scrnHeight);
	}
	
	public void reset() {
		// Basic configuation.
		level = 0;
		lives = 3;
		score = 0;
		ship = null;
		
		// Ship variables.
		ship_accel = 0.20;
		ship_decay = 0.98;
		ship_rotspeed = 0.1;
		ship_firerate = 0.33;
		
		// Asteroid configuation.
		ast_radius = 30;
		ast_minVel = 0.3;
		ast_maxVel = 1;
		ast_numHits = 3;
		ast_numSplit = 2;
		
		// Virus configuation.
		vir_spikes = 5;
		vir_innerr = 5;
		vir_outerr = 15;
		vir_life = 2;
		vir_minVel = 0.5;
		vir_maxVel = 1;
		
	}

	public void setUpNextLevel() { //start new level with one more asteroid
		level++;
		ship_accel=.20;
		// create a new, inactive ship centered on the screen
		// I like .35 for acceleration, .98 for velocityDecay, and
		// .1 for rotationalSpeed. They give the controls a nice feel.
		if (ship == null) {
			resetShip();
			ship.setActive(false);
		}
                
		//no shots on the screen at beginning of level
		paused = true;
		shooting = false;
		//create an array large enough to hold the biggest number
		//of asteroids possible on this level (plus one because
		//the split asteroids are created first, then the original
		//one is deleted). The level number is equal to the
		//number of asteroids at it's start.
		
		entities = new ArrayList<>();
		pending = new ArrayList<>();
		
		// Add the spawners!
		entities.add(new Spawn(World.scrnWidth / 4, World.scrnHeight / 4, 40, Color.RED));
		entities.add(new Spawn(World.scrnWidth * 1/2, World.scrnHeight * 3/4, 40, Color.GREEN));
		entities.add(new Spawn(World.scrnWidth * 3/4, World.scrnHeight / 4, 40, Color.BLUE));
		
		//create asteroids in random spots on the screen
		for (int i = 0; i < level; i++) {
			entities.add(new Asteroid(Math.random() * World.scrnWidth, Math.random() * World.scrnHeight,
							ast_radius, ast_minVel, ast_maxVel, ast_numHits, ast_numSplit, Asteroid.randomColor()));
			entities.add(new Virus(Math.random() * World.scrnWidth, Math.random() * World.scrnHeight,
							vir_minVel, vir_maxVel, vir_innerr, vir_outerr, vir_life, vir_spikes));
			entities.add(new SpeedUp(Math.random() * World.scrnWidth, Math.random() * World.scrnHeight));
		}	
	}
	
	public void resetShip() {
		int whereSpawn=(int)((Math.random()*3)+1);
        ship_accel=.20;
		if(whereSpawn==1)
		{ship = new Ship(World.scrnWidth / 4, World.scrnHeight / 4, 0, ship_accel, ship_decay, ship_rotspeed, ship_firerate, new Color(250, 0, 0));}
		if(whereSpawn==2)
		{ship=new Ship(World.scrnWidth*1/2, World.scrnHeight*3/4, 0, ship_accel, ship_decay, ship_rotspeed, ship_firerate, new Color(0, 255, 0));}
		if(whereSpawn==3)
		{ship=new Ship(World.scrnWidth*3/4, World.scrnHeight/4, 0, ship_accel, ship_decay, ship_rotspeed, ship_firerate, new Color(0, 0, 255));}
		ship.setActive(true);
		ship.blink();
	}

	@Override
	public void paint(Graphics gfx) {
		g.setColor(Color.black);
		g.fillRect(0, 0, World.scrnWidth, World.scrnHeight);

		Iterator itr = entities.iterator();
		while (itr.hasNext()) {
			Entity entity = (Entity) itr.next();
			if (entity.shouldremove())
				itr.remove();
			entity.draw(g);
		}
		
		if (ship != null)
			ship.draw(g);
	
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
		
		List<Shot> shots = entities.stream().filter(e -> e instanceof Shot).map(e -> (Shot) e).collect(Collectors.toList());
		List<Enemy> enemies = entities.stream().filter(e -> e instanceof Enemy).map(e -> (Enemy) e).collect(Collectors.toList());
		
		// Update ship.
		ship.move();
		
		// Update shots.
		shots.stream().forEach((shot) -> {
			shot.move();
		});
		
		// Update spawners.
		entities.stream().filter(e -> e instanceof Spawn).map(e -> (Spawn) e).forEach((spawn) ->{
			if (spawn.collision(ship))
				ship.colorReset(spawn.getColor());
		});
		
		// Update powerups.
		entities.stream().filter(e -> e instanceof Powerup).map(e -> (Powerup) e).forEach((powerup) -> {
			if (powerup.collision(ship)) {
				if (powerup instanceof LifeUp) {
					this.lives ++;
					powerup.remove();
				}
				else if (powerup instanceof SpeedUp) {
					this.ship_accel += 0.15;
					ship.setAcceleration(this.ship_accel);
					powerup.remove();
				}
			}
		});
		
		// Update enemies.		
		for (Enemy enemy : enemies) {
			if (enemy instanceof Asteroid) {
				enemy = (Asteroid) enemy;
				enemy.move();
			} else if (enemy instanceof Virus) {
				enemy = (Virus) enemy;
				enemy.move();
			} else if (enemy instanceof Shrapnel) {
				enemy = (Shrapnel) enemy;
				enemy.move();
			}
			
			if (enemy.collision(ship) && !ship.isInvincible()) {
				lives --;
				if (lives == 0) {
					reset();
					setUpNextLevel();
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
						Asteroid asteroid = (Asteroid) enemy;
						asteroid.playSound(); // Boom!
						if (enemy.getHitsLeft() > 1) {
							for (int k = 0; k < asteroid.getNumSplit(); k++)
								pending.add(asteroid.createSplitAsteroid(ast_minVel, ast_maxVel));
						}
						score += enemy.getScore();
						enemy.remove();
					} 
					
					// This takes care of the virus enemy type.
					else if (enemy instanceof Virus) {
						Virus virus = (Virus) enemy;
						virus.hit();
						if (virus.getHitsLeft() == 0) {
							virus.remove();
							pending.addAll(virus.virusShrapnels());
							pending.add(new LifeUp(virus.getX(), virus.getY()));
							score += virus.getScore();
						}
					}
					
					// This handles shrapnels from a virus.
					else if (enemy instanceof Shrapnel) {
						Shrapnel shrapnel = (Shrapnel) enemy;
						shrapnel.hit();
						if (shrapnel.getHitsLeft() == 0) {
							shrapnel.remove();
							score += shrapnel.getScore();
						}
					}
					
					break;
				}
			}
		}
	
	}
	
	@Override
	public void run() {
		for (;;) {
			startTime = System.currentTimeMillis();

			entities.addAll(pending);
			pending = new ArrayList<>();
			
			//start next level when all asteroids are destroyed
			if (entities.stream().filter(e -> e instanceof Enemy).count() == 0)
				setUpNextLevel();

			if (!paused) {
				step();
			
				if (shooting && ship.canShoot()) {
					// Add a shot.
					pending.add(ship.shoot());
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
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			ship.setAccelerating(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.setTurningLeft(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.setTurningRight(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			shooting = true; //Start shooting if ctrl is pushed
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			ship.setAccelerating(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.setTurningLeft(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.setTurningRight(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			shooting = false;
		}
	}

	public void keyTyped(KeyEvent e) {}
}