import java.awt.Graphics;
import java.util.ArrayList;


public class Room extends Screen{
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Room parentRoom;
	ArrayList<Door> doors = new ArrayList<Door>(); 
	Player player;
	
	
	public Room(){
	
	}
	
	public Room(ArrayList<Door> d, Player p){
		doors = d;
		player = p;
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
		for(Door d : doors){
			if(player.intersects(d)){
				return d.linkingRoom;
			}
		}
		return null;
	}
	
	

}
