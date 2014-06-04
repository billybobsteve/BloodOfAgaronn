import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class MainMenu extends Screen {

	Main main;
	JButton start, music, quit;
	JLabel difficulty = new JLabel("Difficulty:");
	JSlider difficultySlider = new JSlider(3, 25, 11);
	JPanel fix = new JPanel(new FlowLayout());
	ArrayList<JComponent> components = new ArrayList<JComponent>();
	BufferedImage image;
	int frame = 0;

	public MainMenu(Main m) {
		main = m;
		setUpComponents();
		
		try {
			image = ImageIO.read(new FileInputStream("main/castle_00000.jpg"));
		} catch (Exception e) { e.printStackTrace(); }
		
		//main.playSound(Main.MENU_MUSIC);
	}

	public void draw(Graphics g) {
		
		if (frame > 122)
			frame = 0;
		
		if (true/*System.currentTimeMillis() - timeSinceLastFrame >= FRAME_DELAY*/) {
			try {
				if (frame / 10 < 1)
					image = ImageIO.read(new FileInputStream("main/castle_0000" + frame + ".jpg"));
				else if (frame / 10 < 10)
					image = ImageIO.read(new FileInputStream("main/castle_000" + frame + ".jpg"));
				else //if (frame / 10 < 100)
					image = ImageIO.read(new FileInputStream("main/castle_00" + frame + ".jpg"));
			} catch (Exception e) { e.printStackTrace(); }
		}

		//g.drawImage(image, 0, 0, null); 
		g.drawImage(image, 0, 0, main.screenManager.getWidth(), main.screenManager.getHeight(), null);

		frame++;
		
	}

	public void setUpComponents() {
		start = new JButton("Start");
		start.addActionListener(main);
		music = new JButton("Music On/Off");
		music.addActionListener(main);
		quit = new JButton("Quit");
		quit.addActionListener(main);
		fix.add(difficulty);
		fix.add(difficultySlider);
		components.add(start);
		components.add(music);
		components.add(fix);
		components.add(quit);
	}

	public Screen nextScreen() {
		if (main.currentMap != null && main.gameStarted) {
			//main.stopSound(main.MENU_MUSIC);
			return main.currentMap.getNextRoom();
		}
		return this;
	}

}
