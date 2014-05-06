import javax.swing.RepaintManager;


public class NoRepaintsManager extends RepaintManager{
	public static void setAsManager(){
		RepaintManager me = new NoRepaintsManager();
		me.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(me);
	}
	public void addInvalidComponent(){}
	public void addDirtyRegion(){}
	public void markCompletelyDirty(){}
	public void paintDirtyRegions(){}
}
