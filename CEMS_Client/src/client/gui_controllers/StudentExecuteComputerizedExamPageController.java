package gui_controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the Student Execute Computerized Exam page.
 * This class is responsible for handling user interactions within the page.
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 */
public class StudentExecuteComputerizedExamPageController {

	@FXML
	private Label lblHello;
	@FXML
	private Label lblFirstName;
	@FXML
	private TextField txtExamCode;
	@FXML
	private Button btnStartExam; // getAccessToExam()
	@FXML
	private Button btnExit; // the exit button goes back to available exams for the student


    /**
     * The main entry point for the JavaFX application.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * @throws Exception if any error occurs during loading the layout file.
     */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ExecuteComputerizedExam.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Computerized Exam");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    /**
     * Handles the Exit button click event.
     * Navigates to the AvailableExams.fxml page when the Exit button is clicked.
     *
     * @param event the event object representing the Exit button click event.
     * @throws IOException if any error occurs during loading the layout file.
     */
	@FXML
	void ExitFromGradesViewButton(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("AvailableExams.fxml"));
		Scene scene = new Scene(root2);
		// Import the stage
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
	}

}
