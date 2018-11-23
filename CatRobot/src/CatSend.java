import lejos.hardware.Button;
import lejos.hardware.Bluetooth;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;
import java.io.DataOutputStream;

public class CatSend {
	
	public static final int up = Button.ID_UP, down = Button.ID_DOWN, left = Button.ID_LEFT, 
							right = Button.ID_RIGHT, enter = Button.ID_ENTER, esc = Button.ID_ESCAPE;

	public static NXTCommConnector connector = Bluetooth.getNXTCommConnector();
	public static NXTConnection connection = connector.connect("00:16:53:44:66:05", NXTConnection.RAW);
	public static DataOutputStream output = connection.openDataOutputStream();
	
	// STATES - MOVE MODE: 0=stop 1=forward 2=back 3=left 4=right 5=stop
	//			CATAPULT MODE: 0=stop 1=adjust up 2=adjust down 3=stop 4=stop 5=throw
	public static byte[] data = new byte[2];
							
	public static void main(String[] args) throws Exception {

		while(Button.getButtons() != esc + right) {
			
			System.out.println(data[0] + " : " + data[1]);
			
			if(Button.getButtons() == esc + enter) //change modes
				modeChange();
			else if(Button.getButtons() == up)    //forward - adjust up
				data[1] = 1;
			else if(Button.getButtons() == down)  //backward - adjust down
				data[1] = 2;
			else if(Button.getButtons() == left)  //left - stop
				data[1] = 3;
			else if(Button.getButtons() == right) //right - stop
				data[1] = 4;
			else if(Button.getButtons() == enter) //stop - throw
				data[1] = 5;
			else								 //stop
				data[1] = 0;
			
			output.write(data);
			output.flush();
			
		}
			

	}
	
	public static void modeChange() {
			if(data[0] == 0) {
				data[0] = 1;
			}else {
				data[0] = 0;
			}
			data[1] = 0;
			while(Button.getButtons() == esc + enter);
	}

}






