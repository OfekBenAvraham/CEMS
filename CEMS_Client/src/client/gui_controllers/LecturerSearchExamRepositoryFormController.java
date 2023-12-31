package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.User;
import enums.Operations;
import enums.RepositoryOperations;
import enums.Role;
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
 * The LecturerSearchExamRepositoryFormController class provides the main user
 * interface for a Lecturer to search the exam repository. The UI includes
 * options to search the repository, input lecturer ID and exit the application.
 * It also includes options to initialize the form.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class LecturerSearchExamRepositoryFormController implements Initializable {

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
	 * Initializes the lecturer search exam repository form by retrieving the
	 * current user and displaying their name. This method is called by the
	 * FXMLLoader when the initialization is complete.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
	}

	/**
	 * Searches the exam repository for the lecturer ID input by the user. If there
	 * are no exams found for the lecturer ID, an error message is displayed. If the
	 * lecturer ID is not 9 digits, an error message is displayed. If exams are
	 * found, the Lecturer Exam Repository form is opened.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
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
		Message getExam = new Message(Operations.GetExamRepository, parameters);
		ClientUI.client.accept(getExam);
		Message getName = new Message(Operations.RepositoryOwner, LecturerId.getText());
		ClientUI.client.accept(getName);
		if (LecturerExamRepositoryFormController.exams.isEmpty()) {
			textError.setText("There is no repository with this ID!");
			return;
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Handles the action when the exit button is pressed. The user is redirected to
	 * the lecturer main exam repository form.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		User user = Session.getInstance().getCurrentUser();
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
		if(user.getRole() == Role.LECTURER)
		{
			root = loader.load(getClass().getResource("/gui/LecturerMainExamRepositoryForm.fxml").openStream());
		}

		else
		{
			root = loader.load(getClass().getResource("/gui/HeadOfDepartmentMainOptionPage.fxml").openStream());
		}
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	

}
