import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends MovableSprite{
	private Weapon weapon;
	private Armor armor;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private boolean attacking = false;
	private int baseHealth;
	public Player(int x, int y, int width, int height, String fileName,Weapon weapon,Armor armor) {
		super(x, y, width, height, fileName);
		this.weapon = weapon;
		setArmor(armor);
		baseHealth = health;
	}
	
	public void setWeapon(Weapon w){
		weapon = w;
	}
	
	public void setArmor(Armor a){
		armor = a;
		health = baseHealth + armor.getStrength();
	}
	
	public void setXVelocity(int xvel){
		xVelocity = xvel-armor.getWeight();
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
