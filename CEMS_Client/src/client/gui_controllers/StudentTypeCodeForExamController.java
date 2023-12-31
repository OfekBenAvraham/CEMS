package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import client.ClientController;
import client.ClientUI;
import entities.ExecutedExam;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.ExamType;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 * Controller for the StudentTypeCodeForExam.fxml view.
 *
 * @author Maayan Avittan
 * @author Paz Fayer
 * 
 */
public class StudentTypeCodeForExamController extends ControllerInterface implements Initializable {
	
	public static StudentTypeCodeForExamController studentTypeCodeForExamController;
	public static Student student;

	@FXML
	private Label lblFirstName;
	@FXML
	private Label lblStartingExam;
	@FXML
	private Label lblEnterExamCode;
	@FXML
	private TextField txtExamCode;
	@FXML
	private Button btnStartExam;
	@FXML
	private Button btnBack; 
	@FXML
	private Label lblErrorcode;

	ClientController client;
	
	private boolean isExamLocked = false;
	
	private FXMLLoader loader = new FXMLLoader();
	
	private ExecutedExam exam;
	
	public static String code; 

	/**
	 * This method is called after the FXML file has been loaded and can be used to initialize the scene.
	 * It sets the studentTypeCodeForExamController instance to this object and initializes various UI elements with data from the current user session.
	 * 
	 * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if the root object was not localized.
	 * @see Session
	 * @see Student
	 * @throws NullPointerException if the current user is null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentTypeCodeForExamController = this;
		Student student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentMainOptionPageController.student = student;
			this.lblFirstName.setText(student.getFullName());
		}
	}
	
	
	/**
	 * This method is triggered when the user clicks on the 'Go To Exam' button.
	 * It verifies the inputted exam code and checks if the exam is locked or not. Depending on the exam type and the validation checks, 
	 * it navigates the user to the correct form or displays an error message.
	 * 
	 * @param event The ActionEvent object representing the triggering event (in this case, the click on the 'Go To Exam' button).
	 * @throws IOException if there is an error during the loading of the new form.
	 */
	@FXML
	void clickGoToExam(ActionEvent event) throws IOException {
		code = txtExamCode.getText();
		Message msgForLockCheck = new Message(Operations.StudentComputerizedExamLockCheck, StudentTypeCodeForExamController.code);
		ClientUI.client.accept(msgForLockCheck); 
		isExamLocked = ClientUI.client.getIsComputerizedExamLocked();
		if (code.trim().isEmpty()) {
			lblErrorcode.setText("Please insert exam code!");
			return;
		}
		else if (code.length() != 4 || !isAlphaNumeric(code)) {
			lblErrorcode.setText("Exam code should consist of 4 digits and letters only!");
			return;
		}
		else if(isExamLocked) {
			lblErrorcode.setText("The Exam Is Locked!");
	    	return;
	    }
		else {
			Message msg = new Message(Operations.StudentGetExam, code);
			System.out.println(msg);
			ClientUI.client.accept(msg); // sending request to DB to get type
			ExecutedExam response = ClientUI.client.getExecutedExam();
		    if (response != null) {
		    	if (response.getExamType() == ExamType.COMPUTERIZED) {
		    		loadForm("/gui/StudentStartComputerizedExam.fxml", "Start Computerized Exam", event);
		    	}
		    	if (response.getExamType() == ExamType.MANUAL) {
		    		exam = response;
					StudentMainOptionPageController.student.setChoosenCurrentExam(code);
		    		loadForm("/gui/StudentStartManualExam.fxml", "Start Manual Exam", event);
		    	}
		    }
		    else
		    	lblErrorcode.setText("No Exam Exist");
		}
	}
	
	/**
	 * Loads the specified form and opens it in a new stage.
	 *
	 * @param resource The FXML resource path of the form.
	 * @param title    The title of the new stage.
	 * @param event    The action event that triggered the form loading.
	 */
	private void loadForm(String resource, String title, ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					String userId = Session.getInstance().getCurrentUser().getUserID();
					Message msg = new Message(Operations.UserLogout, userId);
					ClientUI.client.accept(msg);
					Session.getInstance().logout();
					Message sendMessage = new Message(Operations.Disconect);
					ClientUI.client.accept(sendMessage);
				}
			});
			Pane root = loader.load(getClass().getResource(resource).openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if a given string is alphanumeric.
	 *
	 * @param str The string to check.
	 * @return True if the string is alphanumeric, false otherwise.
	 */
	private boolean isAlphaNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Handles the "Back" button click event. Navigates back to the student main
	 * option page.
	 *
	 * @param event The action event triggered by the button click.
	 * @throws IOException If an I/O error occurs while loading the student main
	 *                     option form.
	 */
	@FXML
	void backFromEnterCodePage(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/StudentMainOptionForm.fxml"));
		Scene scene = new Scene(root2);
		// Import the stage
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				String userId = Session.getInstance().getCurrentUser().getUserID();
				Message msg = new Message(Operations.UserLogout, userId);
				ClientUI.client.accept(msg);
				Session.getInstance().logout();
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * This method is used to set the user details in the user interface. 
	 * It updates the user instance variable of the controller and sets the user's full name in the label.
	 *
	 * @param user The User object representing the user whose details should be displayed. 
	 *             This user should be an instance of Student.
	 * @throws ClassCastException if the provided User is not an instance of Student.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void setUser(User user) {
		studentTypeCodeForExamController.student = (Student) user;
		this.lblFirstName.setText(student.getFullName());

	}

}
