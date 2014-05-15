import java.util.ArrayList;


public class Map {
	static Player player;
	static Room startingRoom;
	static int counter = 0;
	public Map(Player p, Room original){
		player = p;
		startingRoom = original;
	}

	public static Room generateMap(Room original, int n){
		if(n>10)
			return original;
		generateNextLevel(original);
		return generateMap(original, n+1);
	}

	public static void generateNextLevel(Room r){
		for(Door d : r.doors){
			Room f = new Room(null, player, r,null);
			counter++;
		}
	}

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



}
