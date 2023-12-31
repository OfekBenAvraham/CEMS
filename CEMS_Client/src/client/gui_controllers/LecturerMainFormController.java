package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import entities.Lecturer;
import entities.Course;
import entities.Field;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import entities.User;

/**
 * The LecturerMainFormController class provides the main user interface for a
 * Lecturer. The UI includes options to get to question repository, exam
 * repository, view reports, validate exams and view exams in progress. It also
 * includes options to set user and initialize the form.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 *	@author Guy Pariente
 *	@author Almog Elbaz
 */
public class LecturerMainFormController extends ControllerInterface implements Initializable {
	public static Lecturer lecturer;
	
	public static String repositoryOwner;

	public static LecturerMainFormController lecturerMainFormController;

	public static ArrayList<Field> fields;

	public static ArrayList<Course> courses;

	@FXML
	private Button btnQuestionRepository;

	@FXML
	private Button btnExamRepository;

	@FXML
	private Button btnViewReports;

	@FXML
	private Button btnValidateExam;

	@FXML
	private Button btnExamInPrograss;

	@FXML
	private Button btnActiveExam;

	@FXML
	private Label lecturerNameLbl;

	@FXML
	private Button btnExit;

	ClientController client;

	FXMLLoader loader;

	/**
	 * Initializes the form by setting up the current lecturer and displaying their
	 * name. This method is called by the FXMLLoader when the initialization is
	 * complete.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerMainFormController = this;
		Lecturer lecturer = (Lecturer) Session.getInstance().getCurrentUser();
		if (lecturer != null) {
			lecturerMainFormController.lecturer = lecturer;
			this.lecturerNameLbl.setText(lecturer.getLecturerFullName());
		}
	}

	/**
	 * Loads the Question Repository UI when the related button is clicked.
	 *
	 * @param event The event that triggers this method.
	 * @throws IOException If the method is unable to load the fxml file.
	 */
	@FXML
	void getQuestionRepository(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
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

	/**
	 * Opens the form for the lecturer to validate exams.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getValidateExam(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
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
				.load(getClass().getResource("/gui/LecturerPickCourseToValidateExams.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Opens the form showing exams in progress for the lecturer.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getExamInProgress(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsInProgress.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Opens the form showing reports for the lecturer.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getViewReports(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
		ArrayList<String> info = new ArrayList<>();
		info.add(Session.getInstance().getCurrentUser().getUserID());
		Message msg = new Message(Operations.LecturerGetExamsToShowStats,info);
		ClientUI.client.accept(msg);
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerPickExamToSeeStats.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Opens the Exam Repository form for the lecturer.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws IOException If there is an error loading the form.
	 */
	@FXML
	void getExamRepository(ActionEvent event) throws IOException {
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerMainExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
    /**
     * Event handler for the 'Activate Exam' action.
     * When this method is invoked, it activates an exam by communicating with the server,
     * then navigates to the 'LecturerExamRepositoryToActivateForm' scene.
     *
     * @param event The ActionEvent object representing the event-trigger, such as clicking a button.
     * @throws IOException If there is an error while loading the 'LecturerExamRepositoryToActivateForm' scene.
     */
	@FXML
	void activateExam(ActionEvent event) throws IOException {
		String parameter = Session.getInstance().getCurrentUser().getUserID();
		Message getExam = new Message(Operations.GetExamRepositoryToActive, parameter);
		ClientUI.client.accept(getExam);
		loader = new FXMLLoader();
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
				.load(getClass().getResource("/gui/LecturerExamRepositoryToActivateForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Handles the action when the exit button is pressed. The user is logged out
	 * and the login form is displayed.
	 * 
	 * @param event ActionEvent that triggers this method.
	 * @throws Exception If there is an error handling the logout process or loading
	 *                   the login form.
	 */
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/loginPage.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		String userId = Session.getInstance().getCurrentUser().getUserID();
		Message msg = new Message(Operations.UserLogout, userId);
		ClientUI.client.accept(msg);
		Session.getInstance().logout();
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		window.setScene(scene);
		window.show();
	}

	/**
	 * Sets the current user for the form and displays the lecturer's name on the
	 * form.
	 * 
	 * @param user The current user of the form.
	 */
	@Override
	public void setUser(User user) {
		Lecturer lecturer = (Lecturer) Session.getInstance().getCurrentUser();
		this.lecturerNameLbl.setText(lecturer.getLecturerFullName());
	}

}
