import java.awt.Graphics;
import java.util.ArrayList;

public class MovableSprite extends Sprite{
	int xVelocity, yVelocity;
	boolean jumpHeightReached = false;
	public MovableSprite(int x, int y, int width, int height, String fileName){
		super(x,y,width,height,fileName);
	}
	
	public void jump(){
		yVelocity=10;
		jumpHeightReached = false;
	}
	
	public void setXVelocity(int xvel){
		xVelocity = xvel;
	}
	
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		int tempx = x;
		int tempy = y;
		x+=xVelocity;
		y+=yVelocity;
		for(Sprite sprite : sprites){
			if(this.intersects(sprite)){
				x = tempx;
				y = tempy;
				xVelocity = 0;
				yVelocity = 0;
				super.draw(g, sprites);
				return;
			}
		}
		if(yVelocity!=0 && !jumpHeightReached){
			 yVelocity-=1;
			 if(yVelocity == 0){
				 jumpHeightReached = true;
			 }
		}
		else if(yVelocity !=0){
			yVelocity-=1;
		}
		super.draw(g, sprites);
	}
}
