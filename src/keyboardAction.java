import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;


public class keyboardAction extends AbstractAction {

	Main main;
	
	public keyboardAction(Main m) {
		main = m;
	}

	public keyboardAction(String name) {
		super(name);
	}

	public keyboardAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}
