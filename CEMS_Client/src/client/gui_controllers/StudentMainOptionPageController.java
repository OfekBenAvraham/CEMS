package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.ClientController;
import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the student main option page.
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 *
 */
public class StudentMainOptionPageController extends ControllerInterface implements Initializable {

	public static StudentMainOptionPageController studentMainOptionPageController;
	public static Student student;

	@FXML
	private Button btnViewGrades;
	@FXML
	private Button btnTakeAnExam;
	@FXML
	private Label studentNameLbl;
	@FXML
	private Button btnLogout;
	
	ClientController client;
	
	private FXMLLoader loader = new FXMLLoader();

	@FXML
	void clickViewGradesBtn(ActionEvent event) throws IOException {
		loadForm("/gui/StudentGradesViewPage.fxml", "View Grades", event);
	}
	
	/**
	 * Loads the next form.
	 *
	 * @param resource the resource path of the FXML file
	 * @param title    the title of the stage
	 * @param event    the action event
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
	 * Handles the event when the "Take an Exam" button is clicked.
	 *
	 * @param event the action event
	 * @throws IOException if an I/O exception occurs
	 */
	@FXML
	void clickTakeAnExamBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage1 = new Stage();
		primaryStage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		AnchorPane root = loader.load(getClass().getResource("/gui/StudentTypeCodeForExamPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	
	/**
	 * Handles the event when the "Logout" button is clicked.
	 *
	 * @param event the action event
	 * @throws Exception if an exception occurs
	 */
	@FXML
	void logout(ActionEvent event) throws Exception {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/loginPage.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		String userId = Session.getInstance().getCurrentUser().getUserID();
		Message msg = new Message(Operations.UserLogout, userId);
		ClientUI.client.accept(msg);
		Session.getInstance().logout();
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * This method is called to initialize a controller after its root element has been completely processed.
	 * It sets the reference to the current controller instance and updates the student details in the user interface.
	 * The method is part of the Initializable interface and is called after all @FXML annotated members have been loaded.
	 * 
	 * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentMainOptionPageController = this;
		Student student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentMainOptionPageController.student = student;
		    this.studentNameLbl.setText(student.getFullName());
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
		studentMainOptionPageController.student = (Student) user;
		this.studentNameLbl.setText(student.getFullName());

	}
}
