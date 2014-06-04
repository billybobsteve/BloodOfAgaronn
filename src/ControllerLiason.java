import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;

import arduino_interface.ArduinoTest2;

/**
 * Not fully implemented Arduino controller code
 * @author Work
 *
 */
public class ControllerLiason implements SerialPortEventListener {

	BufferedReader input;
	OutputStream output;
	SerialPort serialPort = null;
	String programName, portName = "/dev/tty.usbmodem";
	Main main;

	final int DATA_RATE = 9600;

	public ControllerLiason(Main m) {
		programName = getClass().getName();
		main = m;
	}

	public boolean initialize() {

		CommPortIdentifier portID = null;
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		System.out.println("Trying ports:");
		while(portID == null && ports.hasMoreElements()) {
			CommPortIdentifier testPort = (CommPortIdentifier) ports.nextElement();
			System.out.println("  " + testPort.getName());
			if(testPort.getName().startsWith(portName)) {
				try {
					serialPort = (SerialPort) testPort.open(programName, 1000);
					portID = testPort;
					System.out.println("Connected on " + portID.getName());
					break;
				} catch(PortInUseException e){
					System.out.println(e);
				}
			}
		}

		if(portID == null || serialPort == null) {
			System.out.println("Could not connect to Arduino!");
			return false;
		}

		try {
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

			Thread.sleep(2000);

			return true; 
		} catch(Exception e) { System.out.println(e); }

		return false;
	}

	public void sendData(String data) {
		try {
			System.out.println("Sending data: " + data);
			output = serialPort.getOutputStream();
			output.write(data.getBytes());
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	public synchronized void close() {
		if(serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}


	public void serialEvent(SerialPortEvent event) {
		try {
			if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
				if(input == null)
					input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				String inputLine = input.readLine();
				System.out.println(inputLine);
			}
		}
		catch(Exception e) { System.out.println(e); }
	}

	public void wait(int delay) {
		try {
			Thread.sleep(delay);
		}
		catch(Exception e) { System.out.println(e); }
	}
}
