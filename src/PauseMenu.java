import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PauseMenu extends JPanel implements ActionListener{
	JButton resume = new JButton("Resume");
	JButton inventory = new JButton("Inventory");
	JButton settings = new JButton("Settings");
	JButton mainMenu = new JButton("Main Menu");
	Player p;
	boolean isMenuButtonPressed, isResumeButtonPressed, isSettingsButtonPressed;
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume){
			isResumeButtonPressed = true;
		}
		else if(e.getSource() == inventory){
			this.add(new Inventory(this.getX(),this.getY(),this.getWidth(),this.getHeight(),p.getInventory()));
		}
		else if(e.getSource() == settings){
			isSettingsButtonPressed = true;
		}
		else if(e.getSource() == mainMenu){
			isMenuButtonPressed = true;
		}
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
}
