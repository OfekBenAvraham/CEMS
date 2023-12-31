package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Lecturer;
import entities.Message;
import entities.Session;
import entities.User;
import enums.Operations;
import enums.Role;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the HeadOfDepartmentViewUsers.fxml file.
 *  @author Guy Pariente	
 * 	  @author Almog Elbaz
 */
public class HeadOfDepartmentViewUsersController implements Initializable {
	public static HeadOfDepartmentViewUsersController headOfDepartmentViewUsersController;
	public static ArrayList<User> users;
	
	@FXML
	private Label headOfDepNameLbl;
	@FXML
	private Label errorlbl;
	@FXML
	private Button viewDataBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<User> dataTbl;
	@FXML
	private TableColumn<User, String> idCol;
	@FXML
	private TableColumn<User, String> firstNameCol;
	@FXML
	private TableColumn<User, String> lastNameCol;
	@FXML
	private TableColumn<User, Role> roleCol;
	

	/**
	 * Initializes the HeadOfDepartmentViewUsersController.
	 * This method is automatically called after the FXML file has been loaded.
	 * It sets up the initial state of the controller and populates the table view with user data.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headOfDepartmentViewUsersController = this;
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if(users == null)
			return;
		idCol.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		roleCol.setCellValueFactory(new PropertyValueFactory<User, Role>("role"));
		ObservableList<User> data = FXCollections.observableArrayList(users);
		dataTbl.setItems(data);	
	}
	
	/**
	 * Handles the action when the back button is pressed.
	 * Closes the current window and redirects the user to the main options form.
	 * 
	 * @param event The action event triggered by clicking the button.
	 * @throws IOException If an error occurs during the loading of the form.
	 */
	@FXML
	void getBackBtn(ActionEvent event) throws IOException {
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
		AnchorPane root = loader.load(getClass().getResource("/gui/HeadOfDepartmentMainOptionPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
