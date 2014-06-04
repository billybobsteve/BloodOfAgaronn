import java.awt.Rectangle;
import java.util.ArrayList;


public class Map {

	public static final int FLOOR_HEIGHT = 49;
	public static final int FLOOR_WIDTH = 256;
	public static final int PLAYER_HEIGHT = 105; //148
	public static final int PLAYER_WIDTH = 75; //123
	public static final int ENEMY_HEIGHT = 150;
	public static final int ENEMY_WIDTH = 100;
	public static final int WALL_WIDTH = 25;
	public static final int WALL_HEIGHT = 10000;//REALLYREALLYTALL
	public static final int DOOR_HEIGHT = 221;
	public static final int DOOR_WIDTH = 187;
	public static int difficulty = 0;
	public static int rooms_passed = 0;

	Room currRoom; //current room of game
	ScreenManager manager; 
	Weapon defaultWeapon = new Weapon(5,0,100,100,"NailBiter.png", new Rectangle(5,0,60,148),15,"NailBiter", 25); //starting sword
	Armor defaultArmor = new Armor(0,0,100,100,null,"Iron Defense", 20,1); //starting armor
	Player player;
	EnemyControl ec;
	ArrayList<Door> doors = new ArrayList<Door>(); 
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	Room startingRoom;
	//generates the starting room and its doors
	public Map(ScreenManager m, int d){
		difficulty = d;
		manager = m;
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT),DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,0,DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		player = new Player(0,0,PLAYER_WIDTH,PLAYER_HEIGHT, 1000+(difficulty*20),"DudeBroDude.png", defaultWeapon,defaultArmor);
		startingRoom = RoomGenerator.getStartingRoom(doors, player, null, manager);
		ArrayList<Room> start = new ArrayList<Room>();
		start.add(startingRoom);

		currRoom = startingRoom;
		defaultWeapon.setParent(player);
		defaultArmor.setParent(player);

	}

	public Screen getNextRoom(){
		return currRoom.nextScreen();
	}

	public void setCurrRoom(Room r){
		currRoom = r;
	}

	public Player getPlayer(){
		return player;
	}

}



