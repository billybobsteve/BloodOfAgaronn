import java.util.ArrayList;

public class EnemyControl {
	private ArrayList<Enemy> list;
	private Player player;
	private int activationDistance, enemySpeed;
	public EnemyControl(ArrayList<Enemy> list, Player player, int activationDistance, int enemySpeed){
		this.list = list;
		this.player = player;
		this.activationDistance = activationDistance;
		this.enemySpeed = enemySpeed;
	}
	public void moveAll(){
		for(int i = 0;i<list.size();i++){
			Enemy e = list.get(i);
			if(e.getHealth() <= 0){
				e.setHidden(true);
				list.remove(i);
				i--;
				continue;
			}
			if(distance(player, e)<=activationDistance)
				e.activate();
			if(e.isActive()){
				int dir = (player.getX()-e.getX())/Math.abs(player.getX()-e.getX());
				e.setXVelocity(enemySpeed * dir);
			}
			if((Math.random())<.0001){
				e.jump();
			}
		}
	}
	private int distance(MovableSprite m1, MovableSprite m2){
		return (int)Math.sqrt(Math.pow(m1.getX()-m2.getX(),2)+Math.pow(m1.getY()-m2.getY(),2));
	}
}
