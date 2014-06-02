import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;


public class SplashScreen extends Screen {

	Main main;
	long timeSinceStart = System.currentTimeMillis();
	long timeSinceLastFrame = System.currentTimeMillis();
	static final long FRAME_DELAY = 0;
	static final long DELAY = 10000;
	BufferedImage image;
	int frame = 0;

	public SplashScreen(Main m) {
		main = m;
		try {
			image = ImageIO.read(new FileInputStream("splash/splash_00000.jpg"));
		} catch (Exception e) { e.printStackTrace(); }
		main.playSound(Main.SPLASH_MUSIC);
	}

	@Override
	public void draw(Graphics g) {
		if (System.currentTimeMillis() - timeSinceLastFrame >= FRAME_DELAY) {
			try {
				if (frame / 10 < 1)
					image = ImageIO.read(new FileInputStream("splash/splash_0000" + frame + ".jpg"));
				else if (frame / 10 < 10)
					image = ImageIO.read(new FileInputStream("splash/splash_000" + frame + ".jpg"));
			} catch (Exception e) { e.printStackTrace(); }
		}

		g.drawImage(image, 0, 0, null); 
		
		frame++;

		/* try {
			image = ImageIO.read(new FileInputStream("splash_screen.png"));
		} catch (Exception e) { e.printStackTrace(); }

		g.drawImage(image, 0, 0, null);  */
	}

	public Screen nextScreen() {
		if (frame > 77) {
			main.menu = new MainMenu(main);
			return main.menu;
		}
		return this;
	}

}
