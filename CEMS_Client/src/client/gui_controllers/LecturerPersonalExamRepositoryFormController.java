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
import enums.RepositoryOperations;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * This class represents a controller for a Lecturer's Personal Exam Repository
 * Form in a JavaFX application. This class handles user events and updates the
 * view accordingly.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerPersonalExamRepositoryFormController implements Initializable {
	public static ArrayList<Exam> exams;
	@FXML
	private Button btnAddExam;

	@FXML
	private Button btnDelete;

	@FXML
	private Button showExam;

	@FXML
	private Button btnDefineCode;

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
	 * This method is overridden from Initializable interface. It is used to
	 * initialize the controller after its root element has been completely
	 * processed. It sets up the TableView by linking table columns with
	 * corresponding properties of the Exam instances.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
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
	}

	/**
	 * This method is invoked when the 'Add Exam' button is clicked. It transitions
	 * the user to the 'Add Exam' scene.
	 *
	 * @param event The event triggered by clicking 'Add Exam' button.
	 * @throws IOException If the FXML file for the 'Add Exam' scene is not found.
	 */
	@FXML
	void addExam(ActionEvent event) throws IOException {
		Message message = new Message(Operations.GetFields);
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerAddExamToRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * This method is invoked when the 'Delete Exam' button is clicked. It deletes
	 * the selected exam from the table.
	 *
	 * @param event The event triggered by clicking 'Delete Exam' button.
	 * @throws IOException If an I/O error occurs during communication with the
	 *                     server.
	 */
	@FXML
	void deleteExam(ActionEvent event) throws IOException {
		ObservableList<Exam> exam = table.getSelectionModel().getSelectedItems();
		if (exam.isEmpty()) {
			textError.setText("you need to choose exam to delete");
		} else {
			String idToDelete = exam.get(0).getExamID();
			Message message = new Message(Operations.DeleteExamById, idToDelete);
			ClientUI.client.accept(message);
			ArrayList<String> parameters = new ArrayList<>();
			parameters.add(RepositoryOperations.Personal.name());
			parameters.add(Session.getInstance().getCurrentUser().getUserID());
			Message getExam = new Message(Operations.GetExamRepository, parameters);
			ClientUI.client.accept(getExam);
			ObservableList<Exam> data = FXCollections.observableArrayList(exams);
			table.setItems(data);
		}

	}

	/**
	 * This method is invoked when the 'Show Exam' button is clicked. It transitions
	 * the user to the 'Show Exam' scene for the selected exam.
	 *
	 * @param event The event triggered by clicking 'Show Exam' button.
	 * @throws IOException If the FXML file for the 'Show Exam' scene is not found.
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
		LecturerShowExamFormController.isOwner = true;
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
	 * This method is invoked when the 'Define Code to Exam' button is clicked. It
	 * transitions the user to the 'Define Exam Code' scene for the selected exam.
	 *
	 * @param event The event triggered by clicking 'Define Code to Exam' button.
	 * @throws IOException If the FXML file for the 'Define Exam Code' scene is not
	 *                     found.
	 */
	@FXML
	void defineCodeToExam(ActionEvent event) throws IOException {
		Exam exam = table.getSelectionModel().getSelectedItem();
		if (exam == null) {
			textError.setText("You must to select an exam!");
			return;
		}
		LecturerDefineExamCodeFormController.exam = exam;
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerDefineExamCodeForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * This method is invoked when the 'Exit' button is clicked. It transitions the
	 * user back to the 'Main Exam Repository' scene.
	 *
	 * @param event The event triggered by clicking 'Exit' button.
	 * @throws IOException If the FXML file for the 'Main Exam Repository' scene is
	 *                     not found.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		exams.clear();
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerMainExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
