import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Room extends Screen{
	protected ArrayList<Sprite> sprites;
	protected Room parentRoom;
	protected ScreenManager manager;
	ArrayList<Door> doors; 
	Player player;
	EnemyControl ec;
	BufferedImage bg;

	public Room(){

	}

	//Constructor for Room, has parameters for its doors, player, all sprites that need to be displayed, enemy controller, and screen manager
	public Room(ArrayList<Door> d, Player p, ArrayList<Sprite> s, EnemyControl c, ScreenManager manager){

		this.manager = manager;
		bg = new BufferedImage(manager.getWidth(),manager.getHeight(),BufferedImage.TYPE_INT_ARGB);
		doors = d;
		player = p;
		sprites = s;
		ec = c;
		try {
			bg = ImageIO.read(new File("duck_you.png")); //background image for each room
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//sets list of enemies to a parameter list of enemies
	public void setEnemies(ArrayList<Enemy> list){
		ec.setList(list);
	}
	//adds a sprite to the list of sprites in room 
	public void addSprite(Sprite s){
		sprites.add(s);
	}
	//returns list of sprites
	public ArrayList<Sprite> getSprites(){
		return sprites;
	}
//displays the sprites in room and background image for rooms
	public void draw(Graphics g){
		g.fillRect(0, 0, manager.getWidth(), manager.getHeight());
		g.drawImage(bg, 0,0,manager.getWidth(),manager.getHeight(),null);
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
	//sets the list of doors to a parameter list of doors
	public void setDoors(ArrayList<Door> list){
		doors = list;
	}
	//determines the next screen based on player intersection/health
	public Screen nextScreen() {
		//if health is less or equal to zero, return a gameover screen with a losing message
		if(player.getHealth() <= 0){
			return new WinningScreen(manager, "Sorry, you are a big, fat, stinky looser!");
		}
		//checks intersection with each door and that all enemies in the room are dead, if so then make a new room and return that room as next room
		for(Door d : doors){
			if(player.intersects(d) && ec.getEnemies().size() <= 0){
				if(Map.rooms_passed < Map.difficulty){
					ArrayList<Door> nextDoors = new ArrayList<Door>();
					nextDoors.add(new Door(0, manager.getHeight()-(Map.DOOR_HEIGHT+Map.FLOOR_HEIGHT), Map.DOOR_WIDTH, Map.DOOR_HEIGHT, "DoorManBro.png", null));
					nextDoors.add(new Door(manager.getWidth()-Map.DOOR_WIDTH, 0, Map.DOOR_WIDTH, Map.DOOR_HEIGHT, "DoorManBro.png", null));


					Room temp = RoomGenerator.getRandomRoom(nextDoors, player, manager);

					player.setX(manager.getWidth()/2);
					player.setY(manager.getHeight()/2);

					d.setLinkingRoom(this);
					Map.rooms_passed++;
					return temp;
				}
				//if the number of rooms has been completed to enter boss room, generate boss room and return
				else{
					
					ArrayList<Door> stupidDoors = new ArrayList<Door>();
					ArrayList<Sprite> sprites = new ArrayList<Sprite>();
					ArrayList<Enemy> enemies = new ArrayList<Enemy>();
					EnemyControl ec = new EnemyControl(enemies, player, manager.getFractionOfScreenX(.4), manager.getFractionOfScreenX(.009),sprites);
					return new BossRoom(stupidDoors, player, sprites, ec, manager);
				}
			}
			
		}
		//otherwise return the same room you're currently in
		return this;
	}

}
