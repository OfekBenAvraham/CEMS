package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import client.ClientUI;
import entities.Message;

public abstract class AbstractController {
	/**This method get message from client and send it to the server*/
	public static void SendToServer(Message msg) {  
		ClientUI.client.accept(msg);
	}
	public AbstractController() {
		
	}
	 /** method message This method will opens new alert box with the message that
	 * user put
	 * @param msg - string that user put and will shows in the alert box
	 */
	public void message(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}

