import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Weapon extends Item{
	protected Rectangle attackArea;
	protected int attackSpeed;
	private int time;
	protected String name;
	protected int damage;
	public Weapon(int x, int y, int width, int height, String fileName, Rectangle attackArea, int attackSpeed, String name, int damage) {
		super(x, y, width, height, fileName);
		this.attackArea = attackArea;
		this.attackSpeed = attackSpeed;
		this.name = name;
		this.damage = damage;
	}

	public Rectangle attack(){
		if(time == 0){
			time = attackSpeed;
			return attackArea;
		}
		return null;
	}	

	public void draw(Graphics g, ArrayList<Sprite> sprites){
		System.out.println(time);
		if(time > 0)
			time--;
		if(parent != null){
			attackArea.x = parent.x + parent.width + 3;
			attackArea.y = parent.y;
		}
		super.draw(g, sprites);
	}

	public int getDamage(){
		return damage;
	}


}
