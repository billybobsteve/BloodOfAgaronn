import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

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

	public MainMenu(Main m) {
		main = m;
		setUpComponents();
		//main.playSound(Main.MENU_MUSIC);
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		//TODO change width/height
		g.fillRect(0, 0, main.screenManager.getWidth(), main.screenManager.getHeight());
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
