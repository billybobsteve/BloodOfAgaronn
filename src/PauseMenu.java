import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu extends JPanel implements ActionListener{
	JButton resume = new JButton("Resume");
	JButton inventory = new JButton("Inventory");
	JButton settings = new JButton("Settings");
	JButton quit = new JButton("Quit");
	public PauseMenu(Player p, int x, int y, int width, int height){
		setBounds(x, y, width, height);
		setLayout(new GridLayout(1,4));
		add(resume);
		add(inventory);
		add(settings);
		add(quit);
		resume.addActionListener(this);
		inventory.addActionListener(this);
		settings.addActionListener(this);
		quit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume){
			
		}
		else if(e.getSource() == inventory){
			
		}
		else if(e.getSource() == settings){
			
		}
		else if(e.getSource() == quit){
			
		}
	}
}
