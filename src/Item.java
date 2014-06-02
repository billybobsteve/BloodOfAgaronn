import java.awt.Graphics;
import java.util.ArrayList;

public class Item extends Sprite{
	protected Sprite parent;
	protected String name;
	public Item(int x, int y, int width, int height, String fileName, String name) {
		super(x, y, width, height, fileName);
		this.name = name;
	}	
	public Sprite parent(){
		return parent;
	}
	public void setParent(Sprite s){
		parent = s;
	}
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(parent != null){
			this.x = parent.getX()+parent.getWidth()/2;
			this.y = parent.getY()-parent.getHeight()/2+20;
		}
		super.draw(g, sprites);
	}
	public String toString(){
		return name;
	}
}
