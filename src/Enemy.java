
public class Enemy extends MovableSprite {
	protected int damage;
	public Enemy(int x, int y, int width, int height, String fileName, int damage) {
		super(x, y, width, height, fileName);
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
}
