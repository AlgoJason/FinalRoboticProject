import java.io.DataOutputStream;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;

public class sendTest {

	public static void main(String[] args)throws Exception {
		
		NXTCommConnector connector = Bluetooth.getNXTCommConnector();
		NXTConnection connection = connector.connect(" 00:16:53:44:66:05", NXTConnection.RAW); //jason's mac: 00:16:53:44:66:05
		DataOutputStream output = connection.openDataOutputStream();						 //Marcus's mac: 00:16:53:4A:68:86
		
		byte[] data = new byte[2];
		
		LCD.drawString("Move Mode", 0, 1);
		
		while(true) {

			if(Button.getButtons() == Button.ID_ESCAPE) {
				if(data[0] == 0) {
					data[0] = 1;
					LCD.clearDisplay();
					LCD.drawString("Catapult Mode", 0, 1);
				}else {
					data[0] = 0;
					LCD.clearDisplay();
					LCD.drawString("Move Mode", 0, 1);
				}
				while(Button.getButtons() == Button.ID_ESCAPE);
			}else if(Button.getButtons() == Button.ID_UP)
				data[1] = 1;
			else if(Button.getButtons() == Button.ID_DOWN)
				data[1] = 2;
			else if(Button.getButtons() == Button.ID_LEFT)
				data[1] = 3;
			else if(Button.getButtons() == Button.ID_RIGHT)
				data[1] = 4;
			else if(Button.getButtons() == Button.ID_ENTER)
				data[1] = 0;
			
			output.write(data);
			output.flush();
			
		}

	}

}
