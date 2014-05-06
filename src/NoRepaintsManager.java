import javax.swing.JComponent;
import javax.swing.RepaintManager;


public class NoRepaintsManager extends RepaintManager {

	public static void setAsManager() {
		RepaintManager me = new NoRepaintsManager();
		me.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(me);
	}

	public void addInvalidComponent(JComponent c) {
		
	}
	
	public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
		
	}
	
	public void markCompletelyDirty(JComponent c) {
		
	}
	
	public void paintDirtyRegions() {
		
	}
	
}
