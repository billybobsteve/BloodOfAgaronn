import java.awt.Rectangle;
import java.util.ArrayList;


public class Map {

	int counter = 0;
	Room currRoom;
	ScreenManager manager = new ScreenManager();
	Weapon defaultWeapon = new Weapon(5,0,30,60,null,null, new Rectangle(5,0,30,60),1000,"NailBiter", 25);
	Armor defaultArmor = new Armor(0,0,100,100,null,null,20,1);
	Player player = new Player(0,0,100,100, 100,null, defaultWeapon,defaultArmor);
	EnemyControl ec = new EnemyControl(null, player, manager.getFractionOfScreenX(.2), manager.getFractionOfScreenX(.01));
	ArrayList<Door> doors = new ArrayList<Door>(); 
	Room startingRoom = new Room(null,player,null,null,ec );
	public Map(){
		currRoom = startingRoom;
		doors.add(new Door(100,200,100,100,null, null));
		doors.add(new Door(400,400,100,100,null, null));
		startingRoom.setDoors(doors);
		generateMap(startingRoom, 0);
	}

	public Room generateMap(Room original, int n){
		if(n>10)
			return null;
		generateNextLevel(original);
		return generateMap(original, n+1);
	}

	public void generateNextLevel(Room r){
		for(Door d : r.doors){
			ArrayList<Door> doors = new ArrayList<Door>();
			doors.add(d);
			doors.add(new Door(100,200,100,100,null, null));
			doors.add(new Door(400,400,100,100,null, null));
			Room f = new Room(doors, player, r,null, new EnemyControl(null, player, manager.getFractionOfScreenX(.2),manager.getFractionOfScreenX(.01)));
			counter++;
		}
	}

	public Room getNextRoom(){
		return currRoom.nextScreen();
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
