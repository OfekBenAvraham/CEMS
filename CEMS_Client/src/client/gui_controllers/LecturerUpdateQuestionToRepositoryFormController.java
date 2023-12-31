package gui_controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientController;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class represents a controller for the Lecturer's Update Question to
 * Repository Form in a JavaFX application. It handles user events and updates
 * the view accordingly.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerUpdateQuestionToRepositoryFormController implements Initializable {
	public static Question q;
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
	private Spinner<String> correctAnswerLabel;

	@FXML
	private Label lecturerNameLbl;

	ObservableList<String> answerList = FXCollections.observableArrayList("option1", "option2", "option3", "option4");

	SpinnerValueFactory<String> correctAnswerLabelValue = new SpinnerValueFactory.ListSpinnerValueFactory<>(answerList);

	ClientController client;

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
		this.lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		correctAnswerLabel.setValueFactory(correctAnswerLabelValue);
		correctAnswerLabelValue.setValue(q.getCorrectAnswer());
		txtQuestion.textProperty().setValue(q.getQuestionText());
		option1.setText(q.getOption1());
		option2.setText(q.getOption2());
		option3.setText(q.getOption3());
		option4.setText(q.getOption4());
	}

	/**
	 * This method is invoked when the 'Update Question' button is clicked. It
	 * validates the inputs, prepares the parameters for updating the question,
	 * sends the update request to the server, gets the updated question repository
	 * and then opens the 'Lecturer Personal Question Repository Form'.
	 *
	 * @param event The event triggered by clicking the 'Update Question' button.
	 * @throws IOException If the FXML file for the 'Lecturer Personal Question
	 *                     Repository Form' is not found.
	 */
	@FXML
	void updateQuestion(ActionEvent event) throws IOException {
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
		ArrayList<String> updateParameters = new ArrayList<>();
		updateParameters.add(q.getId());
		updateParameters.add(txtQuestion.getText());
		updateParameters.add(option1.getText());
		updateParameters.add(option2.getText());
		updateParameters.add(option3.getText());
		updateParameters.add(option4.getText());
		updateParameters.add(correctAnswerLabel.getValue());
		Message message = new Message(Operations.UpdateQuestion, updateParameters);
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
	 * This method is invoked when the 'Exit' button is clicked. It hides the
	 * current window and loads the 'Lecturer Personal Question Repository Form'.
	 *
	 * @param event The event triggered by clicking the 'Exit' button.
	 * @throws IOException If the FXML file for the 'Lecturer Personal Question
	 *                     Repository Form' is not found.
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
