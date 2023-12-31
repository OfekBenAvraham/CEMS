package server;

import gui.ServerPortController;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * 
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Maayan Avittan
 * @author Guy Pariente
 * @author Almog Elbaz
 */
public class ServerUI extends Application {


	// Instance methods ************************************************

	/**
	 * The main method of Application class.
	 */
	public static void main(String args[]) throws Exception {
		launch(args);
	}

	/**
	 * Start the server's UI. Create and start ServerPort frame.
	 * 
	 * @param primaryStage The first window that shows.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerPortController frame = new ServerPortController();
		// close the program
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);
		});
		frame.start(primaryStage);
	}
}
