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

		this.manager = manager;
		doors = d;
		player = p;
		parentRoom = pr;
		sprites = s;
		ec = c;
		ec.setList(generateEnemies(3));
		sprites.addAll(ec.getEnemies());

	}
	
	public void setEnemies(ArrayList<Enemy> list){
		ec.setList(list);
	}

	public ArrayList<Enemy> generateEnemies(int n){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int i = 0; i<n; i++){
			enemies.add(new Enemy((200*i)+100,100,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		}
		return enemies;
	}

	public ArrayList<Enemy> generateStartingEnemies(){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int i = 0; i<3; i++){
			enemies.add(new Enemy(200+(i*300),50,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		}

		enemies.add(new Enemy(500,manager.getHeight()-Map.FLOOR_HEIGHT-Map.ENEMY_HEIGHT,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		enemies.add(new Enemy(900,manager.getHeight()-Map.FLOOR_HEIGHT-Map.ENEMY_HEIGHT,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		sprites.addAll(enemies);
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
		ec.moveAll();
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
				if(d.getX() > manager.getWidth()/2){
					d.setX(0);
					player.setX(d.getX()+200);
					player.setY(d.getY());
				}
				else{
					d.setX(manager.getWidth()-Map.DOOR_WIDTH);
					player.setX(d.getX()-200);
					player.setY(d.getY());
				}
				player.setX(200);
				d.setLinkingRoom(this);
				return temp;
			}
		}
		return this;
	}

}
