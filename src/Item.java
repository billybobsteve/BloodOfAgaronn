import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//an item that can be held by the player, such as a sword
public class Item extends Sprite{
	protected Sprite parent;
	protected String name;
	protected BufferedImage reversedImage;
	protected BufferedImage normalImage;
	public Item(int x, int y, int width, int height, String fileName, String name) {
		super(x, y, width, height, fileName);
		this.name = name;
		reversedImage = Player.flip(this.image);
		normalImage = image;
	}	
	public Sprite parent(){
		return parent;
	}
	public void setParent(Sprite s){
		parent = s;
	}
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(parent != null){
			int modifier = 0;
			int modifier2 = 20;
			if(parent instanceof Player){
				if(((Player)parent).isFlipped()){ //flip image if parent is flipped
					image = reversedImage;
					modifier = -parent.getWidth();
					if(this.name.equals("swwuuud")){
						modifier2 = -20;
					}
				}
				else{
					image = normalImage;
					modifier = parent.getWidth()/2;
					if(this.name.equals("swwuuud")){
						modifier2 = 10;
					}
				}
				this.x = parent.getX()+modifier;
				this.y = parent.getY()-parent.getHeight()/2+modifier2;//position image correctly
			}
		}
		super.draw(g, sprites);
	}
	public String toString(){
		return name;
	}
	public boolean equals(Object other){
		return ((Item)other).name.equals(this.name);
	}
}
