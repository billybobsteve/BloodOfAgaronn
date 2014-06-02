import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inventory extends JPanel {
	HashMap<Item, ImageIcon> map = new HashMap<Item, ImageIcon>();
	public class ImageListCellRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(map.get((Item)value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			return label;
		}
	}
	public Inventory(int x, int y, int width, int height, ArrayList<Item> inventory){
		setBounds(x,y,width,height);
		for(Item i : inventory){
        	map.put(i, new ImageIcon(i.getImage()));
        }
		JList invList = new JList(inventory.toArray());
		invList.setCellRenderer(new ImageListCellRenderer());
		JScrollPane scroll = new JScrollPane(invList);
		this.add(scroll);
	}
	public static void main(String[] args){
		ArrayList<Item> inventory = new ArrayList<Item>();
		inventory.add(new Item(0,0,30,60,"NailBiter.png", "NailBiter"));
		inventory.add(new Item(0,0,30,60,"DudeBroMan.png", "Placeholder"));
		JFrame frame = new JFrame();
		frame.add(new Inventory(0,0,300,300,inventory));
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
