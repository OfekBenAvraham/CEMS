package gui_controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the student start exam controller.
 * 
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 * 
 */
public class StudentStartComputerizedExamController extends ControllerInterface implements Initializable {
	
	public static StudentStartComputerizedExamController studentStartComputerizedExamController;
	public static Student student;
	public static int serialNumber;
	
	@FXML
	private Label lblFirstName;
	@FXML
	private Button btnStartExam;;
	@FXML
	private Button btnBack;
	@FXML
	private TextField enterStudentId;
	@FXML
	private Label error;
	
	private boolean alreadyDidTheExam;

	private FXMLLoader loader = new FXMLLoader();
	
	/**
	 * Initializes the controller class.
	 *
	 * @param location  the location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources the resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentStartComputerizedExamController = this;
		Student student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentMainOptionPageController.student = student;
			this.lblFirstName.setText(student.getFullName());
		}
	}

	/**
	 * Handles the action when the back button is clicked.
	 * Redirects the user back to the studentMainOptionPage.
	 *
	 * @param event the ActionEvent triggered by clicking the back button.
	 * @throws IOException if an I/O error occurs.
	 */
	@FXML
	void backFromStartComputerizedExam(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/StudentTypeCodeForExamPage.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
	}
	/**
	 * Handles the action when the start exam button is clicked.
	 * Validates the student ID, checks if the student has already taken the exam, and loads the studentInComputerizedExam form.
	 *
	 * @param event the ActionEvent triggered by clicking the start exam button.
	 * @throws IOException if an I/O error occurs.
	 */
	@FXML
	void clickStartExam(ActionEvent event) throws IOException {
		
		String enteredId = enterStudentId.getText();
	    ArrayList<String> arrayForCheckStudentAns = new ArrayList<>();
	    arrayForCheckStudentAns.add(StudentTypeCodeForExamController.code);
	    arrayForCheckStudentAns.add(StudentMainOptionPageController.student.getUserID());
		
		Message msgForCheckAvailableAns = new Message(Operations.StudentCheckIfDidTheExam, arrayForCheckStudentAns);
		ClientUI.client.accept(msgForCheckAvailableAns); 
		alreadyDidTheExam = ClientUI.client.getCheckForAnswers();
		
	    
	    if (!enteredId.equals(StudentMainOptionPageController.student.getUserID())) {
	        error.setText("Wrong ID");
	        return;
	    }
	    
	    else if(alreadyDidTheExam) {
	    	error.setText("You Already Did This Exam");
	    	return;
	    }

	    else {
	        loadForm("/gui/StudentInComputerizedExam.fxml", "Start Computerized Exam", event);
	    }
	}
	
	/**
	 * Loads the next form based on the given resource and title.
	 *
	 * @param resource the resource path of the form to be loaded.
	 * @param title    the title of the form to be loaded.
	 * @param event    the ActionEvent triggered by loading the form.
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
		studentStartComputerizedExamController.student = (Student) user;
		this.lblFirstName.setText(student.getFullName());

	}
	
}
