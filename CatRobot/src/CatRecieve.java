import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;
import java.io.DataInputStream;

public class CatRecieve {
	
	public static final int catThrowSpeed = 1000, catAdjustSpeed = 25, moveSpeed = 250, moveTurnSpeed = 100;
	
	public static NXTCommConnector connector = Bluetooth.getNXTCommConnector();
	public static NXTConnection connection = connector.waitForConnection(0, NXTConnection.RAW);
	public static DataInputStream input = connection.openDataInputStream();
	
	public static byte[] data = new byte[2];

	public static void main(String[] args) throws Exception {
		
		while(Button.getButtons() != Button.ID_ESCAPE) {
			
			input.read(data);			
			
				if(data[0] == 0)//move mode
					moveMode();
				else			//catapult mode
					catMode();
			
		}
		
	}
	
	public static void setSpeed(int speed) {
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		Motor.C.setSpeed(speed);
		Motor.D.setSpeed(speed);
	}
	
	public static void moveMode() {
		
			if(data[1] == 1) {   	    //forward
				setSpeed(moveSpeed);
				Motor.B.backward();
				Motor.C.backward();
			}else if(data[1] == 2) {  	    //backward
				setSpeed(moveSpeed);
				Motor.B.forward();
				Motor.C.forward();
			}else if(data[1] == 3) {	    //left
				setSpeed(moveTurnSpeed);
				Motor.B.forward();
				Motor.C.backward();
			}else if(data[1] == 4) {	    //right
				setSpeed(moveTurnSpeed);
				Motor.B.backward();
				Motor.C.forward();
			}else {						    //stop
				Motor.B.stop(true);
				Motor.C.stop();
			}
		
	}
	
	public static void catMode() throws Exception {
		
		if(data[1] == 0) {				//stop
			Motor.A.stop(true);
			Motor.D.stop();
		}else if(data[1] == 1) {   	    //adjust up
			setSpeed(catAdjustSpeed);
			Motor.A.backward();
			Motor.D.backward();
		}else if(data[1] == 2) {  	    //adjust down
			setSpeed(catAdjustSpeed);
			Motor.A.forward();
			Motor.D.forward();
		}else if(data[1] == 5) {		//launch catapult
			setSpeed(catThrowSpeed);
			Motor.A.rotate(-65,true);
			Motor.D.rotate(-65);
			while(data[1] == 5)
				input.read(data);
		}else {						    //stop
			Motor.B.stop(true);
			Motor.C.stop();
		}
		
	}

}
