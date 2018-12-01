import java.io.DataInputStream;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;

public class recieveTest {
	
	public static final int moveSpeed = 250, turnSpeed = 100, throwSpeed = 1000, adjustSpeed = 50;

	public static void main(String[] args)throws Exception {
		
		NXTCommConnector connector = Bluetooth.getNXTCommConnector();
		NXTConnection connection = connector.waitForConnection(0, NXTConnection.RAW);
		DataInputStream input = connection.openDataInputStream();
		
		byte[] data = new byte[2];
		
		while(true) {
			
			byte temp0 = data[0];
			byte temp1 = data[1];
			
			input.read(data);
			
			if(temp0 != data[0] || temp1 != data[1]) {
			
				if(data[0] == 0) {
					
					if(data[1] == 1) {
						changeMoveSpeed(moveSpeed);
						Motor.B.backward();
						Motor.C.backward();
					}else {
						Motor.B.stop(true);
						Motor.C.stop();
					}
					
				}
			
			}
			
			
		}
			
	}
	
	public static void changeCatSpeed(int speed) {
		Motor.A.setSpeed(speed);
		Motor.D.setSpeed(speed);
	}
	
	public static void changeMoveSpeed(int speed) {
		Motor.B.setSpeed(speed);
		Motor.C.setSpeed(speed);
	}

}
