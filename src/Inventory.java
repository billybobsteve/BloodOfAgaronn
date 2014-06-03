import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Inventory extends JInternalFrame implements ListSelectionListener {
	HashMap<Item, ImageIcon> map = new HashMap<Item, ImageIcon>();
	JList invList;
	Player player;
	boolean back;
	public class ImageListCellRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(map.get((Item)value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			return label;
		}
	}
	public Inventory(int x, int y, int width, int height, Player player){
		this.player = player;
		ArrayList<Item> inventory = player.getInventory();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(width,height);
		for(Item i : inventory){
			//System.out.println(i.getImage());
			if(i.getImage() != null)
				map.put(i, new ImageIcon(i.getImage()));
        }
		invList = new JList(inventory.toArray());
		invList.setCellRenderer(new ImageListCellRenderer());
		invList.addListSelectionListener(this);
		JScrollPane scroll = new JScrollPane(invList);
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
	}

	public boolean shouldGoBack(){
		return back;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Item i = (Item)invList.getSelectedValue();
		if(i instanceof Weapon){
			player.changeWeapon((Weapon)i);
		}
		else if(i instanceof Armor){
			player.changeArmor((Armor)i);
		}
	}
}
