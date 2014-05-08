package arduino_interface;
//represents a signal that specifically came from a controller.
//The purpose of this class is to encapsulate the receiving of the signal from the user
//And replace it with an event that signifies a button was pressed or released
public class ControllerEvent extends java.util.EventObject
{
	protected String controller, status;
	protected int button;
	
	public ControllerEvent(Object source, String controller, int button, String status)
	{
		super(source);
	}
	
	public ControllerEvent(Object source, String signal)
	{
		this(source, signal.substring(0, 1), Integer.parseInt(signal.substring(1, 2)), signal.substring(2));
	}
	
	public String getControllerId()
	{
		return controller;
	}
	
	public int getButton()
	{
		return button;
	}
	
	public String getButtonStatus()
	{
		//the last char is whether it is pressed or release
		return status;
	}
}
