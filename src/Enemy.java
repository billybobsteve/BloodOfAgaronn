
public class Enemy extends MovableSprite {
	protected int damage;
	protected boolean activated = false;
	public Enemy(int x, int y, int width, int height, int health, String fileName, int damage) {
		super(x, y, width, height, health, fileName);
		this.damage = damage;
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
	
}
