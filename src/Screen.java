import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class Screen{
	public Screen(){
		
	}
	
	public abstract void draw(Graphics g);
	public abstract Screen nextScreen();
	
	protected ArrayList<JComponent> components = new ArrayList<JComponent>();
	
	public ArrayList<JComponent> getJComponentsToDraw() {
		return components;
	}
}
