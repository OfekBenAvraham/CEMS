package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
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
 * Controller for the HeadOfDepPickLecturerToShowStats view.
 * This controller manages the interaction between the user and the UI.
 * @author Guy Pariente	
 * @author Almog Elbaz
 */
public class HeadOfDepPickLecturerToShowStatsController implements Initializable {
	public static HeadOfDepPickLecturerToShowStatsController headOfDepPickLecturerToShowStatsController;
	public static ArrayList<Lecturer> lecturers;
	
	@FXML
	private Label headOfDepNameLbl;
	@FXML
	private Label errorlbl;
	@FXML
	private Button viewDataBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<Lecturer> dataTbl;
	@FXML
	private TableColumn<Lecturer, String> idCol;
	@FXML
	private TableColumn<Lecturer, String> firstNameCol;
	@FXML
	private TableColumn<Lecturer, String> lastNameCol;

	/**
	 * Initialize the controller after the root element has been completely processed.
	 * The method is called after the fxml file has been loaded.
	 * 
	 * @param location The location used to resolve relative paths for the root object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepPickLecturerToShowStatsController = this;
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if(lecturers == null)
			return;
		idCol.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("lecturerId"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Lecturer, String>("lastName"));
		ObservableList<Lecturer> data = FXCollections.observableArrayList(lecturers);
		dataTbl.setItems(data);	
	}
	
	/**
	 * Event handler for getting the selected lecturer's exams.
	 * When a lecturer is selected and this method is invoked, it retrieves the 
	 * exams for the selected lecturer.
	 *
	 * @param event The click event on the 'Get Lecturer Exams' button.
	 * @throws IOException If there is an error while loading the next scene.
	 */
	@FXML
	void getLecturerExams(ActionEvent event) throws IOException {
		
		Lecturer selectedLecturer = dataTbl.getSelectionModel().getSelectedItem();
		if(selectedLecturer != null)
		{
			ArrayList<String> info = new ArrayList<String>();
			info.add(selectedLecturer.getLecturerId());
			Message msg = new Message(Operations.LecturerGetExamsToShowStats,info);
			ClientUI.client.accept(msg);
			LecturerPickExamToSeeStatsController.navigationBack = "headOfDepToLecturer";
			}
		else {
			errorlbl.setText("Please Select Lecturer.");
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerPickExamToSeeStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	/**
	 * Event handler for the 'Back' button.
	 * When this button is pressed, it navigates to the previous scene.
	 *
	 * @param event The click event on the 'Back' button.
	 * @throws IOException If there is an error while loading the previous scene.
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
		AnchorPane root = loader.load(getClass().getResource("/gui/HeadOfDepartmentNavigationStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
