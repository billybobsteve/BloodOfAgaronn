import java.awt.Graphics;
import java.util.ArrayList;

public class Room extends Screen{
	private ArrayList<Sprite> sprites;
	private Room parentRoom;
	ArrayList<Door> doors; 
	Player player;
	EnemyControl ec;
	
	
	public Room(){
	
	}
	
	public Room(ArrayList<Door> d, Player p, Room pr, ArrayList<Sprite> s, EnemyControl c){
		doors = d;
		player = p;
		parentRoom = pr;
		sprites = s;
		ec = c;
		ec.setList(generateEnemies(10));
	}
	
	public ArrayList<Enemy> generateEnemies(int n){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int i = 0; i<n; i++){
			enemies.add(new Enemy(1,1,100,100,50,null,20));
		}
		return enemies;
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
		return parentRoom;
	}
	
	public void setDoors(ArrayList<Door> list){
		doors = list;
	}

	public Room nextScreen() {
		for(Door d : doors){
			if(player.intersects(d)){
				Room temp = d.getLinkingRoom();
				d.setLinkingRoom(this);
				return temp;
			}
		}
		return this;
	}
	
}
