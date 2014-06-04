import java.awt.Graphics;

//a screen to display
public abstract class Screen{
	public Screen(){
		
	}
	
	public abstract void draw(Graphics g);
	public abstract Screen nextScreen(); //go to next screen
	
}
