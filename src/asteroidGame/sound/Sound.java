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
		} catch (Exception e) {}
	}
	
	public void playOnce() {
		try {
			clip.play();
		} catch (Exception e) {}
	}
	
	public void start() {
		try {
			if (!started) {
				started = true;
				clip.loop();
			}
		} catch (Exception e) {}
	}
	
	public void stop() {
		try {
			if (started) {
				started = false;
				clip.stop();
			}
		} catch (Exception e) {}
	}
	
}
