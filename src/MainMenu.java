import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;




public class MainMenu extends Screen {

	Main main;
	JButton start, options, quit;
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
		options = new JButton("Options");
		options.addActionListener(main);
		quit = new JButton("Quit");
		quit.addActionListener(main);
		components.add(start);
		components.add(options);
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
