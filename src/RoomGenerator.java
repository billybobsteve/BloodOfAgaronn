import java.util.ArrayList;

public class RoomGenerator {
	public static final String floorName = "BasedCutman.png";
	public static Room getRandomRoom(ArrayList<Door> doors, Player player, Room previous, EnemyControl ec, ScreenManager sm){
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.addAll(ec.getEnemies());
		sprites.add(player);
		for(int i = 0;i<sm.getWidth();i+=256){
			sprites.add(new Sprite(i,48,256,48,floorName));
		}
		return new Room(doors, player, previous, sprites, ec);
	}
}
