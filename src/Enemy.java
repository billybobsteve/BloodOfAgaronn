import java.awt.Graphics;
import java.util.ArrayList;

//an enemy
public class Enemy extends MovableSprite {
	protected int damage;
	protected boolean activated = false, bounce = false;
	protected long bounceStart = 0;
	public Enemy(int x, int y, int width, int height, int health, String fileName, int damage) {
		super(x, y, width, height, health, fileName);
		this.damage = damage;
	}
	
	//"bounce" away from the player
	public void bounce(){
		bounce = true;
		bounceStart = System.currentTimeMillis();
		this.jump();
	}
	
	public boolean isBounce(){
		return bounce;
	}
	
	public boolean isActive(){
		return activated;
	}
	
	public void activate(){
		activated = true;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(bounce && System.currentTimeMillis() - bounceStart > 1500)
			bounce = false;
		super.draw(g, sprites);
	}
	
}
