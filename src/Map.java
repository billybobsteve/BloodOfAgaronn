import java.awt.Rectangle;
import java.util.ArrayList;


public class Map {

	public static final int FLOOR_HEIGHT = 49;
	public static final int FLOOR_WIDTH = 256;
	public static final int PLAYER_HEIGHT = 148;
	public static final int PLAYER_WIDTH = 123;
	public static final int ENEMY_HEIGHT = 150;
	public static final int ENEMY_WIDTH = 100;
	public static final int DOOR_HEIGHT = 221;
	public static final int DOOR_WIDTH = 187;
	
	Room currRoom;
	ScreenManager manager = new ScreenManager();
	Weapon defaultWeapon = new Weapon(5,0,30,60,null, new Rectangle(5,0,30,60),1000,"NailBiter", 25);
	Armor defaultArmor = new Armor(0,0,100,100,null,20,1);
	Player player = new Player(0,0,100,100, 100,"DudeBroDude.png", defaultWeapon,defaultArmor);
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	EnemyControl ec = new EnemyControl(enemies, player, manager.getFractionOfScreenX(.2), manager.getFractionOfScreenX(.01));
	ArrayList<Door> doors = new ArrayList<Door>(); 
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	Room startingRoom;
	public Map(){
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT),DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,50,DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		startingRoom = RoomGenerator.getStartingRoom(doors, player, null, ec ,manager);
		startingRoom.setDoors(doors);
		
		generateMap(startingRoom, 0);
		currRoom = startingRoom;
	}

	public void generateMap(Room original, int n){
		if(n>3)
			return;
		generateNextLevel(original);
		for(int i = 0; i<original.doors.size(); i++){
			//if(original.doors.get(i).getLinkingRoom() != original)
				generateMap(original.doors.get(i).getLinkingRoom(), n+1);
		}
	}

	public void generateNextLevel(Room r){
		for(Door d : r.doors){
			ArrayList<Door> doors = new ArrayList<Door>();
			doors.add(d);
			doors.add(new Door(100,200,187,201,"DoorManBro.png", null));
			doors.add(new Door(400,400,187,201,"DoorManBro.png", null));
			Room f = RoomGenerator.getRandomRoom(doors, player, r, ec, manager);
			d.setLinkingRoom(f);

		}
	}

	public Room getNextRoom(){
		return currRoom.nextScreen();
	}

	public void setCurrRoom(Room r){
		currRoom = r;
	}

	public Player getPlayer(){
		return player;
	}

	/*
	public static void main(String[] args){
		ArrayList<Door> doors = new ArrayList<Door>();
		Room original = new Room(null, null, null, null);
		doors.add(new Door(1,1,1,1, null,original));
		doors.add(new Door(1,1,1,1, null,original));
		original.setDoors(doors);
		Map map = new Map(null, original);
		generateMap(startingRoom, 0);
		System.out.println(counter);

	}
	 */


}
