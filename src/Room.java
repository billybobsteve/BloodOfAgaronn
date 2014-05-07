import java.awt.Graphics;
import java.util.ArrayList;


public class Room extends Screen{
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Room parentRoom;
	ArrayList<Door> doors = new ArrayList<Door>(); 
	Player player = new Player();
	
	
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

	public Room backtrack(Room original, Room r){
		if(r.parentRoom == original){
			return r;
		}
		else{
			return backtrack(original, r.parentRoom);
		}

	}
	

	@Override
	public Room nextScreen() {
		
	}
	
	

}
