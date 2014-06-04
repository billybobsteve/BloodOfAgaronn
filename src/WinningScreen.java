import java.awt.Color;
import java.awt.Graphics;


public class WinningScreen extends Screen { 
	ScreenManager manager;
	String endText;
	int victoryOrDefeat; // 1 = victory 2 = defeat
	public WinningScreen(ScreenManager m, String t, int s){
		if (s == 1)
			Main.victorySound();
		else if (s == 2) 
			Main.defeatSound();
		manager = m;
		endText = t;
		victoryOrDefeat = s;
	}

	//creates an endgame screen that contains parameter message and credits
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,manager.getWidth(),manager.getHeight());
		g.setColor(Color.MAGENTA);
		g.drawString(endText,(manager.getWidth()/2)-200,  manager.getHeight()/2);
		g.drawString("Credits: ",(manager.getWidth()/2)-200 , (manager.getHeight()/2)+50);
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
