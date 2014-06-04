import java.awt.Color;
import java.awt.Graphics;


public class WinningScreen extends Screen { 
	ScreenManager manager;
	public WinningScreen(ScreenManager m){
		manager = m;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,manager.getWidth(),manager.getHeight());
		g.setColor(Color.MAGENTA);
		g.drawString("You have slain the evil Gorabarr!  The Kingdom has been saved!",manager.getWidth()/2,  manager.getHeight()/2);
		
	}

	@Override
	public Screen nextScreen() {
		return null;
	}

}
