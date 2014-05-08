package arduino_interface;
//an event object representing any signal that the console receives over serial
public class SignalEvent extends java.util.EventObject
{
	//the string that was transmitted between the console and the controller
	protected String signal;
	
	//TODO the parameter source 
	public SignalEvent(Object source, String s)
	{
		super(source);
		signal = s;
	}
	
	public String getSignal()
	{
		return signal;
	}
}
