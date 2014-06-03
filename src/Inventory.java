import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Inventory extends JInternalFrame implements ActionListener {
	HashMap<Item, ImageIcon> map = new HashMap<Item, ImageIcon>();
	boolean back;
	public class ImageListCellRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(map.get((Item)value));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			return label;
		}
	}
	public Inventory(int x, int y, int width, int height, ArrayList<Item> inventory){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(width,height);
		for(Item i : inventory){
			//System.out.println(i.getImage());
			if(i.getImage() != null)
				map.put(i, new ImageIcon(i.getImage()));
        }
		JList invList = new JList(inventory.toArray());
		invList.setCellRenderer(new ImageListCellRenderer());
		JScrollPane scroll = new JScrollPane(invList);
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//back = true;
	}
	public boolean shouldGoBack(){
		return back;
	}
}
