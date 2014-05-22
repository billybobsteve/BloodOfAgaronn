import java.awt.Rectangle;
import java.util.ArrayList;


public class Map {

	Room currRoom;
	ScreenManager manager = new ScreenManager();
	Weapon defaultWeapon = new Weapon(5,0,30,60,null, new Rectangle(5,0,30,60),1000,"NailBiter", 25);
	Armor defaultArmor = new Armor(0,0,100,100,null,20,1);
	Player player = new Player(0,0,100,100, 100,"DudeBroDude.png", defaultWeapon,defaultArmor);
	EnemyControl ec = new EnemyControl(new ArrayList<Enemy>(), player, manager.getFractionOfScreenX(.2), manager.getFractionOfScreenX(.01));
	ArrayList<Door> doors = new ArrayList<Door>(); 
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	Room startingRoom;
	public Map(){
		doors.add(new Door(100,200,100,100,null, null));
		doors.add(new Door(400,400,100,100,null, null));
		startingRoom = RoomGenerator.getRandomRoom(doors, player, null, ec ,manager);
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
			doors.add(new Door(100,200,100,100,null, null));
			doors.add(new Door(400,400,100,100,null, null));
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
