import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends MovableSprite{
	private Weapon weapon;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private boolean attacking = false;
	public Player(int x, int y, int width, int height, String fileName,Weapon weapon) {
		super(x, y, width, height, fileName);
		this.weapon = weapon;
	}
	
	public void setWeapon(Weapon w){
		weapon = w;
	}
	
	public void attack(){
		attacking = true;
	}
	
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(attacking){
			for(Sprite sprite : sprites){
				if(sprite.intersects(weapon.attack()) && sprite instanceof Enemy){
					((Enemy)sprite).hurt(weapon.getDamage());
				}
			}
		}
		super.draw(g, sprites);
	}
}
