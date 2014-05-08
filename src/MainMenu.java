import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainMenu extends Screen {

	Main main;
	
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
		components.add(new JButton("Start"));
		components.add(new JButton("Options"));
		components.add(new JButton("Quit"));
	}

	@Override
	public Screen nextScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}
