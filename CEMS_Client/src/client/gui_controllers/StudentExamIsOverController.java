package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the student exam completion page.
 *
 * @author Maayan Avittan
 * @author Paz Fayer
 * 
 */
public class StudentExamIsOverController extends ControllerInterface implements Initializable {

	public static StudentExamIsOverController studentExamIsOverController;
	public static Student student;

	@FXML
	private Label lblFirstName;
	@FXML
	private Button btnBackToMainPage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentExamIsOverController = this;
		Student student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentExamIsOverController.student = student;
			this.lblFirstName.setText(student.getFullName());
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void setUser(User user) {
		studentExamIsOverController.student = (Student) user;
		this.lblFirstName.setText(student.getFullName());
	}

	/**
	 * Handles the event when the exit button is clicked.
	 *
	 * @param event the action event
	 * @throws IOException if an I/O exception occurs
	 */
	@FXML
	void ExitFromExamIsOverButton(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/StudentMainOptionForm.fxml"));
		Scene scene = new Scene(root2);
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		window.setScene(scene);
		window.show();
	}
}
