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

	Room currRoom;
	ScreenManager manager;
	Weapon defaultWeapon = new Weapon(5,0,100,100,"NailBiter.png", new Rectangle(5,0,60,148),15,"NailBiter", 25);
	Armor defaultArmor = new Armor(0,0,100,100,null,"Iron Defense", 20,1);
	Player player;
	EnemyControl ec;
	ArrayList<Door> doors = new ArrayList<Door>(); 
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	Room startingRoom;
	public Map(ScreenManager m){
		manager = m;
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT),DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		doors.add(new Door(manager.getWidth()-DOOR_WIDTH,0,DOOR_WIDTH,DOOR_HEIGHT,"DoorManBro.png", null));
		player = new Player(0,0,PLAYER_WIDTH,PLAYER_HEIGHT, 1000,"DudeBroDude.png", defaultWeapon,defaultArmor);
		startingRoom = RoomGenerator.getStartingRoom(doors, player, null, manager);
		ArrayList<Room> start = new ArrayList<Room>();
		start.add(startingRoom);

		currRoom = startingRoom;
		//System.out.println("asposing " + startingRoom.doors.get(0).getLinkingRoom().doors.get(1).getLinkingRoom().doors.size());
		defaultWeapon.setParent(player);
		defaultArmor.setParent(player);


	}
	/*
	public void generateMap(Room original, int n){
		if(n>3)
			return;
		generateNextLevel(original);
		for(int i = 0; i<original.doors.size(); i++){
			if(original != startingRoom && i!=0)
				generateMap(original.doors.get(i).getLinkingRoom(), n+1);
		}
	}
	 */
/*
	public ArrayList<Room> generateMap(ArrayList<Room> l, int n){

		ArrayList<Room> level = new ArrayList<Room>();
		for(Room r : l){
			level.addAll(successors(r));
		}
		if(n > 3)
			return level;
		ArrayList<Room> nextLevel = generateMap(level,n+1);
		for(int i = 0;i<nextLevel.size();i++){
			int j = i/2;
			for(Door d:level.get(j).doors){
				d.setLinkingRoom(nextLevel.get(i));
				i++;
			}
		}
		return level;

	}
	public ArrayList<Room> successors(Room r){
		ArrayList<Room> successors = new ArrayList<Room>();
		if(r.doors.size() == 2){
			for(int i = 0;i<2;i++){
				Door d = r.doors.get(i);
				ArrayList<Door> doors = new ArrayList<Door>();
				doors.add(d);
				doors.add(new Door(100,400,DOOR_WIDTH, DOOR_HEIGHT, "DoorManBro.png", null));
				doors.add(new Door(300,400,DOOR_WIDTH, DOOR_HEIGHT, "DoorManBro.png", null));
				Room f = RoomGenerator.getRandomRoom(doors, player, r, manager);
				successors.add(f);
				d.setLinkingRoom(f);
			}
		}
		else if(r.doors.size() == 3){
			for(int i = 1;i<3;i++){
				Door d = r.doors.get(i);
				ArrayList<Door> doors = new ArrayList<Door>();
				doors.add(d);
				doors.add(new Door(100,400,DOOR_WIDTH, DOOR_HEIGHT, "DoorManBro.png", null));
				doors.add(new Door(300,400,DOOR_WIDTH, DOOR_HEIGHT, "DoorManBro.png", null));
				Room f = RoomGenerator.getRandomRoom(doors, player, r, manager);
				successors.add(f);
				d.setLinkingRoom(f);
			}
		}
		return successors;
	}
*/
	public Screen getNextRoom(){
		return currRoom.nextScreen();
	}

	public void setCurrRoom(Room r){
		currRoom = r;
	}

	public Player getPlayer(){
		return player;
	}

	public static void setDoorPositions(Room r, ScreenManager manager){
		if(r == null) return;
		if(r.doors.size() == 2){
			Door d = r.doors.get(0);
			d.setX(0);
			d.setY(manager.getHeight()-(DOOR_HEIGHT+FLOOR_HEIGHT));
			r.doors.get(1).setX(manager.getWidth()-DOOR_WIDTH);
			r.doors.get(1).setY(manager.getHeight()-(DOOR_HEIGHT+FLOOR_HEIGHT));
			/*
			for(int i = 0;i<2;i++){
				d = r.doors.get(i);
				d.setX(i*300);
				d.setY(i*100);
				//d.setX((int)(Math.random()*manager.getWidth()));
				//d.setY((int)(Math.random()*manager.getHeight()));
				if(d.getX() > (manager.getWidth()-DOOR_WIDTH))
					d.setX(manager.getWidth()-DOOR_WIDTH);
				if(d.getY() > manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT))
					d.setY(manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT));
			} */
		}

		if(r.doors.size() == 3){
			Door d = r.doors.get(0);
			/*
			if(d.getX() > manager.getWidth()/2){
				d.setX(Math.abs(d.getX()-manager.getWidth()));
			}
			if(d.getX() < manager.getWidth()/2){
				d.setX(manager.getWidth()-d.getX());
			}
			*/
			d.setX(0);
			d.setY(manager.getHeight()-(DOOR_HEIGHT+FLOOR_HEIGHT));
			/*
			for(int i = 1;i<3;i++){
				d = r.doors.get(i);
				d.setX(i*300);
				d.setY(i*100);
				//d.setX((int)(Math.random()*manager.getWidth()));
				//d.setY((int)(Math.random()*manager.getHeight()));
				if(d.getX() > (manager.getWidth()-DOOR_WIDTH))
					d.setX(manager.getWidth()-DOOR_WIDTH);
				if(d.getY() > manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT))
					d.setY(manager.getHeight()-(DOOR_HEIGHT + FLOOR_HEIGHT));
			}
			*/
			r.doors.get(1).setX(manager.getWidth()-DOOR_WIDTH);
			r.doors.get(1).setY(manager.getHeight()-(DOOR_HEIGHT+FLOOR_HEIGHT));
			r.doors.get(2).setX(manager.getWidth()-DOOR_WIDTH);
			r.doors.get(2).setY(0);
			
		}
	}
}


