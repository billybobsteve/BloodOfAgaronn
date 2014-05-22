import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Main implements Runnable, ActionListener {

	Screen currentScreen;
	SplashScreen splash = null;
	MainMenu menu = null;
	ScreenManager screenManager;
	boolean gameRunning = true;
	Thread init;
	ControllerLiason cL;
	Player pc;
	Map currentMap;
	static Thread thread;

	ArrayList<SoundClip> sfx = new ArrayList<SoundClip>();

	public static final int MENU_MUSIC = 0;
	public static final int DAMAGE_SOUND = 1;
	public static final int MOVEMENT_SOUND = 2;
	public static final int JUMP_SOUND = 3;
	//TODO add more sounds

	public static final int MOVE_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	public static final int JUMP = 0;


	public static void main(String[] args) {
		thread = new Thread(new Main());
		thread.start();
	}

	//TODO Update components
	
	public Main() {
		screenManager = new ScreenManager();
		screenManager.setFullScreen(screenManager.getCurrentDisplayMode());
		init = new Thread() {
			public void run() {
				initializeSoundEngine();
				initializeController();
			}
		};
		init.start(); 
	}

	public void initializeController() {
		cL = new ControllerLiason();
		if(cL.initialize()) {
			Scanner in = new Scanner(System.in);
			String input = "";
			while(!input.equals("Q"))
			{
				input = in.nextLine();

				for(int i = 0; i < input.length(); i++)
				{
					if(input.charAt(i) == ' ')
						cL.wait(400);
					else
						cL.sendData("" + input.charAt(i));
				}

				cL.wait(200);
			}
			in.close();
			cL.close();
		}
		System.out.println("Done.");
	}

	public void run() {
		NoRepaintsManager.setAsManager();

		//menu = new MainMenu(this);
		splash = new SplashScreen(this);
		currentScreen = splash;

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
			panel.add(c);
		}
		while(gameRunning) {
			//?
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

			//tell Swing that it is time to update
			if(currentScreen instanceof MainMenu)
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
		sfx.add(new SoundClip("sounds/whisper.wav"));
		/*sfx.add(new SoundClip("path/to/damage/sound"));
		sfx.add(new SoundClip("path/to/movement/sound"));
		sfx.add(new SoundClip("path/to/jump/sound")); */
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menu.quit)) {
			gameRunning = false;
			System.exit(0);
		}
		else if (e.getSource().equals(menu.start)) {
			currentMap = new Map();
		}
	}

	public void playSound(int sound) {
		sfx.get(sound).play();
	}

	public void movePlayer(int i) {
		if (i == MOVE_RIGHT) {
			//TODO set X velocity
		}
		else if (i == MOVE_LEFT) {
			//TODO set X velocity
		}
		else if (i == JUMP) {
			//TODO jump
		}
	}

}
