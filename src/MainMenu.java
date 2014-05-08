import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;



public class MainMenu extends Screen {

	Main main;
	JButton start, options, quit;
	
	public MainMenu(Main m) {
		main = m;
		setUpComponents();
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
		// TODO Auto-generated method stub
		return null;
	}

}
