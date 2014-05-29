import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MovableSprite{
	private Weapon weapon;
	private Armor armor;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private boolean attacking = false;
	private final int baseHealth;
	private long startTime;
	private BufferedImage imageReversed;
	private BufferedImage imageNormal;
	public Player(int x, int y, int width, int height, int health, String fileName, Weapon weapon, Armor armor) {
		super(x, y, width, height, health, fileName);
		this.weapon = weapon;
		setArmor(armor);
		baseHealth = health;
		imageReversed = flip(image);
		imageNormal = image;
	}

	public void addItem(Item i){
		inventory.add(i);
	}

	public ArrayList<Item> getInventory(){
		return inventory;
	}

	public void setWeapon(Weapon w){
		inventory.add(w);
		weapon = w;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public Armor getArmor(){
		return armor;
	}

	public void setArmor(Armor a){
		inventory.add(a);
		armor = a;
		health = baseHealth + armor.getStrength();
	}

	public void setXVelocity(int xvel){
		if(xvel == 0){
			xVelocity = 0;
			return;
		}
		if(xvel < 0)
			image = imageReversed;
		else if(xvel > 0)
			image = imageNormal;
		xVelocity = (Math.abs(xvel)-armor.getWeight())*(xvel/Math.abs(xvel));
	}

	public void attack(){
		attacking = true;
		startTime = System.currentTimeMillis();
	}

	public BufferedImage flip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(attacking){
			Rectangle attack = weapon.attack();
			if(image == imageReversed && attack != null)
				attack.x = attack.x-this.width-attack.width-3;
			for(Sprite sprite : sprites){
				if(sprite.intersects(attack) && sprite instanceof Enemy){
					((Enemy)sprite).hurt(weapon.getDamage());
				}
			}
			if(attack != null){
				g.setColor(Color.MAGENTA);
				g.fillRect(attack.x, attack.y, attack.width, attack.height);
			}
		}
		if(System.currentTimeMillis() - startTime  >= 100){
			attacking = false;
			startTime = 0;
		}
		super.draw(g, sprites);
	}
}
