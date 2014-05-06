import java.awt.Graphics;
import java.util.ArrayList;


public class Room extends Screen{
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	public Room(){
		
	}
	
	public void addSprite(Sprite s){
		sprites.add(s);
	}
	
	public ArrayList<Sprite> getSprites(){
		return sprites;
	}

	public void draw(Graphics g){
		for(Sprite sprite : sprites){
			sprite.draw(g,sprites);
		}
	}

	public Screen successor(){
		return null;
	}

}
