import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {
	private File file;
	private Clip clip;
	private AudioInputStream inputStream;
	//plays a sound
	public SoundClip(String filename){
		file = new File(filename);
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	//play the sound
	public void play(){
		new Thread(new Runnable(){
			public void run(){
				clip.start();
			}
		}).start();
	}
	//stop the sound
	public void stop(){
		clip.stop();
	}
	public void close(){
		clip.close();
	}
}
