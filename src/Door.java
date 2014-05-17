
public class Door extends Sprite {
	
	private Room linkingRoom;
	public Door(int x, int y, int width, int height, String fileName, Room r) {
		super(x, y, width, height, fileName);
		linkingRoom = r;
		
	}
	
	public Room getLinkingRoom(){
		return linkingRoom;
	}
	
	public void setLinkingRoom(Room r){
		linkingRoom = r;
	}
	
	
	

}
