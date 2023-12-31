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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The LecturerMainQuestionRepositoryFormController class provides the main user
 * interface for a Lecturer to manage question repositories. The UI includes
 * options to search repositories, get personal repository, exit the application
 * and a label to display lecturer's name. It also includes options to
 * initialize the form.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class LecturerMainQuestionRepositoryFormController implements Initializable {

	@FXML
	private Button btnSearchRepository;

	@FXML
	private Button btnMyRepository;

	@FXML
	private Button btnExit;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * Initializes the Lecturer Main Question Repository form by retrieving the
	 * current user and displaying their name. This method is called by the
	 * FXMLLoader when the initialization is complete.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
	}

	/**
	 * Retrieves the personal question repository of the current user, and displays
	 * it in a new stage.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getMyRepository(ActionEvent event) throws IOException {
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
	 * Allows the user to search repositories and displays the result in a new
	 * stage.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void searchRepository(ActionEvent event) throws IOException {
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
				.load(getClass().getResource("/gui/LecturerSearchQuestionRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Handles the action when the exit button is pressed. The current window is
	 * closed and the user is redirected to the main lecturer form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws Exception If there is an error loading the form.
	 */
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
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
