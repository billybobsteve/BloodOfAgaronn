import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Item extends Sprite{
	protected Sprite parent;
	public Item(int x, int y, int width, int height, String fileName) {
		super(x, y, width, height, fileName);
	}	
	public Sprite parent(){
		return parent;
	}
	public void setParent(Sprite s){
		parent = s;
	}
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		this.x = parent.getX();
		this.y = parent.getY();
		super.draw(g, sprites);
	}
}
