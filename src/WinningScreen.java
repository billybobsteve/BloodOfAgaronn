import java.awt.Color;
import java.awt.Graphics;


public class WinningScreen extends Screen { 
	ScreenManager manager;
	String endText;
	public WinningScreen(ScreenManager m, String t){
		manager = m;
		endText = t;
	}

	//creates an endgame screen that contains parameter message and credits
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,manager.getWidth(),manager.getHeight());
		g.setColor(Color.MAGENTA);
		g.drawString(endText,(manager.getWidth()/2)-200,  manager.getHeight()/2);
		g.drawString("Credits: ",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+100);
		g.drawString("Programmers: Chase Goddard, Thomas Ragucci, and Nathan Spring",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+100);
		g.drawString("Drawer of Title Screen: Acacia Dougan",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+150);
		g.drawString("Special Thanks to our Lord and Savior Andrew \"Does That Make Sense\" Burke for a stock photo",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+200);
		g.drawString("and imparting his infinte pool of technical knowledge in order for us to complete this masterpiece.",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+250);
		g.drawString("Bless Him.",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+300);
	}

	
	public Screen nextScreen() {
		return null;
	}

}
