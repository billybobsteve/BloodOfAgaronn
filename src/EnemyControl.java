import java.awt.Rectangle;
import java.util.ArrayList;

public class EnemyControl {
	private ArrayList<Enemy> list;
	private Player player;
	private int activationDistance, enemySpeed;
	private ArrayList<Sprite> sprites;
	public EnemyControl(ArrayList<Enemy> list, Player player, int activationDistance, int enemySpeed, ArrayList<Sprite> sprites){
		this.list = list;
		this.player = player;
		this.activationDistance = activationDistance;
		this.enemySpeed = enemySpeed;
		this.sprites = sprites;
	}
	public ArrayList<Enemy> getEnemies(){
		return list;
	}
	public void moveAll(){
		for(int i = 0;i<list.size();i++){
			Enemy e = list.get(i);
			if(e.getHealth() <= 0){
				e.setHidden(true);
				list.remove(i);
				i--;
				if(Math.random()>.5)
					sprites.add(new Weapon(e.getX(),e.getY(),30,60,"placeholder.png",new Rectangle(e.getX(),e.getY(),50,100),5,"Swwuuud", 50));
				continue;
			}
			if(!e.isBounce()){
				if(distance(player, e)<=activationDistance)
					e.activate();
				if(e.isActive()){
					int dir = 0;
					if(player.getX()-e.getX() != 0){
						dir = (player.getX()-e.getX())/Math.abs(player.getX()-e.getX());
						e.setXVelocity(enemySpeed * dir);
					}
					if(e.intersects(player)){
						e.bounce();
						e.setXVelocity(-dir * 5);
					}
				}
				if(Math.random()<.0009){
					e.jump();
				}
			}
		}
	}

	public void setList(ArrayList<Enemy> enemies){
		list = enemies;
	}
	private int distance(MovableSprite m1, MovableSprite m2){
		return (int)Math.sqrt(Math.pow(m1.getX()-m2.getX(),2)+Math.pow(m1.getY()-m2.getY(),2));
	}
}
