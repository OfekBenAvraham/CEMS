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
 * The LecturerExamRepositoryFormController class provides the main user
 * interface for a Lecturer to view the exam repository. The UI includes options
 * to show an exam, exit the application and a table displaying exam details. It
 * also includes options to initialize the form.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class LecturerExamRepositoryFormController implements Initializable {
	public static ArrayList<Exam> exams;

	@FXML
	private Label lblrepositoryOwner;
	
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
	private Text textError;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * Initializes the Lecturer Exam Repository form by retrieving the current user
	 * and displaying their name. Sets up the table view with the exams if any
	 * exist. This method is called by the FXMLLoader when the initialization is
	 * complete.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if (exams == null) {
			return;
		}
		id.setCellValueFactory(new PropertyValueFactory<>("examID"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
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
		lblrepositoryOwner.setText(LecturerMainFormController.repositoryOwner);
	}

	/**
	 * Displays the details of the selected exam in the table. If no exam is
	 * selected, an error message is displayed. If an exam is selected, the details
	 * are shown in the Lecturer Show Exam form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void showExam(ActionEvent event) throws IOException {
		Exam e = table.getSelectionModel().getSelectedItem();
		if (e == null) {
			textError.setText("You must to select an exam!");
			return;
		}
		LecturerShowExamFormController.exam = e;
		Message message = new Message(Operations.ShowQuestionsInExam, e.getExamID());
		ClientUI.client.accept(message);
		LecturerShowExamFormController.isOwner = false;
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Handles the action when the exit button is pressed. If there are any exams,
	 * the list is cleared. The user is redirected to the lecturer search exam
	 * repository form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
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
		
		AnchorPane root = loader
				.load(getClass().getResource("/gui/LecturerSearchExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	

}
