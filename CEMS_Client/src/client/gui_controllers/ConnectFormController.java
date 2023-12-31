package gui_controllers;

import java.io.IOException;

import client.ClientUI;
import entities.Message;
import entities.Session;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Controller for the connection form of the GUI.
 */
public class ConnectFormController {
	public static String connectedToServer = null;

	@FXML
	private Button btnConnect;

	@FXML
	private Button btnExit;

	@FXML
	private TextField txtServerIP;

	@FXML
	private Label lblErrorID;

	@FXML
	private Label lblErrorPort;
	
	/**
	 * Retrieves the IP from the input text field.
	 *
	 * @return A string representing the IP.
	 */
	@SuppressWarnings("unused")
	private String getIP() {
		return txtServerIP.getText();
	}
	
	/**
	 * Event handler for the Connect button.
	 * It sets the chat client, sends a connect message to the server,
	 * and opens the login page.
	 *
	 * @param event The click event on the Connect button.
	 * @throws IOException If there is an error while loading the login page.
	 */
	@FXML
	void getConnectBtn(ActionEvent event) throws IOException {
		String ip = txtServerIP.getText();
		FXMLLoader loader = new FXMLLoader();
	
		if (checkNull(ip)) {
			lblErrorPort.setText("Please Insert IP");
			return;
		}
		ClientUI.setChat(ip, 5555);
		Message sendMessage = new Message(Operations.Port, "Connect");
		ClientUI.client.accept(sendMessage); 
		if (connectedToServer != null) {
			lblErrorPort.setText("Wrong IP");
			connectedToServer = null;
			return;
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage1 = new Stage();
		primaryStage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		BorderPane root = loader.load(getClass().getResource("/gui/loginPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();

		
	}
	/**
	 * Checks if a string is empty or not.
	 *
	 * @param str The string to be checked.
	 * @return True if the string is empty, false otherwise.
	 */
	public boolean checkNull(String str) {
		return str.isEmpty();
	}

	/**
	 * Starts the form by loading the ConnectForm.fxml file and displaying it on a new stage.
	 *
	 * @param primaryStage The primary stage on which to display the scene.
	 * @throws Exception If there is an error while loading the scene.
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConnectForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Get IP Form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * Event handler for the Exit button.
	 * It closes the application when the button is clicked.
	 *
	 * @param event The click event on the Exit button.
	 * @throws Exception If there is an error while exiting the application.
	 */
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}

}
