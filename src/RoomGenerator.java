import java.util.ArrayList;

public class RoomGenerator {
	public static final String floorName = "BasedCutman.png";
	public static Room getRandomRoom(ArrayList<Door> doors, Player player, Room previous, ScreenManager sm){
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		enemies = generateEnemies(3+(Map.rooms_passed/3), sm);
		EnemyControl ec = new EnemyControl(enemies, player, sm.getFractionOfScreenX(.2), sm.getFractionOfScreenX(.01),sprites);
		ec.setList(enemies);
		sprites.addAll(enemies);
		sprites.addAll(doors);
		/*
		for(Door d : doors){
			if(d.getY() < sm.getHeight()-(Map.DOOR_HEIGHT + Map.FLOOR_HEIGHT))
				sprites.add(new Sprite(d.getX()-50,d.getY()+Map.DOOR_HEIGHT,350,50,"BasedCutman.png"));
		}
		 */
		sprites.add(new Sprite(sm.getWidth(),sm.getHeight(),Map.WALL_WIDTH, Map.WALL_HEIGHT,null));
		sprites.add(new Sprite(0,sm.getHeight(),Map.WALL_WIDTH, Map.WALL_HEIGHT,null));
		int x = (int)(Math.random()*200);
		for(int i = 1; i<(int)(Math.random()*5)+4; i++){
			sprites.add(new Sprite(i*(x+200),(int)(Math.random()*300)+300,200,50,"BasedCutman.png"));
		}
		//sprites.add(new Sprite(500,425,200,50,"BasedCutman.png"));
		//sprites.add(new Sprite(200,625,200,50,"BasedCutman.png"));
		sprites.add(new Sprite((sm.getWidth()-Map.DOOR_WIDTH-Map.FLOOR_WIDTH)+100,200,350,50,"BasedCutman.png"));

		sprites.addAll(player.getInventory());

		sprites.add(player.getWeapon());
		sprites.add(player.getArmor());
		sprites.add(player);
		/*
		for(Door door : doors){
			sprites.add(new Sprite(door.getX(),door.getY()+door.getHeight(),door.getWidth(),Map.FLOOR_HEIGHT,"BasedCutman.png"));
			sprites.add(new Sprite(door.getX()-50-Map.FLOOR_WIDTH,door.getY()+door.getHeight()+Map.ENEMY_HEIGHT+20,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,"BasedCutman.png"));
		}
		 */
		for(int i = 0;i<sm.getWidth();i+=256){
			sprites.add(new Sprite(i,sm.getHeight()-Map.FLOOR_HEIGHT,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,floorName));

		}
		Room r = new Room(doors, player, previous, sprites, ec, sm);
		return r;
	}

	public static Room getStartingRoom(ArrayList<Door> doors, Player player, Room previous, ScreenManager sm){
		ArrayList<Enemy> enemies = generateStartingEnemies(sm);
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		EnemyControl ec = new EnemyControl(enemies, player, sm.getFractionOfScreenX(.2), sm.getFractionOfScreenX(.01),sprites);
		sprites.addAll(enemies);
		sprites.addAll(doors);
		sprites.add(new Sprite(800,225,200,50,"BasedCutman.png"));
		sprites.add(new Sprite(500,425,200,50,"BasedCutman.png"));
		sprites.add(new Sprite(200,625,200,50,"BasedCutman.png"));
		sprites.add(new Sprite((sm.getWidth()-Map.DOOR_WIDTH-Map.FLOOR_WIDTH)+100,200,350,50,"BasedCutman.png"));
		sprites.addAll(player.getInventory());
		sprites.add(player.getWeapon());
		sprites.add(player.getArmor());
		sprites.add(player);
		for(int i = 0;i<sm.getWidth();i+=256){
			sprites.add(new Sprite(i,sm.getHeight()-Map.FLOOR_HEIGHT,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,floorName));
		}
		Room r = new Room(doors, player, previous, sprites, ec, sm);
		return r;
	}

	public static ArrayList<Enemy> generateEnemies(int n, ScreenManager manager){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int i = 1; i<n+1; i++){
			enemies.add(new Enemy((int)(Math.random()*((manager.getWidth()/n)*i)),100,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50+10*Map.rooms_passed+(Map.difficulty/3),"DudeBroMan.png",5+3*Map.rooms_passed));
		}
		return enemies;
	}

	private static ArrayList<Enemy> generateStartingEnemies(ScreenManager manager){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int i = 0; i<3; i++){
			enemies.add(new Enemy(200+(i*300),50,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",5));
		}

		enemies.add(new Enemy(500,manager.getHeight()-Map.FLOOR_HEIGHT-Map.ENEMY_HEIGHT,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		enemies.add(new Enemy(900,manager.getHeight()-Map.FLOOR_HEIGHT-Map.ENEMY_HEIGHT,Map.ENEMY_WIDTH,Map.ENEMY_HEIGHT,50,"DudeBroMan.png",20));
		return enemies;
	}
}
