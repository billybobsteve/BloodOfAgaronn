import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

//a graphical pause menu
public class PauseMenu extends JPanel implements ActionListener{
	JButton resume = new JButton("Resume");
	JButton inventory = new JButton("Inventory");
	JButton music = new JButton("Music On/Off");
	JButton mainMenu = new JButton("Main Menu");
	Player p;
	Inventory i;
	boolean isMenuButtonPressed, isResumeButtonPressed, isMusicButtonPressed, isInventoryButtonPressed; //booleans for the pressed buttons
	public PauseMenu(Player p, int x, int y, int width, int height){
		this.p = p;
		setBounds(x, y, width, height);
		setLayout(new GridLayout(1,4));
		add(resume);
		add(inventory);
		add(music);
		add(mainMenu);
		resume.addActionListener(this);
		inventory.addActionListener(this);
		music.addActionListener(this);
		mainMenu.addActionListener(this);
	}
	
	public void paint(Graphics g){
		super.paint(g);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume){
			isResumeButtonPressed = true;
		}
		else if(e.getSource() == inventory){
			isInventoryButtonPressed = true;
			i = new Inventory(0,0,300,500,p); //create a new inventory
		}
		else if(e.getSource() == music){
			isMusicButtonPressed = true;
		}
		else if(e.getSource() == mainMenu){
			isMenuButtonPressed = true;
		}
	}
	
	public Inventory getInventory(){
		return i;
	}
	
	public boolean isMenuButtonPressed(){
		return isMenuButtonPressed;
	}
	
	public boolean isResumeButtonPressed(){
		return isResumeButtonPressed;
	}
	
	public boolean isMusicButtonPressed(){
		return isMusicButtonPressed;
	}
	
	public boolean isInventoryButtonPressed(){
		return isInventoryButtonPressed;
	}
}
