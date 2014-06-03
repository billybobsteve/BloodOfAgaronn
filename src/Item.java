import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
			if(parent instanceof Player){
				if(((Player)parent).isFlipped()){
					image = reversedImage;
					modifier = -parent.getWidth();
				}
				else{
					image = normalImage;
					modifier = parent.getWidth()/2;
				}
				this.x = parent.getX()+modifier;
				this.y = parent.getY()-parent.getHeight()/2+20;
			}
		}
		super.draw(g, sprites);
	}
	public String toString(){
		return name;
	}
}
