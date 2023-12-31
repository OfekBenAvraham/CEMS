package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.HeadOfDepartment;
import entities.Lecturer;
import entities.Message;
import entities.Session;
import entities.User;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the Head of Department Main Option Page.
 * This class handles the user interface and functionality of the main options available to the Head of Department.
 * It interacts with the server and other controller classes to perform operations such as viewing pending time change requests,
 * viewing lecturer exams, viewing statistics reports, and managing user data.
 * @author Guy Pariente	
 * @author Almog Elbaz
 */
public class HeadOfDepartmentMainOptionController extends ControllerInterface implements Initializable{
	/**
     * Reference to the current instance of HeadOfDepartmentMainOptionController.
     */
    public static HeadOfDepartmentMainOptionController headOfDepartmentMainOptionPageController;
    
    /**
     * The current Head of Department.
     */
    public static HeadOfDepartment headOfDep;

	@FXML
	private Button btnPending;
	@FXML
	private Button btnViewData;
	@FXML
	private Button btnStatistics;
	@FXML
	private Button btnExit;
	@FXML
	private Button logoutBtn;
	@FXML
	private Label headOfDepNameLbl;
	
	private FXMLLoader loader = new FXMLLoader();
	
	/**
     * Initializes the HeadOfDepartmentMainOptionController.
     * Sets the current instance of HeadOfDepartmentMainOptionController and retrieves the current Head of Department from the session.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepartmentMainOptionPageController = this;
		HeadOfDepartment headOfDep = (HeadOfDepartment) Session.getInstance().getCurrentUser();
		if (headOfDep != null) {
			headOfDepartmentMainOptionPageController.headOfDep = headOfDep;
		    this.headOfDepNameLbl.setText(headOfDep.getFullName());
		}
		
	}
	/**
     * Handles the action of clicking the "Pending Time Requests" button.
     * Retrieves the field code of the current Head of Department and sends a message to the server to get the pending time change requests.
     * Loads the PendingTimeChangeRequestsForm.
     *
     * @param event The action event triggered by clicking the button.
     * @throws Exception If an error occurs during the loading of the form.
     */
	public void pendingTimeRequests(ActionEvent event) throws Exception {
		HeadOfDepartment head = (HeadOfDepartment) Session.getInstance().getCurrentUser();
		String fieldCode = head.getFieldCode();
		Message msg = new Message(Operations.HeadOfDepGetRequests);
		ClientUI.client.accept(msg);
		PendingTimeChangeRequestsController.fieldCode = fieldCode;
		loadForm("/gui/PendingTimeChangeRequestsForm.fxml", "Pending Time Change Requests", event);
	}

	 /**
     * Handles the action of clicking the "View Lecturer Exams" button.
     * Loads the LecturerSearchExamRepositoryForm.
     *
     * @param event The action event triggered by clicking the button.
     * @throws Exception If an error occurs during the loading of the form.
     */
	public void viewLecturerExams(ActionEvent event) throws Exception {
		loadForm("/gui/LecturerSearchExamRepositoryForm.fxml", "Lecturer Search Form", event);
	}

	 /**
     * Handles the action of clicking the "View Statistics Report" button.
     * Loads the HeadOfDepartmentNavigationStats form.
     *
     * @param event The action event triggered by clicking the button.
     * @throws Exception If an error occurs during the loading of the form.
     */
	public void viewStatisticsReport(ActionEvent event) throws Exception {
		loadForm("/gui/HeadOfDepartmentNavigationStats.fxml", "Statistical Report Search", event);
	}
	
	/**
	 * Handles the action of clicking the "View Users" button.
	 * Sends a message to the server to retrieve the users.
	 * Loads the HeadOfDepartmentViewUsers form.
	 *
	 * @param event The action event triggered by clicking the button.
	 * @throws Exception If an error occurs during the loading of the form.
	 */
	public void viewUsers(ActionEvent event) throws Exception {
		Message msg = new Message(Operations.HeadOfDepGetUsers);
		ClientUI.client.accept(msg);
		loadForm("/gui/HeadOfDepartmentViewUsers.fxml", "View Data", event);
	}
	
	/**
	 * Starts the Head of Department Main Option Page.
	 *
	 * @param primaryStage The primary stage to display the page.
	 * @throws Exception If an error occurs during the loading of the form.
	 */
	public void start(Stage primaryStage) throws Exception {
		loadForm("/gui/HeadOfDepartmentMainOptionPage.fxml", "Head Of Department Main Page", null);
	}
	
	/**
	 * Loads the next form.
	 *
	 * @param resource The resource path of the form to load.
	 * @param title    The title of the form.
	 * @param event    The action event associated with the button click.
	 */
	private void loadForm(String resource, String title, ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource(resource).openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the action of clicking the "Back" button.
	 * Loads the loginPage form.
	 *
	 * @param event The action event triggered by clicking the button.
	 * @throws IOException If an error occurs during the loading of the form.
	 */
	// handles the exit button.
	@FXML
	void backForm(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/loginPage.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Handles the action of clicking the "Logout" button.
	 * Logs out the user, closes the client connection, and loads the loginPage form.
	 *
	 * @param event The action event triggered by clicking the button.
	 * @throws IOException If an error occurs during the loading of the form.
	 */
	@FXML
	void logout(ActionEvent event) throws IOException{
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
	 * Sets the current user for the controller.
	 *
	 * @param user The user to set.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void setUser(User user) {
		HeadOfDepartment headOfDep = (HeadOfDepartment) Session.getInstance().getCurrentUser();
		this.headOfDepNameLbl.setText(headOfDep.getFullName());	
	}
	
}
