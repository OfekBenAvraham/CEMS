package gui_controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.Exam;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class represents a controller for the Lecturer's Show Exam Form in a
 * JavaFX application. It handles user events and updates the view accordingly.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerShowExamFormController implements Initializable {
	public static ArrayList<QuestionInExam> questionsInExam;
	public static boolean isOwner;
	private static ArrayList<Question> questions;
	public static Exam exam;

	@FXML
	private Label examId;

	@FXML
	private Button btnExit;

	@FXML
	private TextArea descriptionForExaminee;

	@FXML
	private TextArea descriptionForLecturer;

	@FXML
	private TableView<Question> table;

	@FXML
	private TableColumn<Question, String> id;
	@FXML
	private TableColumn<Question, String> question;
	@FXML
	private TableColumn<Question, String> option1;
	@FXML
	private TableColumn<Question, String> option2;
	@FXML
	private TableColumn<Question, String> option3;
	@FXML
	private TableColumn<Question, String> option4;
	@FXML
	private TableColumn<Question, String> correctAnswer;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * This method is overridden from the Initializable interface. It initializes
	 * the controller after its root element has been completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if (questionsInExam == null) {
			return;
		}
		questions = new ArrayList<Question>();
		for (QuestionInExam q : questionsInExam) {
			questions.add(q.getQuestion());
		}
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		question.setCellValueFactory(new PropertyValueFactory<>("questionText"));
		option1.setCellValueFactory(new PropertyValueFactory<>("option1"));
		option2.setCellValueFactory(new PropertyValueFactory<>("option2"));
		option3.setCellValueFactory(new PropertyValueFactory<>("option3"));
		option4.setCellValueFactory(new PropertyValueFactory<>("option4"));
		correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
		ObservableList<Question> data = FXCollections.observableArrayList(questions);
		table.setItems(data);
		examId.setText(exam.getExamID());
		descriptionForExaminee.setText(exam.getDescriptionForExaminee());
		descriptionForLecturer.setText(exam.getDescriptionForLecturer());
		descriptionForExaminee.setEditable(false);
		descriptionForLecturer.setEditable(false);
	}

	/**
	 * This method is invoked when the 'Exit' button is clicked. It clears the
	 * questions list, hides the current window and loads the respective next scene
	 * depending on whether the user is the owner of the exam.
	 *
	 * @param event The event triggered by clicking the 'Exit' button.
	 * @throws IOException If the FXML file for the respective next scene is not
	 *                     found.
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
		AnchorPane root;
		if (isOwner) {
			root = loader.load(getClass().getResource("/gui/LecturerPersonalExamRepositoryForm.fxml").openStream());
		} else {
			root = loader.load(getClass().getResource("/gui/LecturerExamRepositoryForm.fxml").openStream());
		}
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
