package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Message;
import entities.Question;
import entities.Session;
import enums.Operations;
import enums.RepositoryOperations;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class represents a controller for a Lecturer's Personal Question
 * Repository Form in a JavaFX application. This class handles user events and
 * updates the view accordingly.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerPersonalQuestionRepositoryFormController implements Initializable {
	public static String isDeleted = null;
	public static ArrayList<Question> questions;
	@FXML
	private Button btnAddQuestion;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnUpdateQuestion;

	@FXML
	private TableView<Question> table;

	@FXML
	private TableColumn<Question, String> id;

	@FXML
	private TableColumn<Question, String> question;

	@FXML
	private TableColumn<Question, String> fieldName;

	@FXML
	private Text textError;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * This method is overridden from Initializable interface. It is used to
	 * initialize the controller after its root element has been completely
	 * processed. It sets up the TableView by linking table columns with
	 * corresponding properties of the Question instances.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if (questions == null) {
			return;
		}
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		question.setCellValueFactory(new PropertyValueFactory<>("questionText"));
		fieldName.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
		// Add these lines for each column
		ObservableList<Question> data = FXCollections.observableArrayList(questions);
		table.setItems(data);
	}

	/**
	 * This method is invoked when the 'Add Question' button is clicked. It
	 * transitions the user to the 'Add Question' scene.
	 *
	 * @param event The event triggered by clicking 'Add Question' button.
	 * @throws IOException If the FXML file for the 'Add Question' scene is not
	 *                     found.
	 */
	@FXML
	void addQuestion(ActionEvent event) throws IOException {
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
		AnchorPane root = loader
				.load(getClass().getResource("/gui/LecturerAddQuestionToRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * This method is invoked when the 'Update Question' button is clicked. It
	 * transitions the user to the 'Update Question' scene for the selected
	 * question.
	 *
	 * @param event The event triggered by clicking 'Update Question' button.
	 * @throws IOException If the FXML file for the 'Update Question' scene is not
	 *                     found.
	 */
	@FXML
	void updateQuestion(ActionEvent event) throws IOException {
		ObservableList<Question> question = table.getSelectionModel().getSelectedItems();
		if (question.isEmpty()) {
			textError.setText("you need choose question to update");
		} else {
			LecturerUpdateQuestionToRepositoryFormController.q = new Question(question.get(0).getId(),
					question.get(0).getField(), question.get(0).getFieldName(), question.get(0).getCourses(),
					question.get(0).getQuestionText(), question.get(0).getQuestionNumber(),
					question.get(0).getLecturerName(), question.get(0).getLecturerID(), question.get(0).getOption1(),
					question.get(0).getOption2(), question.get(0).getOption3(), question.get(0).getOption4(),
					question.get(0).getCorrectAnswer());
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
					.load(getClass().getResource("/gui/LecturerUpdateQuestionToRepositoryForm.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage1.setScene(scene);
			primaryStage1.show();
		}
	}

	/**
	 * This method is invoked when the 'Delete Question' button is clicked. It
	 * deletes the selected question from the table.
	 *
	 * @param event The event triggered by clicking 'Delete Question' button.
	 * @throws IOException If an I/O error occurs during communication with the
	 *                     server.
	 */
	@FXML
	void deleteQuestion(ActionEvent event) throws IOException {
		ObservableList<Question> question = table.getSelectionModel().getSelectedItems();
		if (question.isEmpty()) {
			textError.setText("you need to choose question to delete");
		} else {
			String idToDelete = question.get(0).getId();
			Message message = new Message(Operations.DeleteQuestionById, idToDelete);
			ClientUI.client.accept(message);
			if (isDeleted != null) {
				textError.setText("you can't delete becouse the question is a part of this exams id: " + isDeleted);
				isDeleted = null;
				return;
			}
			ArrayList<String> parameters = new ArrayList<>();
			parameters.add(RepositoryOperations.Personal.name());
			parameters.add(Session.getInstance().getCurrentUser().getUserID());
			Message getQuestion = new Message(Operations.GetQuestionRepository, parameters);
			ClientUI.client.accept(getQuestion);
			ObservableList<Question> data = FXCollections.observableArrayList(questions);
			table.setItems(data);
		}

	}

	/**
	 * This method is invoked when the 'Exit' button is clicked. It transitions the
	 * user back to the 'Main Question Repository' scene.
	 *
	 * @param event The event triggered by clicking 'Exit' button.
	 * @throws IOException If the FXML file for the 'Main Question Repository' scene
	 *                     is not found.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		questions.clear();
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
				.load(getClass().getResource("/gui/LecturerMainQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
