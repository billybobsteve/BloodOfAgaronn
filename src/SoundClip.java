import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {
	File file;
	Clip clip;
	public SoundClip(String filename){
		file = new File(filename);
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void play(){
		new Thread(new Runnable(){
			public void run(){
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
					clip.open(inputStream);
					clip.start();
				} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
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
