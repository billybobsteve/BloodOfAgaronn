
public class Map {
	Player player;
	Room startingRoom;
	public Map(Player p, Room original){
		player = p;
		startingRoom = original;
	}

	public Room generateMap(Room original, int n){
		if(n > 10)
			return original; 
		
		for(Door d : original.doors){
			d.linkingRoom = new Room(null, player, original, null);
			d.linkingRoom = generateMap(d.linkingRoom, n+1);
		}
		
	}






}
