import java.util.ArrayList;


public class Map {
	Player player;
	Room startingRoom;
	public Map(Player p, Room original){
		player = p;
		startingRoom = original;
	}

	public Room generateMap(Room original, int n){
		if(n<10)
			return original;
		else if(original.doors.get(1).linkingRoom == null){
			generateNextLevel(original.doors.get(1).linkingRoom);
			return generateMap(original.doors.get(1).linkingRoom, n+1);
		}
		else if(original.doors.get(2).linkingRoom == null){
			generateNextLevel(original.doors.get(2).linkingRoom);
			return generateMap(original.doors.get(2).linkingRoom, n+1);
		}
		else{
			return null;
		}
	}
	
	public void generateNextLevel(Room r){
		for(Door d : r.doors){
			Room f = new Room(null, player, r,null);
		}
	}
	
	public static void main(String[] args){
		ArrayList<Door> doors = new ArrayList<Door>();
		Room original = new Room(null, null, null, null);
		doors.add(new Door(1,1,1,1, null,original));
		doors.add(new Door(1,1,1,1, null,original));
		Map map = new Map(null, original);
		
	}
	
	
	
}
