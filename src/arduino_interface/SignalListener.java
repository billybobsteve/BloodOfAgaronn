package arduino_interface;

public interface SignalListener extends java.util.EventListener
{	
	void signalReceived(SignalEvent e);
}
