
public abstract class Item extends Sprite{
	protected Sprite parent;
	public Item(int x, int y, int width, int height, String fileName,Sprite parent) {
		super(x, y, width, height, fileName);
		this.parent = parent;
	}	
	public Sprite parent(){
		return parent;
	}
	public void setParent(Sprite s){
		parent = s;
	}
}
