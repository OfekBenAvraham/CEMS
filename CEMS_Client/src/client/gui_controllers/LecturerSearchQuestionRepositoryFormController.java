package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.Message;
import entities.Session;
import enums.Operations;
import enums.RepositoryOperations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class represents a controller for a Lecturer's Search Question
 * Repository Form in a JavaFX application. This class handles user events and
 * updates the view accordingly.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerSearchQuestionRepositoryFormController implements Initializable {

	@FXML
	private Button btnExit;

	@FXML
	private TextField LecturerId;

	@FXML
	private Text textError;

	@FXML
	private Button btnSearch;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * This method is overridden from Initializable interface. It is used to
	 * initialize the controller after its root element has been completely
	 * processed.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
	}

	/**
	 * This method is invoked when the 'Search' button is clicked. It performs a
	 * search on the question repository using the ID provided by the user.
	 *
	 * @param event The event triggered by clicking the 'Search' button.
	 * @throws IOException If the FXML file for the 'Question Repository Form' scene
	 *                     is not found.
	 */
	@FXML
	void searchRepository(ActionEvent event) throws IOException {
		if (LecturerId.getText().length() != 9) {
			textError.setText("The ID must be 9 digits!");
			return;
		}
		ArrayList<String> parameters = new ArrayList<>();
		parameters.add(RepositoryOperations.Search.name());
		parameters.add(LecturerId.getText());
		Message getQuestion = new Message(Operations.GetQuestionRepository, parameters);
		ClientUI.client.accept(getQuestion);
		if (LecturerQuestionRepositoryFormController.questions.isEmpty()) {
			textError.setText("There is no repository with this ID!");
			return;
		}
		Message getName = new Message(Operations.RepositoryOwner, LecturerId.getText());
		ClientUI.client.accept(getName);
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * This method is invoked when the 'Exit' button is clicked. It transitions the
	 * user back to the 'Main Question Repository' scene.
	 *
	 * @param event The event triggered by clicking the 'Exit' button.
	 * @throws IOException If the FXML file for the 'Main Question Repository' scene
	 *                     is not found.
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
				.load(getClass().getResource("/gui/LecturerMainQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
}
