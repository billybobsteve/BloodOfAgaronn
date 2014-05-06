import java.awt.Graphics;

public abstract class Screen{
	public Screen(){

	}
	
	public abstract void draw(Graphics g);
	public abstract Screen successor();
}
