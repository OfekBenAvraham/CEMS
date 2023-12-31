package gui_controllers;

import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientController;
import client.ClientUI;
import entities.Course;
import entities.Field;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for managing the Lecturer Add Question to Repository form. This
 * form allows a lecturer to add a question to a repository.
 * 
 * The lecturer selects a field, chooses courses from a list, enters the
 * question text and options, and chooses the correct option from a spinner.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class LecturerAddQuestionToRepositoryFormController implements Initializable {

	@FXML
	private ListView<Course> courseListView;

	@FXML
	private ComboBox<Field> comboBox;

	@FXML
	private Button btnSubmit;

	@FXML
	private Button btnExit;

	@FXML
	private Text textError;

	@FXML
	private TextArea txtQuestion;

	@FXML
	private TextArea option1;

	@FXML
	private TextArea option2;

	@FXML
	private TextArea option3;

	@FXML
	private TextArea option4;
	

	@FXML
	private Label lecturerName;

	@FXML
	private Spinner<String> correctAnswerLabel;

	ObservableList<String> answerList = FXCollections.observableArrayList("option1", "option2", "option3", "option4");

	SpinnerValueFactory<String> correctAnswerLabelValue = new SpinnerValueFactory.ListSpinnerValueFactory<>(answerList);

	ClientController client;

	/**
	 * Initializes the form components with default values.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerName.setText(Session.getInstance().getCurrentUser().getFullName());
		correctAnswerLabel.setValueFactory(correctAnswerLabelValue);
		ObservableList<Field> fieldList = FXCollections.observableArrayList(LecturerMainFormController.fields);
		comboBox.setItems(fieldList);

		comboBox.setConverter(new StringConverter<Field>() {
			@Override
			public String toString(Field field) {
				if (field == null) {
					return null;
				} else {
					return field.getFieldName();
				}
			}

			@Override
			public Field fromString(String string) {
				return null;
			}
		});

		courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Handles the event when the Add Question button is clicked.
	 * 
	 * Validates the form fields and creates a new Question to be added to the
	 * repository.
	 *
	 * @param event The event triggered by clicking the Add Question button.
	 * @throws IOException If an error occurs during I/O operations.
	 */
	@FXML
	void addQuestion(ActionEvent event) throws IOException {
		ObservableList<Course> selectedCourses = courseListView.getSelectionModel().getSelectedItems();
		if (comboBox.getValue() == null) {
			textError.setText("You must pick field in the field!");
			return;
		}
		if (courseListView.getSelectionModel().getSelectedItem() == null) {
			textError.setText("You must pick courses in the courses field!");
			return;
		}
		if (txtQuestion.getText().length() == 0) {
			textError.setText("You must write the question in the question description field!");
			return;
		}
		if (option1.getText().length() == 0) {
			textError.setText("You must write the option1 in the option1 field!");
			return;
		}

		if (option2.getText().length() == 0) {
			textError.setText("You must write the option2 in the option2 field!");
			return;
		}
		if (option3.getText().length() == 0) {
			textError.setText("You must write the option3 in the option3 field!");
			return;
		}
		if (option4.getText().length() == 0) {
			textError.setText("You must write the option4 in the option4 field!");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (Course course : selectedCourses) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(course.getCourseCode());
		}

		String selectedCoursesCodes = sb.toString();
		Question q = new Question(comboBox.getValue().getFieldCode(), comboBox.getValue().getFieldName(),
				selectedCoursesCodes, txtQuestion.getText(), Session.getInstance().getCurrentUser().getFirstName(),
				Session.getInstance().getCurrentUser().getUserID(), option1.getText(), option2.getText(),
				option3.getText(), option4.getText(), correctAnswerLabel.getValue());
		Message message = new Message(Operations.AddQuestion, q);
		ClientUI.client.accept(message);
		ArrayList<String> parameters = new ArrayList<>();
		parameters.add(RepositoryOperations.Personal.name());
		parameters.add(Session.getInstance().getCurrentUser().getUserID());
		Message getQuestion = new Message(Operations.GetQuestionRepository, parameters);
		ClientUI.client.accept(getQuestion);
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
				.load(getClass().getResource("/gui/LecturerPersonalQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Updates the list of courses when a new field is selected in the combo box.
	 *
	 * @param event The event triggered by selecting a new field.
	 */
	@FXML
	void getComboBox(ActionEvent event) {
		ArrayList<String> data = new ArrayList<>();
		data.add(Session.getInstance().getCurrentUser().getUserID());
		data.add(comboBox.getValue().getFieldCode());
		Message message = new Message(Operations.GetCoursesByField, data);
		ClientUI.client.accept(message);

		ObservableList<Course> courseList = FXCollections.observableArrayList(LecturerMainFormController.courses);
		courseListView.setItems(courseList);

		courseListView.setCellFactory(param -> new ListCell<Course>() {
			@Override
			protected void updateItem(Course item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getCourseName() == null) {
					setText(null);
				} else {
					setText(item.getCourseName());
				}
			}
		});
	}

	/**
	 * Handles the event when the Exit button is clicked.
	 * 
	 * Hides the current form and displays the
	 * LecturerPersonalQuestionRepositoryForm.
	 *
	 * @param event The event triggered by clicking the Exit button.
	 * @throws IOException If an error occurs during I/O operations.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
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
				.load(getClass().getResource("/gui/LecturerPersonalQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
