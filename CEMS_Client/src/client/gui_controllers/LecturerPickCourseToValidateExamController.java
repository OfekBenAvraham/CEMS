package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Course;
import entities.Exam;
import entities.ExamsDisplayData;
import entities.Message;
import entities.Request;
import entities.Session;
import enums.Operations;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * The controller class for the LecturerPickCourseToValidateExam.fxml file.
 * It handles the display of available courses for the lecturer to validate exams.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerPickCourseToValidateExamController implements Initializable {
	
	public static LecturerPickCourseToValidateExamController lecturerPickCourseToValidateExamController;
	public static ArrayList<Course> courses;

	@FXML
	private TableView<Course> coursesTbl;

	@FXML
	private TableColumn<Course, String> courseCodeCol;

	@FXML
	private TableColumn<Course, String> courseNameCol;

	@FXML
	private Button seeExamsBtn;

	@FXML
	private Button backBtn;

	@FXML
	private Label errorLbl;
	
	@FXML
	private Label lecturerNameLbl;
	
	ArrayList<String> info = new ArrayList<String>(3);
	
	/**
     * Initializes the LecturerPickCourseToValidateExamController.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the controller.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerPickCourseToValidateExamController = this;
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		String lecturerId = Session.getInstance().getCurrentUser().getUserID().toString();
		Message msg = new Message(Operations.LecturerGetCoursesToValidateExams,lecturerId);
		ClientUI.client.accept(msg);
		courseCodeCol.setCellValueFactory(new PropertyValueFactory<Course,String>("courseCode"));
		courseNameCol.setCellValueFactory(new PropertyValueFactory<Course,String>("courseName"));
		if (courses == null)
			return;
		ObservableList<Course> data = FXCollections.observableArrayList(courses);
		coursesTbl.setItems(data);
	}
	
	
	 /**
     * Handles the action when the "See Exams" button is pressed.
     * It retrieves the selected course and displays the exams to be validated.
     *
     * @param event ActionEvent that triggers this method.
     * @throws IOException If there is an error loading the form.
     */
	@FXML
	void getExamsToSee(ActionEvent event) throws IOException {
		Course selectedCourse = coursesTbl.getSelectionModel().getSelectedItem();
	    if (selectedCourse != null) 
	    {
	    	String lecturerId = Session.getInstance().getCurrentUser().getUserID().toString();
	    	String courseCode = selectedCourse.getCourseCode();
	    	String courseName = selectedCourse.getCourseName();
	        info.add(lecturerId);
	        info.add(courseCode);
	        info.add(courseName); 
	        LecturerShowExamsNotValidatedController.dataToReturn = info;
	        Message msg = new Message(Operations.LecturerGetDoneExamsByCourseChosen, info);
	        ClientUI.client.accept(msg);
	    } 
	    else {
	    	errorLbl.setText("Please select course.");
	    	return;
	    }
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsByLecturerId.fxml").openStream());
		LecturerShowExamByLecturerIdController.info = info;
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	
	 /**
     * Handles the action when the "Back" button is pressed.
     * It navigates back to the LecturerMainForm.fxml file.
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerMainForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	


}
