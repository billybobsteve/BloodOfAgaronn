
public class Armor extends Item {
	private int strength;
	private int weight;
	public Armor(int x, int y, int width, int height, String fileName, int strength, int weight) {
		super(x, y, width, height, fileName);
	}
	public int getStrength(){
		return strength;
	}
	public int getWeight(){
		return weight;
	}
}
