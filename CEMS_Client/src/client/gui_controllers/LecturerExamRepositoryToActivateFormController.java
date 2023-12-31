package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.Exam;
import entities.Message;
import entities.Question;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * This class represents a controller for the Lecturer's Exam Repository
 * Activation Form in a JavaFX application. It handles user events, updates the
 * view accordingly, and manages the exams list.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class LecturerExamRepositoryToActivateFormController implements Initializable {
	public static ArrayList<Exam> exams;

	@FXML
	private Button btnExit;

	@FXML
	private TableView<Exam> table;

	@FXML
	private TableColumn<Exam, String> id;

	@FXML
	private TableColumn<Exam, String> courseName;

	@FXML
	private TableColumn<Exam, Integer> duration;

	@FXML
	private TableColumn<Exam, String> fieldName;
	
	@FXML
	private TableColumn<Exam, String> serialNumber;
	
	@FXML
	private TableColumn<Exam, String> examCode;

	@FXML
	private Text textError;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * Initializes the controller after its root element has been completely
	 * processed. Sets the lecturer's name in the view and populates the table with
	 * exam data.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if (exams == null) {
			return;
		}
		id.setCellValueFactory(new PropertyValueFactory<>("examID"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
		examCode.setCellValueFactory(new PropertyValueFactory<>("examCode"));
		courseName.setCellValueFactory(new Callback<CellDataFeatures<Exam, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exam, String> p) {
				return new ReadOnlyObjectWrapper<>(p.getValue().getCourse().getCourseName());
			}
		});
		fieldName.setCellValueFactory(new Callback<CellDataFeatures<Exam, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exam, String> p) {
				return new ReadOnlyObjectWrapper<>(p.getValue().getField().getFieldName());
			}
		});
		ObservableList<Exam> data = FXCollections.observableArrayList(exams);
		table.setItems(data);
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	/**
	 * Shows the selected exam from the table view. If no exam is selected, shows an
	 * error message. Otherwise, it sends a message to the server to get questions
	 * for the selected exam, hides the current window, and loads the
	 * 'LecturerShowExamToActivateForm' scene.
	 *
	 * @param event The event triggered by clicking the 'Show Exam' button.
	 * @throws IOException If the FXML file for the 'LecturerShowExamToActivateForm'
	 *                     scene is not found.
	 */
	@FXML
	void showExam(ActionEvent event) throws IOException {
		Exam e = table.getSelectionModel().getSelectedItem();
		if (e == null) {
			textError.setText("You must to select an exam!");
			return;
		}
		LecturerShowExamToActiavteFormController.exam = e;
		Message message = new Message(Operations.ShowQuestionsInExamToActive, e.getExamID());
		ClientUI.client.accept(message);
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamToActivateForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Clears the exams list, hides the current window and loads the
	 * 'LecturerMainForm' scene. If the stage is closed, it handles the logout
	 * process for the user and disconnects from the server.
	 *
	 * @param event The event triggered by clicking the 'Exit' button.
	 * @throws IOException If the FXML file for the 'LecturerMainForm' scene is not
	 *                     found.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		if (exams != null) {
			exams.clear();
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerMainForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
