package asteroidGame.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	
	public Sound(String fileName) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(fileName));
		} catch (Exception e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				@Override
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Exception e) {}
	}
}
