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
	boolean gameRunning = true;
	Thread init;
	ControllerLiason cL;
	Player pc;
	Map currentMap;
	static Thread thread;
	JFrame frame;
	Container content;
	JPanel panel;
	boolean leftHeld = false;
	boolean rightHeld = false;

	ArrayList<SoundClip> sfx = new ArrayList<SoundClip>();

	public static final int MENU_MUSIC = 0;
	public static final int DAMAGE_SOUND = 1;
	public static final int MOVEMENT_SOUND = 2;
	public static final int JUMP_SOUND = 3;
	//TODO add more sounds

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
		screenManager.getFullScreenWindow().enableInputMethods(true);
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),"right");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "jump");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stop_right");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stop_left");
		panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "attack");
		panel.getActionMap().put("right", right);
		panel.getActionMap().put("left", left);
		panel.getActionMap().put("jump", jump);
		panel.getActionMap().put("stop_left", stop_left);
		panel.getActionMap().put("stop_right", stop_right);
		panel.getActionMap().put("attack", attack);
	}
	
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
		frame = screenManager.getFullScreenWindow();

		content = frame.getContentPane();
		((JComponent)content).setOpaque(false);
		panel = new JPanel(new GridLayout(1,3));

		content.setLayout(new BorderLayout());
		content.add(panel,BorderLayout.SOUTH);


		components = currentScreen.getJComponentsToDraw();
		for (JComponent c : components) {
			//Add them to the window
			panel.add(c);
		}
		boolean b = false;
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

			//tell Swing that it is time to update
			if(currentScreen == menu)
				screenManager.getFullScreenWindow().getLayeredPane().paintComponents(g);

			g.dispose();
			screenManager.update();

			Screen nextScreen = currentScreen.nextScreen();
			if(nextScreen== null) System.out.println(nextScreen != null);
			if (nextScreen != null && nextScreen != currentScreen) 
				currentScreen = nextScreen;
			if(b == false){
				initializeKeyboard();
				b = true;
			}
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			currentMap = new Map(screenManager);
		}
	}

	public void playSound(int sound) {
		sfx.get(sound).play();
	}

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
/*
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("test");
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 37)
			this.movePlayer(this.MOVE_LEFT);
		else if (e.getKeyCode() == 39)
			this.movePlayer(this.MOVE_RIGHT);
		else if (e.getKeyCode() == 38)
			this.movePlayer(this.JUMP);
		//TODO CROUCH
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("test");
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 37)
			this.movePlayer(this.MOVE_LEFT);
		else if (e.getKeyCode() == 39)
			this.movePlayer(this.MOVE_RIGHT);
		else if (e.getKeyCode() == 38)
			this.movePlayer(this.JUMP);
		//TODO CROUCH
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("test");

	}
*/

}
