import java.awt.Graphics;
import java.util.ArrayList;

public class MovableSprite extends Sprite{
	int xVelocity, yVelocity, ticks;
	public MovableSprite(int x, int y, int width, int height, String fileName){
		super(x,y,width,height,fileName);
	}
	
	public void jump(){
		yVelocity=3;
	}
	
	public void setXVelocity(int xvel){
		xVelocity = xvel;
	}
	
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		x+=xVelocity;
		y+=yVelocity;
		if(yVelocity!=0){
			
		}
		super.draw(g, sprites);
	}
}
