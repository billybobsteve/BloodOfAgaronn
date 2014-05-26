import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class Inventory extends JPanel {
	HashMap<String, ImageIcon> map;
	public class ImageListCellRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(map.get((String)value));
			return null;
		}
	}
	public Inventory(int x, int y, int width, int height, ArrayList<Item> inventory){
		setBounds(x,y,width,height);
	}
}
