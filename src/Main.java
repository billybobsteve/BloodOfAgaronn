import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Main implements Runnable, ActionListener {

	Screen currentScreen;
	MainMenu menu = null;
	ScreenManager screenManager;
	boolean gameRunning = true;
	ControllerListener cL;

	public static void main(String[] args) {
		Thread thread = new Thread(new Main());
		thread.start();
	}

	public Main() {
		screenManager = new ScreenManager();
		screenManager.setFullScreen(screenManager.getCurrentDisplayMode());
		cL = new ControllerListener();
	}

	public void run() {
		NoRepaintsManager.setAsManager();

		menu = new MainMenu(this);
		currentScreen = menu;

		/*
		///Make some Swing components
		JLabel label = new JLabel("Welcome!", SwingConstants.CENTER);
		JButton button = new JButton("CLICK");

		Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		button.setCursor(c);
		//button.addActionListener();
		button.setBorder(null);
		button.setToolTipText("This is helpful!"); */

		ArrayList<JComponent> components = null;
		JFrame frame = screenManager.getFullScreenWindow();
		Container content = frame.getContentPane();
		((JComponent)content).setOpaque(false);
		content.setLayout(new BorderLayout());

		while(gameRunning) {
			//ArrayList<JComponent> componentsReplacement = currentScreen.getJComponentsToDraw();
			components = currentScreen.getJComponentsToDraw();
			for (JComponent c : components) {
				//Add them to the window
				content.add(c, BorderLayout.NORTH);
				content.add(c, BorderLayout.NORTH);
				//TODO Reduce flicker
			}

			frame.validate();

			Graphics2D g = screenManager.getGraphics();
			currentScreen.draw(g);

			BufferedImage image = null;

			try {
				image = ImageIO.read(new FileInputStream("splash_screen.png"));
			} catch (Exception e) { e.printStackTrace(); }

			g.drawImage(image, 0, 0, null); 

			//tell Swing that it is time to update
			screenManager.getFullScreenWindow().getLayeredPane().paintComponents(g);

			g.dispose();
			screenManager.update();

			Screen nextScreen = currentScreen.nextScreen();
			if (nextScreen != null && nextScreen != currentScreen) 
				currentScreen = nextScreen;

		}
		screenManager.restoreScreen();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menu.quit)) {
			gameRunning = false;
			System.exit(0);
		}
		else if (e.getSource().equals(menu.start)) {
			currentScreen = currentScreen.nextScreen();
		}
	}

}
