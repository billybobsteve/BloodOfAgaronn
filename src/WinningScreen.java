import java.awt.Color;
import java.awt.Graphics;


public class WinningScreen extends Screen { 
	ScreenManager manager;
	String endText;
	public WinningScreen(ScreenManager m, String t){
		manager = m;
		endText = t;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,manager.getWidth(),manager.getHeight());
		g.setColor(Color.MAGENTA);
		g.drawString(endText,manager.getWidth()/2-50,  manager.getHeight()/2);
		
	}

	@Override
	public Screen nextScreen() {
		return null;
	}

}
