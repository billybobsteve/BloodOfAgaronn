import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Sprite {
	protected int x, y, width, height;
	protected BufferedImage image;
	protected boolean hidden = false;
	public Sprite(int x, int y, int width, int height, String fileName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if(fileName != null)
			try {
				image = ImageIO.read(new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public BufferedImage getImage(){
		return image;
	}
	public void setHidden(boolean h){
		hidden = h;
	}
	public boolean intersects(Sprite s){
		if(!hidden)
			return new Rectangle(x,y,width,height).intersects(new Rectangle(s.x,s.y,s.width,s.height));
		return false;
	}
	public boolean intersects(Rectangle r){
		if(!hidden)
			return new Rectangle(x,y,width,height).intersects(r);
		return false;
	}
	public void draw(Graphics g, ArrayList<Sprite> sprites){
		if(!hidden && image != null)
			g.drawImage(image, x, y, width, height, null);
	}

}
