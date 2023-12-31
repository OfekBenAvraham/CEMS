package client;

import gui_controllers.ConnectFormController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Client User Interface for the application.
 * It initializes the Client Controller and manages the initial connection form.
 */
public class ClientUI extends Application {
	/**
	 * Client Controller that handles client-server interactions.
	 */
	public static ClientController client;

	/**
	 * The main method to launch the application.
	 *
	 * @param args Command-line arguments. Not used in this application.
	 * @throws Exception If the application fails to launch.
	 */
	public static void main(String args[]) throws Exception {
		launch(args);
	}
	/**
	 * Sets up a new ClientController with the given IP and port.
	 *
	 * @param ip   The IP address of the server.
	 * @param port The port number of the server.
	 */
	public static void setChat(String ip, int port) {
		client = new ClientController(ip, port);
	}
	
	/**
	 * The start method is the main entry point for all JavaFX applications.
	 * It sets up and displays the initial subscriber form.
	 *
	 * @param primaryStage The primary stage for this application, onto which
	 *                     the application scene can be set.
	 * @throws Exception If the application fails to start for any reason.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ConnectFormController frame = new ConnectFormController(); // create subscriber form
		frame.start(primaryStage);
	}
}
