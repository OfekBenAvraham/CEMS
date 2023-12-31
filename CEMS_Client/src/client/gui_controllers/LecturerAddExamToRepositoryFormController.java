package gui_controllers;

import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientController;
import client.ClientUI;
import entities.Course;
import entities.Exam;
import entities.Field;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
import entities.Session;
import enums.ExamType;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.converter.DoubleStringConverter;

/**
 * The LecturerAddExamToRepositoryFormController class provides the main user
 * interface for a Lecturer to add an exam to the repository. The UI includes a
 * form with fields for specifying the details of the exam, a table for
 * selecting questions for the exam, options for submission of the form, and a
 * label to display the lecturer's name.
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerAddExamToRepositoryFormController implements Initializable {
	public static ArrayList<QuestionInExam> questionsInExam;
	public static String examId;

	@FXML
	private CheckBox checkBox;

	@FXML
	private ComboBox<Course> comboBoxCourses;

	@FXML
	private ComboBox<Field> comboBoxFields;

	@FXML
	private Button btnSubmit;

	@FXML
	private Button btnExit;

	@FXML
	private Text textError;

	@FXML
	private Spinner<Integer> examDurationSpinner;

	@FXML
	private TextArea descriptionForExaminee;

	@FXML
	private TextArea descriptionForLecturer;

	@FXML
	private Spinner<String> correctAnswerLabel;

	@FXML
	private TableView<QuestionInExam> questionTable;

	@FXML
	private TableColumn<QuestionInExam, String> questionText;

	@FXML
	private TableColumn<QuestionInExam, String> option1;

	@FXML
	private TableColumn<QuestionInExam, String> option2;

	@FXML
	private TableColumn<QuestionInExam, String> option3;

	@FXML
	private TableColumn<QuestionInExam, String> option4;

	@FXML
	private TableColumn<QuestionInExam, String> correctAnswer;

	@FXML
	private TableColumn<QuestionInExam, Double> points;

	@FXML
	private Label lecturerNameLbl;

	ClientController client;

	/**
	 * Initializes the form for adding an exam to the repository by setting up the
	 * user interface components.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		ObservableList<Field> fieldList = FXCollections.observableArrayList(LecturerMainFormController.fields);
		comboBoxFields.setItems(fieldList);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360);
		examDurationSpinner.setValueFactory(valueFactory);

		comboBoxFields.setConverter(new StringConverter<Field>() {
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
				// Not needed in this case, so return null
				return null;
			}
		});

		comboBoxCourses.setConverter(new StringConverter<Course>() {
			@Override
			public String toString(Course course) {
				if (course == null) {
					return null;
				} else {
					return course.getCourseName();
				}
			}

			@Override
			public Course fromString(String string) {
				return null;
			}
		});
		comboBoxCourses.setItems(FXCollections.observableArrayList());
		questionTable.setEditable(true);
		questionText.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getQuestionText());
		});
		option1.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getOption1());
		});
		option2.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getOption2());
		});
		option3.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getOption3());
		});
		option4.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getOption4());
		});

		correctAnswer.setCellValueFactory(cellData -> {
			Question question = cellData.getValue().getQuestion();
			return new SimpleStringProperty(question.getCorrectAnswer());
		});
		points.setCellValueFactory(new PropertyValueFactory<>("points"));
		points.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		points.setOnEditCommit(event -> {
			QuestionInExam questionInExam = event.getRowValue();
			questionInExam.setPoints(event.getNewValue());
		});
		points.setEditable(true);

		questionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Handles the submission of the form, adds the new exam to the repository, and
	 * redirects the user to the personal exam repository form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void addExam(ActionEvent event) throws IOException {
		if (comboBoxFields.getValue() == null) {
			textError.setText("You must pick field in the field!");
			return;
		}
		if (comboBoxCourses.getValue() == null) {
			textError.setText("You must pick courses in the courses field!");
			return;
		}
		if (examDurationSpinner.getValue() == 0) {
			textError.setText("You must pick a duration!");
			return;
		}
		if (questionTable.getSelectionModel().getSelectedItems().isEmpty()) {
			textError.setText("You must pick a questions!");
			return;
		}
		ObservableList<QuestionInExam> selectedItems = questionTable.getSelectionModel().getSelectedItems();
		ArrayList<QuestionInExam> selectedItemsList = new ArrayList<>(selectedItems);
		for (QuestionInExam q : selectedItemsList) {
			if (q.getPoints() == 0.0) {
				textError.setText("You must add points to the questions you selected!");
				return;
			}
		}
		ExamType e = checkBox.isSelected() ? ExamType.COMPUTERIZED : ExamType.MANUAL;
		Exam exam = new Exam(examDurationSpinner.getValue(), descriptionForExaminee.getText(),
				descriptionForLecturer.getText(), Session.getInstance().getCurrentUser().getFirstName(), false, e,
				Session.getInstance().getCurrentUser().getUserID(), comboBoxCourses.getValue(),
				comboBoxFields.getValue());
		Message message = new Message(Operations.AddExam, exam);
		ClientUI.client.accept(message);
		for (QuestionInExam q : selectedItemsList) {
			q.setExamId(examId);
		}
		message = new Message(Operations.AddQuestionInExam, selectedItemsList);
		ClientUI.client.accept(message);
		ArrayList<String> parameters = new ArrayList<>();
		parameters.add(RepositoryOperations.Personal.name());
		parameters.add(Session.getInstance().getCurrentUser().getUserID());
		Message getExam = new Message(Operations.GetExamRepository, parameters);
		ClientUI.client.accept(getExam);
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
				.load(getClass().getResource("/gui/LecturerPersonalExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Populates the courses combo box based on the selected field.
	 * 
	 * @param event ActionEvent that triggers this method.
	 */
	@FXML
	void getComboBoxField(ActionEvent event) {
		ArrayList<String> data = new ArrayList<>();
		data.add(Session.getInstance().getCurrentUser().getUserID());
		data.add(comboBoxFields.getValue().getFieldCode());
		Message message = new Message(Operations.GetCoursesByField, data);
		ClientUI.client.accept(message);

		ObservableList<Course> courseList = FXCollections.observableArrayList(LecturerMainFormController.courses);
		comboBoxCourses.setItems(courseList);

		comboBoxCourses.setCellFactory(param -> new ListCell<Course>() {
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
	 * Populates the table of questions based on the selected course.
	 * 
	 * @param event ActionEvent that triggers this method.
	 */
	@FXML
	void getComboBoxCourses(ActionEvent event) {
		if (comboBoxCourses.getValue() != null) {
			ArrayList<String> values = new ArrayList<>();
			values.add(Session.getInstance().getCurrentUser().getUserID());
			values.add(comboBoxCourses.getValue().getCourseCode());
			Message message = new Message(Operations.GetQuestionsByCourse, values);
			ClientUI.client.accept(message);

			ObservableList<QuestionInExam> observableQuestionInExam = FXCollections
					.observableArrayList(questionsInExam);
			questionTable.setItems(observableQuestionInExam);
		}
	}

	/**
	 * Handles the action when the exit button is pressed. The current window is
	 * closed and the user is redirected to the personal exam repository form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
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
				.load(getClass().getResource("/gui/LecturerPersonalExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	

}
