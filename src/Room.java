import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Room extends Screen{
	private ArrayList<Sprite> sprites;
	private Room parentRoom;
	private ScreenManager manager;
	ArrayList<Door> doors; 
	Player player;
	EnemyControl ec;
	
	
	public Room(){
	
	}
	
	public Room(ArrayList<Door> d, Player p, Room pr, ArrayList<Sprite> s, EnemyControl c, ScreenManager manager){
		doors = d;
		player = p;
		parentRoom = pr;
		sprites = s;
		ec = c;
		ec.setList(generateEnemies(10));
		this.manager = manager;
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, manager.getWidth(), manager.getHeight());
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
