import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Main implements Runnable {

	Screen currentScreen;
	ScreenManager screenManager;
	boolean gameRunning = true;

	public static void main(String[] args) {
		Thread t = new Thread(new Main());
		t.start();
	}

	public Main() {
		screenManager = new ScreenManager();
		screenManager.setFullScreen(screenManager.getCurrentDisplayMode());
	}

	public void run() {
		NoRepaintsManager.setAsManager();
				
		//Make some Swing components
		JLabel label = new JLabel("Welcome!", SwingConstants.CENTER);
		JButton button = new JButton("CLICK");
		
		Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		button.setCursor(c);
		//button.addActionListener();
		button.setBorder(null);
		button.setToolTipText("This is helpful!");
		
		//Add them to the window
		JFrame frame = screenManager.getFullScreenWindow();
		Container content = frame.getContentPane();
		((JComponent)content).setOpaque(false);
		
		content.setLayout(new BorderLayout());
		content.add(label, BorderLayout.CENTER);
		content.add(button, BorderLayout.CENTER);
		frame.validate();
		
		while(gameRunning) {
			Graphics2D g = screenManager.getGraphics();
			//currentScreen.draw(g);
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());
						
			BufferedImage image = null;
			
			try {
				image = ImageIO.read(new FileInputStream("splash_screen.png"));
			} catch (Exception e) { e.printStackTrace(); }
			
			g.drawImage(image, 0, 0, null); 
			
			//tell Swing that it is time to update
			screenManager.getFullScreenWindow().getLayeredPane().paintComponents(g);
			
			g.dispose();
			screenManager.update();
		}
		screenManager.restoreScreen();
	}
	
	public void setScreen(Screen screen) {
		currentScreen = screen;
	}
	
}
