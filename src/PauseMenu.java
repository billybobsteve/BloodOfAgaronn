import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu extends JPanel implements ActionListener{
	JButton resume = new JButton("Resume");
	JButton inventory = new JButton("Inventory");
	JButton settings = new JButton("Settings");
	JButton mainMenu = new JButton("Main Menu");
	Player p;
	Inventory i;
	boolean isMenuButtonPressed, isResumeButtonPressed, isSettingsButtonPressed, isInventoryButtonPressed;
	public PauseMenu(Player p, int x, int y, int width, int height){
		this.p = p;
		setBounds(x, y, width, height);
		setLayout(new GridLayout(1,4));
		add(resume);
		add(inventory);
		add(settings);
		add(mainMenu);
		resume.addActionListener(this);
		inventory.addActionListener(this);
		settings.addActionListener(this);
		mainMenu.addActionListener(this);
	}
	
	public void paint(Graphics g){
		if(i != null)
			if(i.shouldGoBack()){
				isResumeButtonPressed = true;
			}
		super.paint(g);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume){
			isResumeButtonPressed = true;
		}
		else if(e.getSource() == inventory){
			isInventoryButtonPressed = true;
			i = new Inventory(0,0,300,500,p.getInventory());
		}
		else if(e.getSource() == settings){
			isSettingsButtonPressed = true;
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
	
	public boolean isSettingsButtonPressed(){
		return isSettingsButtonPressed;
	}
	
	public boolean isInventoryButtonPressed(){
		return isInventoryButtonPressed;
	}
}
