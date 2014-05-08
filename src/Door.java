
public class Door extends Sprite {
	
	Room linkingRoom;
	public Door(int x, int y, int width, int height, String fileName, Room r) {
		super(x, y, width, height, fileName);
		linkingRoom = r;
		
	}
	

}
