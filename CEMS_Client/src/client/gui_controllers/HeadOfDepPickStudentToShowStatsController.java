package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
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
 * The controller class for the HeadOfDepPickStudentToShowStats.fxml file.
 * It handles the display of available students and allows the head of department
 * to view the statistics of a specific student.
 * @author Guy Pariente	
 * @author Almog Elbaz
 */
public class HeadOfDepPickStudentToShowStatsController implements Initializable {
	
	public static HeadOfDepPickStudentToShowStatsController headOfDepPickStudentToShowStatsController;
	public static ArrayList<Student> students;
	
	@FXML
	private Label headOfDepNameLbl;
	@FXML
	private Label errorlbl;
	@FXML
	private Button viewStudents;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<Student> dataTbl;
	@FXML
	private TableColumn<Student, String> idCol;
	@FXML
	private TableColumn<Student, String> firstNameCol;
	@FXML
	private TableColumn<Student, String> lastNameCol;

    /**
     * Initializes the HeadOfDepPickStudentToShowStatsController.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the controller.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepPickStudentToShowStatsController = this;
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if(students == null)
			return;
		idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studentId"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		ObservableList<Student> data = FXCollections.observableArrayList(students);
		dataTbl.setItems(data);	
	}
	
    /**
     * Handles the action when the "View Students" button is pressed.
     * It retrieves the selected student and displays their statistics.
     *
     * @param event ActionEvent that triggers this method.
     * @throws IOException If there is an error loading the form.
     */
	@FXML
	void viewStudents(ActionEvent event) throws IOException {
		
		if(dataTbl.getSelectionModel().getSelectedItem() != null)
		{
			Student s = dataTbl.getSelectionModel().getSelectedItem();
			ArrayList<String> info = new ArrayList<String>();
			info.add(s.getUserID());
			Message msg = new Message(Operations.HeadOfDepGetExamByStudentId,info);
			ClientUI.client.accept(msg);
			LecturerPickExamToSeeStatsController.navigationBack = "headOfDepToStudent";
		}
		else {
			errorlbl.setText("Please choose Student");
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
