import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main implements Runnable, ActionListener {

	Screen currentScreen;
	MainMenu menu = null;
	ScreenManager screenManager;
	boolean gameRunning = true;
	ControllerListener cL;
	
	ArrayList<SoundClip> sfx = new ArrayList<SoundClip>();
	
	public static final int DAMAGE_SOUND = 0;
	public static final int MOVEMENT_SOUND = 1;
	public static final int JUMP_SOUND = 2;
	//TODO add more sounds

	public static void main(String[] args) {
		Thread thread = new Thread(new Main());
		thread.start();
	}

	public Main() {
		screenManager = new ScreenManager();
		screenManager.setFullScreen(screenManager.getCurrentDisplayMode());
		cL = new ControllerListener();
		//initializeSoundEngine();
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
		JPanel panel = new JPanel(new GridLayout(1,3));
		content.setLayout(new BorderLayout());
		content.add(panel,BorderLayout.SOUTH);
		components = currentScreen.getJComponentsToDraw();
		for (JComponent c : components) {
			//Add them to the window
			//content.add(c, BorderLayout.SOUTH);
			panel.add(c);
			//TODO Reduce flicker
		}
		while(gameRunning) {

			ArrayList<JComponent> componentsReplacement = currentScreen.getJComponentsToDraw();

			if (!components.equals(componentsReplacement)) {
				components = componentsReplacement;
				panel = new JPanel(new GridLayout(1,3));
				content.setLayout(new BorderLayout());
				content.add(panel,BorderLayout.SOUTH);
				components = currentScreen.getJComponentsToDraw();
				for (JComponent c : components) {
					panel.add(c);
				}
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

	public void initializeSoundEngine() {
		sfx.add(new SoundClip("path/to/damage/sound"));
		sfx.add(new SoundClip("path/to/movement/sound"));
		sfx.add(new SoundClip("path/to/jump/sound"));
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
	
	public void playSound(int sound) {
		sfx.get(sound).play();
	}


}
