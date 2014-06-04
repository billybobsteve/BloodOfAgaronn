import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

//a sprite that moves with simulated physics
public class MovableSprite extends Sprite{
	protected int xVelocity, yVelocity, health, jumpVelocity;
	protected boolean jumpHeightReached = false, jumping = false;
	protected long lastTimeHurt = System.currentTimeMillis();
	public MovableSprite(int x, int y, int width, int height, int health, String fileName){
		super(x,y,width,height,fileName);
		jumpVelocity = -25;
		this.health = health;
	}
	
	//jump by setting a y velocity
	public void jump(){
		if(!jumping){
			yVelocity=jumpVelocity;
			jumpHeightReached = false;
			jumping = true;
		}
	}

	//set x velocity
	public void setXVelocity(int xvel){
		xVelocity = xvel;
	}

	public int getHealth(){
		return health;
	}

	//do damage if the sprite has not taken damage recently
	public void hurt(int h){
		if (this instanceof Player)
			Main.pcDamageSound();
		else
			Main.npcDamageSound();
		if(System.currentTimeMillis() - lastTimeHurt > 500){
			health-=h;
			lastTimeHurt = System.currentTimeMillis();
		}
	}

	//draw the sprite and simulate physics
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		int tempx = x;
		int tempy = y;
		x+=xVelocity;
		y+=yVelocity;
		for(Sprite sprite : sprites){
			if(this.intersects(sprite) && !(sprite instanceof MovableSprite || sprite instanceof Door || sprite instanceof Item)){
				if(!(sprite.intersects(new Rectangle(x,y,this.width-xVelocity,this.height)))){
					x = tempx;
					xVelocity = 0;
				}
				if(!(sprite.intersects(new Rectangle(x,y,this.width,this.height-yVelocity)))){
					y = tempy;
					yVelocity = 1;
					jumping = false;
				}
				super.draw(g, sprites);
				return;
			}
			else if(this.intersects(sprite) && sprite instanceof Enemy && this instanceof Player){
				this.hurt(((Enemy)sprite).getDamage());
			}
		}
		if(yVelocity!=0 && !jumpHeightReached){
			yVelocity+=1;
			if(yVelocity == 0){
				jumpHeightReached = true;
			}
		}
		else if(yVelocity !=0){
			yVelocity+=1;
		}
		else
			yVelocity = 1;
		super.draw(g, sprites);
	}
}
