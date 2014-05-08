package arduino_interface;
public interface ControllerListener extends java.util.EventListener
{	
	void buttonPressed(ControllerEvent e);
	void buttonReleased(ControllerEvent e);
}
