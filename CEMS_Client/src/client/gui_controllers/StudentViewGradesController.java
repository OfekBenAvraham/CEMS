package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import entities.CourseDoneByStudent;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.Operations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the student view grades table.
 * 
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 * 
 */
public class StudentViewGradesController extends ControllerInterface implements Initializable {
	public static StudentViewGradesController studentViewGradesController;
	public static Student student;

	@FXML
	private Label lblFirstName;
	@FXML
	private Label lblInstructionToSelectRowToStatistic;
	@FXML
	private Button btnBack; 
	@FXML
	private Button btnGetStatistics;
	@FXML
	private TableView<CourseDoneByStudent> studentGradesTbl;
	@FXML
	private TableColumn<CourseDoneByStudent, String> courseNameCol;
	@FXML
	private TableColumn<CourseDoneByStudent, String> examIDCol;
	@FXML
	private TableColumn<CourseDoneByStudent, Double> gradeCol;
	@FXML
	private TableColumn<CourseDoneByStudent, String> noteCol;

	ClientController client;

	private ObservableList<CourseDoneByStudent> gradesList = FXCollections.observableArrayList();

	/**
	 * Handles the action when the Exit button is clicked.
	 *
	 * @param event The ActionEvent representing the button click.
	 * @throws IOException If an I/O error occurs when loading the
	 *                     StudentMainOptionForm.fxml.
	 */
	@FXML
	void ExitFromGradesViewButton(ActionEvent event) throws IOException {
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
	 * Handles the action when a row is selected in the studentGradesTbl TableView.
	 *
	 * @param event The ActionEvent representing the button click.
	 * @throws IOException If an I/O error occurs when loading the
	 *                     StudentStatisticalExamReport.fxml.
	 */
	@FXML
	void viewSelectedRow(ActionEvent event) throws IOException {
		CourseDoneByStudent selectedGrade = studentGradesTbl.getSelectionModel().getSelectedItem();
		if (selectedGrade != null) {
			student.setChoosenCourse(selectedGrade);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/StudentStatisticalExamReport.fxml"));
			AnchorPane root = loader.load();
			StudentStatisticalExamReportController reportController = loader.getController();
			reportController.setSelectedGrade(selectedGrade);

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.hide();

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
			Scene scene = new Scene(root);
			primaryStage1.setTitle("Student statistical reports");
			primaryStage1.setScene(scene);
			primaryStage1.show();

		}
	}

	/**
	 * Loads the table with grades for the current student.
	 */
	private void loadTable() {

		Message msg = new Message(Operations.StudentViewGrades, student.getUserID());
		ClientUI.client.accept(msg);
		ObservableList<CourseDoneByStudent> response = ClientUI.client.getGrades();
		if (response != null) {
			gradesList.addAll(response);
		}
		studentGradesTbl.setItems(gradesList);
	}

	/**
	 * Initializes the controller and sets up the initial state of the user
	 * interface.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentViewGradesController = this;
		student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentMainOptionPageController.student = student;
			this.lblFirstName.setText(student.getFullName());
		}
		courseNameCol.setCellValueFactory(new PropertyValueFactory<CourseDoneByStudent, String>("courseName"));
		examIDCol.setCellValueFactory(new PropertyValueFactory<CourseDoneByStudent, String>("examID"));
		gradeCol.setCellValueFactory(new PropertyValueFactory<CourseDoneByStudent, Double>("grade"));
		noteCol.setCellValueFactory(new PropertyValueFactory<CourseDoneByStudent, String>("note"));
		loadTable();
		studentGradesTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	/**
	 * Sets the user for the controller.
	 *
	 * @param user The User object representing the current user.
	 */
	@Override
	public void setUser(User user) {
		StudentViewGradesController.student = (Student) user;
		this.lblFirstName.setText(student.getFullName());

	}

}
