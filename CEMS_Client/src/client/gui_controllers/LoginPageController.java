package gui_controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import controllers.AbstractController;
import controllers.Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.HeadOfDepartment;
import entities.Lecturer;
import entities.Session;
import entities.Student;
import entities.Message;
import enums.Operations;

/**
 * This class represents a controller for the Login page in a JavaFX application.
 * It handles user events, updates the view accordingly, and manages the user login processes.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LoginPageController extends AbstractController implements Initializable {

	public static LoginPageController loginPageController;

	@FXML
	private Button loginBtn;

	@FXML
	private Label errorlbl;

	@FXML
	private TextField usernameField;

	@FXML
	private TextField passwordField;

	@FXML
	private RadioButton studentSelect;

	@FXML
	private RadioButton LecturerSelect;

	@FXML
	private RadioButton HeadOfDepSelect;

	@FXML
	private ToggleGroup loginTgl;

	@FXML
	private ImageView userImage;

	@FXML
	private ImageView cemsLogoImg;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets the static instance of LoginPageController to this instance.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginPageController = this;
	}
	
    /**
     * Handles the login button press. Checks if the username and password fields are filled and a role is selected.
     * Then, it initiates a login request to the server.
     *
     * @param event The event triggered by pressing the login button.
     * @throws Exception If there's an error during the login process.
     */
	@FXML
	void loginToServer(ActionEvent event) throws Exception {
		ToggleGroup select = loginTgl;
		RadioButton selected = (RadioButton) select.getSelectedToggle();
		String userType = ""; // Student | Lecturer | Head Of Department
		if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
			errorlbl.setText("You Must fill Username and Password");
			return;
		}

		if (select.getSelectedToggle() != null)
			userType = selected.getText().toString();
		else {
			errorlbl.setText("Please Select Role.");
			return;
		}
		Login.userRequestLogin(usernameField.getText(), passwordField.getText(), event, userType);

	}

    /**
     * Sets up the view and the session for a logged in Lecturer.
     *
     * @param lecturer The logged in Lecturer.
     * @param event The event that triggered the login.
     */
	public void setLecturerLogin(Lecturer lecturer, ActionEvent event) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root;
				try {
					Stage primaryStage1 = new Stage();
					primaryStage1.setResizable(false);
					root = loader.load(getClass().getResource("/gui/LecturerMainForm.fxml").openStream());
					Session.getInstance().setCurrentUser(lecturer);
					((ControllerInterface) loader.getController()).setUser(lecturer); 
																						
					((Node) event.getSource()).getScene().getWindow().hide(); 

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

					Scene scene = new Scene(root);
					primaryStage1.setTitle("Lecturer Main Form");
					primaryStage1.setScene(scene);
					primaryStage1.show();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

	}

    /**
     * Sets up the view and the session for a logged in HeadOfDepartment.
     *
     * @param headOfDep The logged in HeadOfDepartment.
     * @param event The event that triggered the login.
     */
	public void setHeadOfDepartmentLogin(HeadOfDepartment headOfDep, ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root;
				try {
					Stage primaryStage1 = new Stage();
					primaryStage1.setResizable(false);
					root = loader.load(getClass().getResource("/gui/HeadOfDepartmentMainOptionPage.fxml").openStream());
					Session.getInstance().setCurrentUser(headOfDep);
					((ControllerInterface) loader.getController()).setUser(headOfDep);
					((Node) event.getSource()).getScene().getWindow().hide(); 

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

					Scene scene = new Scene(root);
					primaryStage1.setTitle("Head Of Department Main Form");
					primaryStage1.setScene(scene);
					primaryStage1.show();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}
	
    /**
     * Sets up the view and the session for a logged in Student.
     *
     * @param student The logged in Student.
     * @param event The event that triggered the login.
     */
	public void setStudentLogin(Student student, ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				AnchorPane root;
				try {
					Stage primaryStage1 = new Stage();
					primaryStage1.setResizable(false);
					root = loader.load(getClass().getResource("/gui/StudentMainOptionForm.fxml").openStream());
					Session.getInstance().setCurrentUser(student);
					((ControllerInterface) loader.getController()).setUser(student);
					((Node) event.getSource()).getScene().getWindow().hide(); 

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

					Scene scene = new Scene(root);
					primaryStage1.setTitle("Student Main Form");
					primaryStage1.setScene(scene);
					primaryStage1.show();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

    /**
     * Sets the error label text on the login view.
     *
     * @param msg The message to be displayed on the error label.
     */
	public void setErrorLabel(String msg) {
		this.errorlbl.setText(msg);
	}
	



}
