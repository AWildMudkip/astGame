package asteroidGame.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	private boolean started = false;
	
	// Preload all sounds.
	public static final Sound shotSound = new Sound("laser.wav");
	public static final Sound asteroidSound = new Sound("explosion-02.wav");
	public static final Sound thrusterSound = new Sound("rocket.wav");
	
	public Sound(String fileName) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(fileName));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void playOnce() {
		try {
			new Thread() {
				@Override
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Exception e) {}
	}
	
	public void start() {
		try {
			new Thread() {
				@Override
				public void run() {
					if (!started) {
						started = true;
						clip.loop();
					}
				}
			}.start();
		} catch (Exception e) {}
	}
	
	public void stop() {
		try {
			new Thread() {
				@Override
				public void run() {
					clip.stop();
					started = false;
				}
			}.start();
		} catch (Exception e) {}
	}
	
}
