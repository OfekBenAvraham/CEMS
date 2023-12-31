package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Course;
import entities.Lecturer;
import entities.Message;
import entities.Session;
import enums.Operations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The controller class for the HeadOfDepPickCourseToShowStats.fxml file.
 * It handles the display of available courses and allows the head of department
 * to select a course and view its statistics.
 * @author Guy Pariente	
 * @author Almog Elbaz
 */
public class HeadOfDepPickCourseToShowStatsController implements Initializable {
	public static HeadOfDepPickCourseToShowStatsController headOfDepPickCourseToShowStatsController;
	public static ArrayList<Course> courses;
	
	@FXML
	private Label headOfDepNameLbl;
	@FXML
	private Label errorlbl;
	@FXML
	private Button viewDataBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<Course> dataTbl;
	@FXML
	private TableColumn<Course, String> codeCol;
	@FXML
	private TableColumn<Course, String> courseNameCol;

	
	 /**
     * Initializes the HeadOfDepPickCourseToShowStatsController.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the controller and populates the table view with course data.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepPickCourseToShowStatsController = this;	
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if(courses == null)
			return;
		codeCol.setCellValueFactory(new PropertyValueFactory<Course, String>("courseCode"));
		courseNameCol.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));

		ObservableList<Course> data = FXCollections.observableArrayList(courses);
		dataTbl.setItems(data);	
		
	}
	
	/**
     * Handles the action when the "Get Courses Exams" button is pressed.
     * It retrieves the selected course and sends a message to the server to get the exams for that course.
     * Then it navigates to the LecturerPickExamToSeeStats.fxml file to display the exams statistics.
     *
     * @param event ActionEvent that triggers this method.
     * @throws IOException If there is an error loading the form.
     */
	@FXML
	void getCoursesExams(ActionEvent event) throws IOException {
		
		if(dataTbl.getSelectionModel().getSelectedItem() != null)
		{
			Course c = dataTbl.getSelectionModel().getSelectedItem();
			ArrayList<String> info = new ArrayList<String>();
			info.add(c.getCourseCode());
			Message msg = new Message(Operations.HeadOfDepGetExamByCourseCode,info);
			ClientUI.client.accept(msg);
			LecturerPickExamToSeeStatsController.navigationBack = "headOfDepToCourse";
		}
		else {
			errorlbl.setText("Please choose course.");
			return;
		}
	
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
				.load(getClass().getResource("/gui/LecturerPickExamToSeeStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

    /**
     * Handles the action when the "Back" button is pressed.
     * It navigates back to the HeadOfDepartmentNavigationStats.fxml file.
     *
     * @param event ActionEvent that triggers this method.
     * @throws IOException If there is an error loading the form.
     */
	@FXML
	void getBackBtn(ActionEvent event) throws IOException {
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
				.load(getClass().getResource("/gui/HeadOfDepartmentNavigationStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
