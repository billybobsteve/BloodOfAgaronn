package arduino_interface;
import java.util.ArrayList;
import java.util.HashMap;

//while the controller input could be managed from the ControllerLiaison class, these classes encapsulate the serial
//input and make interfacing with controllers easier. They also facilitate better interactivity with the console
public abstract class ControllerObject implements SignalListener
{	
	ControllerLiaison liaison;
	ArrayList<ControllerListener> listeners;
	
	//id is firch char in string transmitted by controllers to console
	String id;
	
	//string is same format as transmitted by controllers to console
	HashMap<String, Boolean> buttons;
	
	public ControllerObject(ControllerLiaison l)
	{
		liaison = l;
		liaison.addControllerListener(this);
		
		buttons = new HashMap<String, Boolean>();
		listeners = new ArrayList<ControllerListener>();
	}
	
	public void addControllerListener(ControllerListener l)
	{
		listeners.add(l);
	}
	
	//n is the number of the button
	public boolean isButtonDown(int n)
	{
		return buttons.get(id + n);
	}
	
	//take a signal that came in and abstract it to a usable controller event that gets sent to the console
	public void signalReceived(SignalEvent e)
	{
		ControllerEvent event = new ControllerEvent(this, e.getSignal());
		
		//make sure the signal came from the controller represented by this object
		if(!event.getControllerId().equals(id))
			return;
		
		//register it as a button pressed or released action
		if(event.getButtonStatus().equals("1"))
		{
			fireButtonPressed(event);
		}
		else if(event.getButtonStatus().equals("0"))
		{
			fireButtonReleased(event);
		}
	}
	
	private void fireButtonPressed(ControllerEvent e)
	{
		for(ControllerListener l: listeners)
		{
			l.buttonPressed(e);
		}
	}
	
	private void fireButtonReleased(ControllerEvent e)
	{
		for(ControllerListener l: listeners)
		{
			l.buttonReleased(e);
		}
	}
}
