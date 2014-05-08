package arduino_interface;
import gnu.io.*;
import java.io.*;
import java.util.*;


public class ControllerLiaison implements SerialPortEventListener
{	
	//a table to keep track of all buttons that have been pressed
	HashMap<String, Boolean> controllerMap = new HashMap<String, Boolean>();
	
	//probably controller objects who use the signals processed by this class to update information and alert the game of input
	ArrayList<SignalListener> listeners = new ArrayList<SignalListener>();
	
	BufferedReader input;
	OutputStream output;
	SerialPort serialPort = null;
	String programName, portName = "/dev/tty.usbmodem";
	
	final int DATA_RATE = 9600;
	
	public ControllerLiaison()
	{
		programName = getClass().getName();
	}
	
	private boolean initialize()
	{
		CommPortIdentifier portID = null;
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		System.out.println("Trying ports:");
		while(portID == null && ports.hasMoreElements())
		{
			CommPortIdentifier testPort = (CommPortIdentifier) ports.nextElement();
			System.out.println("  " + testPort.getName());
			if(testPort.getName().startsWith(portName))
			{
				try
				{
					serialPort = (SerialPort) testPort.open(programName, 1000);
					portID = testPort;
					System.out.println("Connected on " + portID.getName());
					break;
				}
				catch(PortInUseException e)
				{
					System.out.println(e);
				}
			}
		}
		
		if(portID == null || serialPort == null)
		{
			System.out.println("Could not connect to Arduino!");
			return false;
		}
		
		try
		{
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
			SerialPort.PARITY_NONE);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
			Thread.sleep(2000);
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return false;
	}
	
	public void addControllerListener(SignalListener l)
	{
		listeners.add(l);
	}
	
	public void sendData(String data)
	{
		try
		{
			System.out.println("Sending data: " + data);
			output = serialPort.getOutputStream();
			output.write(data.getBytes());
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	public synchronized void close()
	{
		if(serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public boolean isButtonDown(String id)
	{
		if(controllerMap.containsKey(id))
		{
			return controllerMap.get(id);
		}
		else
			return false;
	}
	
	public void serialEvent(SerialPortEvent e)
	{
		try
		{
			if(e.getEventType() == SerialPortEvent.DATA_AVAILABLE)
			{
				//get the string
				if(input == null)
				{
					input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				}
				String inputLine = input.readLine();
				System.out.println(inputLine);
				
				//TODO check that there is not a 2
				controllerMap.put(inputLine.substring(0, 2), inputLine.charAt(2) == '1');
				
				//Alert relevant parties that a message has been received
				fireSignalEvent(inputLine);
				
				//test it
				System.out.println(controllerMap);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	private void fireSignalEvent(String s)
	{
		for(SignalListener l: listeners)
		{
			l.signalReceived(new SignalEvent(this, s));
		}
	}
	
	public void wait(int delay)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void main(String...args)
	{
		ControllerLiaison test = new ControllerLiaison();
		if(test.initialize())
		{
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			while(!input.equals("Q"))
				input = in.nextLine();

			test.close();
		}
		System.out.println("Done.");
	}
}
