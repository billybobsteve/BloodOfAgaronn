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
		//System.out.println(sprites);

	}
	
	public void setEnemies(ArrayList<Enemy> list){
		ec.setList(list);
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
		g.setColor(Color.RED);
		g.fillRect(50, 50, 600, 50);
		g.setColor(Color.GREEN);
		g.fillRect(50, 50, (int)(600*((double)player.getHealth()/(player.getBaseHealth()+player.getArmor().getStrength()))), 50);
		ec.moveAll();
		for(Sprite sprite : sprites){
			if(sprite.getX() < 0)
				sprite.setX(5);
			else if(sprite.getX()+sprite.getWidth() > manager.getWidth())
				sprite.setX(manager.getWidth()-sprite.getWidth()-5);
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
