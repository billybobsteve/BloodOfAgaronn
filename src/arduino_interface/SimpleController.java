package arduino_interface;
//An object to represent the simple 6-button controller (the first one designed)
public class SimpleController extends ControllerObject
{
	public SimpleController(ControllerLiaison l)
	{
		super(l);
		
		for(int i = 0; i <= 6; i++)
			buttons.put(id + i, false);
	}
}
