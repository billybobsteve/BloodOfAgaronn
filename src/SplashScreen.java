import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;


public class SplashScreen extends Screen {

	Main main;
	long timeSinceStart = System.currentTimeMillis();
	static final long DELAY = 3000;

	public SplashScreen(Main m) {
		main = m;
	}

	@Override
	public void draw(Graphics g) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new FileInputStream("splash_screen.png"));
		} catch (Exception e) { e.printStackTrace(); }

		g.drawImage(image, 0, 0, null); 
	}

	public Screen nextScreen() {
		if (System.currentTimeMillis() - timeSinceStart > DELAY) {
			main.menu = new MainMenu(main);
			return main.menu;
		}
		return this;
	}

}
