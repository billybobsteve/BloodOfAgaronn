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
	JButton start, music, quit, info;
	JLabel difficulty = new JLabel("Difficulty:");
	JSlider difficultySlider = new JSlider(3, 25, 11);
	JPanel fix = new JPanel(new FlowLayout());
	ArrayList<JComponent> components = new ArrayList<JComponent>();
	BufferedImage image, information;
	int frame = 0;
	boolean drawInfo = false;

	public MainMenu(Main m) {
		main = m;
		setUpComponents();

		try {
			image = ImageIO.read(new FileInputStream("main/castle_00000.jpg"));
			information = ImageIO.read(new FileInputStream("info.png"));
		} catch (Exception e) { e.printStackTrace(); }

		//main.playSound(Main.MENU_MUSIC);
	}

	public void draw(Graphics g) { // Draws jpeg sequence

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
			
			if (drawInfo)
				g.drawImage(information, ((main.screenManager.getWidth()-information.getWidth())/2), ((main.screenManager.getHeight()-information.getHeight())/2), information.getWidth(), information.getHeight(), null);

	}

	public void setUpComponents() {
		start = new JButton("Start");
		start.addActionListener(main);
		music = new JButton("Music On/Off");
		music.addActionListener(main);
		info = new JButton("How to play");
		info.addActionListener(main);
		quit = new JButton("Quit");
		quit.addActionListener(main);
		fix.add(difficulty);
		fix.add(difficultySlider);
		components.add(start);
		components.add(music);
		components.add(fix);
		components.add(info);
		components.add(quit);
	}

	public Screen nextScreen() {
		if (main.currentMap != null && main.gameStarted) {
			main.stopSound(main.MENU_MUSIC);
			main.playSound(main.AMBIENT_MUSIC);
			return main.currentMap.getNextRoom();
		}
		return this;
	}

}
