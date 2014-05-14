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
	public SoundClip(String filename){
		file = new File(filename);
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(file);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	public void play(){
		new Thread(new Runnable(){
			public void run(){
				try {
					clip.open(inputStream);
					clip.start();
				} catch (IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void stop(){
		clip.stop();
	}

	public void close(){
		clip.close();
	}
}
