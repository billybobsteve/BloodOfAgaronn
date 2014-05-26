import java.util.ArrayList;

public class RoomGenerator {
	public static final String floorName = "BasedCutman.png";
	public static Room getRandomRoom(ArrayList<Door> doors, Player player, Room previous, EnemyControl ec, ScreenManager sm){
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.addAll(ec.getEnemies());
		sprites.addAll(doors);
		sprites.add(player);
		for(int i = 0;i<sm.getWidth();i+=256){
			sprites.add(new Sprite(i,sm.getHeight()-Map.FLOOR_HEIGHT,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,floorName));
		}
		return new Room(doors, player, previous, sprites, ec, sm);
	}
	public static Room getStartingRoom(ArrayList<Door> doors, Player player, Room previous, EnemyControl ec, ScreenManager sm){
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.addAll(ec.getEnemies());
		sprites.addAll(doors);
		sprites.add(new Sprite(800,225,200,50,"BasedCutman.png"));
		sprites.add(new Sprite(500,425,200,50,"BasedCutman.png"));
		sprites.add(new Sprite(200,625,200,50,"BasedCutman.png"));
		sprites.add(new Sprite((sm.getWidth()-Map.DOOR_WIDTH-Map.FLOOR_WIDTH)+100,200,350,50,"BasedCutman.png"));
		
		sprites.add(player);
		for(int i = 0;i<sm.getWidth();i+=256){
			sprites.add(new Sprite(i,sm.getHeight()-Map.FLOOR_HEIGHT,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,floorName));
		}
		return new Room(doors, player, previous, sprites, ec, sm);
	}
}
