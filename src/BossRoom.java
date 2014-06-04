import java.util.ArrayList;

public class BossRoom extends Room {
	Enemy boss;
	//sets up position, creates/displays boss, same as normal room w/boss enemy
	public BossRoom(ArrayList<Door> d, Player p, ArrayList<Sprite> s, EnemyControl c, ScreenManager manager){
		super(d,p,s,c,manager);
		this.doors = new ArrayList<Door>();
		sprites.addAll(player.getInventory());
		sprites.add(p.getWeapon());
		sprites.add(p.getArmor());
		sprites.add(p);
		ArrayList<Enemy> bossList = new ArrayList<Enemy>();
		boss = new Enemy(0,0,300,300,200,"boss.png",40);
		bossList.add(boss);
		this.addSprite(boss);
		c.setList(bossList);
		for(int i = 0;i<manager.getWidth();i+=256){
			this.sprites.add(new Sprite(i,manager.getHeight()-Map.FLOOR_HEIGHT,Map.FLOOR_WIDTH,Map.FLOOR_HEIGHT,"BasedCutman.png"));
		}
	}
	//determines next screen, if boss health is less/equal to zero, good gameover message, if you die bad gameover message
	public Screen nextScreen(){
		
		if(boss.health <= 0){
			return new WinningScreen(manager, "Congratulations!  You have defeated the evil Gorabarr!  The kingdom has been saved!");
		}
		
		if(player.getHealth() <= 0){
			return new WinningScreen(manager, "Sorry, you are a big, fat, stinky looser!");
		}
		
		return this;
	}
}
