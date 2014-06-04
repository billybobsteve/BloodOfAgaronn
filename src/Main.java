import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public class Main implements Runnable, ActionListener {

	Screen currentScreen;
	SplashScreen splash = null;
	MainMenu menu = null;
	ScreenManager screenManager;
	Thread init;
	ControllerLiason cL;
	Player pc;
	Map currentMap;
	static Thread thread;
	JFrame frame;
	Container content;
	JPanel panel, pausePanel, inventoryPanel; //various panels
	boolean gameRunning = true, leftHeld = false, rightHeld = false, paused = false, musicIsPlaying = true;
	PauseMenu pause;
	boolean gameStarted = false;
	Inventory inventory;

	ArrayList<SoundClip> sfx = new ArrayList<SoundClip>();
	
	/* Constants to play sound effects */
	public static final int MENU_MUSIC = 0; 
	//public static final int AMBIENT_MUSIC_1 = 1;
	public static final int SPLASH_MUSIC = 1;
	public static final int DAMAGE_SOUND = 2;
	public static final int MOVEMENT_SOUND = 3;
	public static final int JUMP_SOUND = 4;

	/* Constants for movement */
	public static final int MOVE_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	public static final int JUMP = 0;
	public static final int STOP_LEFT = 2;
	public static final int STOP_RIGHT = 3;


	public static void main(String[] args) {
		thread = new Thread(new Main());
		thread.start();
	}

	//TODO Update components

	public Main() {
		screenManager = new ScreenManager();
		screenManager.setFullScreen(screenManager.getCurrentDisplayMode(),null);
		screenManager.getFullScreenWindow().setVisible(false);		
		screenManager.getFullScreenWindow().setVisible(true);


		init = new Thread() {
			public void run() {
				initializeSoundEngine();
				initializeController();
			}
		};
		init.start(); 
	}

	@SuppressWarnings("serial")
	/**
	 * Sets up keyboard input; uses key bindings instead of keylistener
	 */
	public void initializeKeyboard() {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRoot(panel);
		frame.setFocusTraversalKeysEnabled(false);
		panel.setFocusTraversalKeysEnabled(false); 
		Action right = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movePlayer(MOVE_RIGHT);
			}
		};
		Action left = new AbstractAction() {
			public void actionPerformed(ActionEvent e) { 
				movePlayer(MOVE_LEFT);
			}
		};
		Action jump = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movePlayer(JUMP);
			}
		};
		Action stop_left = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movePlayer(STOP_LEFT);
			}
		};
		Action stop_right = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movePlayer(STOP_RIGHT);
			}
		};
		Action attack = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				currentMap.player.attack();
				panel.requestFocusInWindow();
			}
		};
		Action pause = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		};
		
		/* Bindings */
		screenManager.getFullScreenWindow().enableInputMethods(true);
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),"right");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "jump");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stop_right");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stop_left");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false), "attack");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "pause");
		panel.getActionMap().put("right", right);
		panel.getActionMap().put("left", left);
		panel.getActionMap().put("jump", jump);
		panel.getActionMap().put("stop_left", stop_left);
		panel.getActionMap().put("stop_right", stop_right);
		panel.getActionMap().put("attack", attack);
		panel.getActionMap().put("pause", pause);
		/* End bindings */
	}
	/**
	 * Brings up pause menu
	 */
	public void pause() {
		if (currentScreen == menu || currentScreen == splash)
			return;
		else if (!paused) { //pause -- adds pause menu
			content.add(pausePanel, BorderLayout.SOUTH);
			paused = true;
			rightHeld = false;
			leftHeld = false;
			//stopSound(MENU_MUSIC);
		}
		else if (paused) { //unpause -- removes pause menu
			paused = false;
			content.remove(pausePanel);
			content.remove(inventoryPanel);
			//playSound(MENU_MUSIC);
		}
	}

	/**
	 * Arduino controller code -- not fully implemented yet -- please use keyboard input only
	 */
	public void initializeController() {
		cL = new ControllerLiason(this);
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

	/**
	 * Main thread
	 */
	public void run() {
		NoRepaintsManager.setAsManager();

		splash = new SplashScreen(this); //Splash screen is the first screen that's loaded
		currentScreen = splash;

		menu = new MainMenu(this);

		frame = screenManager.getFullScreenWindow();

		content = frame.getContentPane();
		((JComponent)content).setOpaque(false);
		panel = new JPanel(new GridLayout(1,3));

		content.setLayout(new BorderLayout());
		content.add(panel,BorderLayout.SOUTH); //Adds panel to screen


		for (JComponent c : menu.components) { //Add main menu components to panel
			//Add them to the window
			panel.add(c);
		}

		pausePanel = new JPanel(new GridLayout(1,1)); //Panel for pause menu
		inventoryPanel = new JPanel(new GridLayout(1,1)); //Panel for inventory menu

		boolean b = false;
		while(gameRunning) { // Game loop
			if (paused) { //Doesn't redraw while game is paused -- just draws components
				frame.validate(); 
				Graphics2D g = screenManager.getGraphics();
				screenManager.getFullScreenWindow().getLayeredPane().paintComponents(g);
				g.dispose();
				screenManager.update();
				if (pause.isMenuButtonPressed()) { // checks for main menu button press
					for (JComponent c : menu.components) 
						panel.add(c); //adds components back to main panel
					content.remove(pausePanel); // removes pause panel from screen
					/* Remove then add main panel -- for some reason this fixes an issue we had */
					content.remove(panel); 
					content.add(panel, BorderLayout.SOUTH);
					paused = false;
					currentScreen = menu;
					gameStarted = false;
				}
				else if(pause.isInventoryButtonPressed()){ // checks for inventory button press -- brings up inventory
					inventory = pause.getInventory();
					inventoryPanel.removeAll(); //Makes sure that we don't add a second copy of inventory
					if(inventory != null) // Catches potential error
						inventoryPanel.add(inventory);
					content.add(inventoryPanel, BorderLayout.NORTH);
					pause.isInventoryButtonPressed = false;
					//TODO Inventory
				}
				else if (pause.isResumeButtonPressed) { //Resumes game -- can also press esc
					paused = false;
					content.remove(pausePanel);
					content.remove(inventoryPanel);
					pause.isResumeButtonPressed = false;
				}
				else if (pause.isMusicButtonPressed) { //Stops music
					if (musicIsPlaying) {
						stopSound(MENU_MUSIC);
						initializeSoundEngine(); //Resets sound engine
						musicIsPlaying = false;
						pause.isMusicButtonPressed = false;
					}
					else {
						playSound(MENU_MUSIC);
						musicIsPlaying = true;
						pause.isMusicButtonPressed = false;
					}
				}
			}
			else {
				frame.validate();

				Graphics2D g = screenManager.getGraphics();
				currentScreen.draw(g);

				//tell Swing that it is time to update
				if(currentScreen != splash)
					screenManager.getFullScreenWindow().getLayeredPane().paintComponents(g);

				g.dispose();
				screenManager.update();

				Screen nextScreen = currentScreen.nextScreen(); // asks for next screen
				if(nextScreen== null) System.out.println(nextScreen != null);
				if (nextScreen != null && nextScreen != currentScreen) 
					currentScreen = nextScreen;
				if(b == false){
					initializeKeyboard(); 
					b = true;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		screenManager.restoreScreen();
		System.exit(0);
	}

	public void initializeSoundEngine() { //loads sounds
		sfx.clear();
		//sfx.add(new SoundClip("sounds/whisper.wav"));
		sfx.add(new SoundClip("sounds/main.wav"));
		//sfx.add(new SoundClip("sounds/ambient.wav"));
		sfx.add(new SoundClip("splash/splash.wav"));
		/*sfx.add(new SoundClip("path/to/damage/sound"));
		sfx.add(new SoundClip("path/to/movement/sound"));
		sfx.add(new SoundClip("path/to/jump/sound")); */
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menu.quit)) {
			gameRunning = false;
			//System.exit(0);
		}
		else if (e.getSource().equals(menu.start)) { //Starts game
			//playSound(AMBIENT_MUSIC_1);
			if (currentMap == null)
				currentMap = new Map(screenManager, menu.difficultySlider.getValue());
			Map.rooms_passed = 0;
			pause = new PauseMenu(currentMap.player, screenManager.getFirstThirdX(), screenManager.getFirstThirdY(), screenManager.getFirstThirdX(), screenManager.getFirstThirdY());
			pausePanel.removeAll();
			pausePanel.add(pause);
			panel.removeAll();
			gameStarted = true;
		}
		else if (e.getSource().equals(menu.music)){
			if (musicIsPlaying) {
				stopSound(MENU_MUSIC);
				initializeSoundEngine();
				musicIsPlaying = false;
			}
			else {
				playSound(MENU_MUSIC);
				musicIsPlaying = true;
			}
		}
		else if (e.getSource().equals(menu.info)) {
			if (!menu.drawInfo)
				menu.drawInfo = true;
			else
				menu.drawInfo = false;
		}
	}

	public void playSound(int sound) {
		sfx.get(sound).play();
	}

	public void stopSound(int sound) {
		sfx.get(sound).stop();
		sfx.get(sound).close();
	}

	/**
	 * Passes keystrokes to player
	 * @param i
	 */
	public void movePlayer(int i) {
		if (currentScreen == this.menu) {
			//BUTTONS
		}
		else 
			if (i == MOVE_RIGHT) {
				//currentMap.player.setXVelocity(10);
				rightHeld = true;
				currentMap.player.setXVelocity(10);
			}
			else if (i == MOVE_LEFT) {
				//currentMap.player.setXVelocity(-10);
				leftHeld = true;
				currentMap.player.setXVelocity(-10);
			}
			else if (i == JUMP) {
				currentMap.player.jump();
			}
			else if(i == STOP_LEFT){
				leftHeld = false;
				if (rightHeld == true)
					currentMap.player.setXVelocity(10);
				else
					currentMap.player.setXVelocity(0);
			}
			else if(i == STOP_RIGHT){
				rightHeld = false;
				if (leftHeld == true)
					currentMap.player.setXVelocity(-10);
				else
					currentMap.player.setXVelocity(0);
			}
	}

}
