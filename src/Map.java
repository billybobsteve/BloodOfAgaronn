
public class Map {
	int numberOfRooms = 0;
	public Map(int n){
		numberOfRooms = n;
		
	}
	public Room generateRoom(){
		return new Room();
	}

}
