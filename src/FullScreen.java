import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferStrategy;


public class FullScreen {

	public static void main(String[] args) {
		new FullScreen();
	}
	
	public FullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice(); //Gives access to GPU
		GraphicsConfiguration gc = device.getDefaultConfiguration();
		
		Window window = new Window(null, gc); //null would be the frame that "owns" it
		window.setIgnoreRepaint(true);
		device.setFullScreenWindow(window);
		
		Rectangle bounds = window.getBounds();
		window.createBufferStrategy(2);
		BufferStrategy strategy = window.getBufferStrategy();
		
		Graphics g = strategy.getDrawGraphics();
		if (!strategy.contentsLost()) {
			//drawing in here
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, bounds.width, bounds.height);
			
			strategy.show();
			g.dispose();
		}
		
		try { Thread.sleep(3000); }
		catch(Exception e) {}
		
		window.dispose();
		device.setFullScreenWindow(null);
		
	}

}
