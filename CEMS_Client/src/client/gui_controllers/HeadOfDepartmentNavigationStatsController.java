package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.HeadOfDepartment;
import entities.Lecturer;
import entities.Message;
import entities.Session;
import entities.User;
import enums.Operations;
import enums.RepositoryOperations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the Head of Department Navigation Stats Page.
 * This class handles the user interface and functionality of the navigation options available to the Head of Department for viewing statistics.
 * It interacts with the server to retrieve data and loads the appropriate forms for displaying statistics.
 *   @author Guy Pariente	
 * 	 @author Almog Elbaz
 */
public class HeadOfDepartmentNavigationStatsController implements Initializable {
    /**
     * Reference to the current instance of HeadOfDepartmentNavigationStatsController.
     */
	public static HeadOfDepartmentNavigationStatsController headOfDepartmentNavigationStatsController;
	
	@FXML
	private Button btnLecturer;
	@FXML
	private Button btnCourse;
	@FXML
	private Button btnStudent;
	@FXML
	private Button backBtn;
	@FXML
	private Label headOfDepNameLbl;
	
	/**
     * Initializes the HeadOfDepartmentNavigationStatsController.
     * Sets the current instance of HeadOfDepartmentNavigationStatsController and retrieves the current Head of Department from the session.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());	
	}

	 /**
     * Handles the action of clicking the "Lecturer" button.
     * Sends a message to the server to retrieve the lecturers and loads the HeadOfDepPickLecturerToShowStats form.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an error occurs during the loading of the form.
     */
	@FXML
	void getLecturerStats(ActionEvent event) throws IOException {
		Message msg = new Message(Operations.GetLecturers);
		ClientUI.client.accept(msg);
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
		AnchorPane root = loader.load(getClass().getResource("/gui/HeadOfDepPickLecturerToShowStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	
	  /**
     * Handles the action of clicking the "Course" button.
     * Sends a message to the server to retrieve the courses and loads the HeadOfDepPickCourseToShowStats form.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an error occurs during the loading of the form.
     */
	@FXML
	void getCourseStats(ActionEvent event) throws IOException {
		
		Message msg = new Message(Operations.GetCourses);
		ClientUI.client.accept(msg);
		
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
		AnchorPane root = loader
				.load(getClass().getResource("/gui/HeadOfDepPickCourseToShowStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	/**
	 * Handles the action of clicking the "Student" button.
	 * Sends a message to the server to retrieve the students and loads the HeadOfDepPickStudentToShowStats form.
	 *
	 * @param event The action event triggered by clicking the button.
	 * @throws IOException If an error occurs during the loading of the form.
	 */
	@FXML
	void getStudentStats(ActionEvent event) throws IOException {
		
		Message msg = new Message(Operations.GetStudents);
		ClientUI.client.accept(msg);
		
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
		AnchorPane root = loader
				.load(getClass().getResource("/gui/HeadOfDepPickStudentToShowStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	/**
	 * Handles the action when the exit button is pressed. The current window is
	 * closed and the user is redirected to the main options form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws Exception If there is an error loading the form.
	 */
	@FXML
	public void getExitButton(ActionEvent event) throws Exception {
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
		AnchorPane root = loader.load(getClass().getResource("/gui/HeadOfDepartmentMainOptionPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
