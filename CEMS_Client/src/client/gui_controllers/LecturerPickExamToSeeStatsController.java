package gui_controllers;

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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.ExamsDisplayData;
import entities.Message;
import entities.Session;
import enums.Operations;
import enums.Role;

/**
 * The controller class for the LecturerPickExamToSeeStats.fxml file.
 * It handles the display of exams available for the lecturer to view statistics.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerPickExamToSeeStatsController implements Initializable {
    /**
     * The instance of the controller itself.
     */
    public static LecturerPickExamToSeeStatsController lecturerPickExamToSeeStatsController;

    /**
     * The list of exams to be displayed for the lecturer to view statistics.
     */
    public static ArrayList<ExamsDisplayData> exams;

    /**
     * The navigation direction or source of the current form.
     */
    public static String navigationBack;
	
    @FXML
    private TableView<ExamsDisplayData> examsTbl;

    @FXML
    private TableColumn<ExamsDisplayData, String> courseCodeCln;

    @FXML
    private TableColumn<ExamsDisplayData, String> courseNameClm;

    @FXML
    private TableColumn<ExamsDisplayData, String> examCodeClm;

    @FXML
    private TableColumn<ExamsDisplayData, Timestamp> dateClm;

    @FXML
    private TableColumn<ExamsDisplayData, String> examIdCol;
    
    @FXML
    private Button btnBack;

   @FXML
   private Label errorLbl;
   
   @FXML
   private Label lecturerNameLbl;

   
   /**
    * Initializes the LecturerPickExamToSeeStatsController.
    * This method is automatically called after the FXML file has been loaded.
    * It sets up the initial state of the controller.
    *
    * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
    * @param resources The resources used to localize the root object, or null if the root object was not localized.
    */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			lecturerPickExamToSeeStatsController = this;
	    	lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
	        courseCodeCln.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("courseCode"));
	        courseNameClm.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("courseName"));
	        examCodeClm.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("examCode"));
	        dateClm.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, Timestamp>("date"));
	        examIdCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("examId"));
	        if(exams == null)
	        	return;
	        ObservableList<ExamsDisplayData> data = FXCollections.observableArrayList(exams);
	        examsTbl.setItems(data);
		}
		
		
		/**
	     * Handles the action when the "Show Exam" button is pressed.
	     * It retrieves the selected exam and navigates to the LecturerViewExamStats.fxml file to display the statistics.
	     *
	     * @param event ActionEvent that triggers this method.
	     * @throws IOException If there is an error loading the form.
	     */
		@FXML
		void showExam(ActionEvent event) throws IOException {
			
			ExamsDisplayData selectedExam = examsTbl.getSelectionModel().getSelectedItem();
		    if (selectedExam != null) 
		    {
		    	LecturerStatisticalExamReportController.exam = selectedExam;
		    } 
		    else {
		    	errorLbl.setText("Please Select Exam.");
		    	return;
		    }
			
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage1 = new Stage();
			AnchorPane root = loader.load(getClass().getResource("/gui/LecturerViewExamStats.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage1.setScene(scene);
			primaryStage1.show();
		}
		
		
	    /**
	     * Handles the action when the "Exit" button is pressed.
	     * It navigates back to the respective form based on the value of the navigationBack variable.
	     * If the user role is a lecturer, it goes to the LecturerMainForm.fxml file.
	     * If the navigationBack is "headOfDepToLecturer", it goes to the HeadOfDepPickLecturerToShowStats.fxml file.
	     * If the navigationBack is "headOfDepToStudent", it goes to the HeadOfDepPickStudentToShowStats.fxml file.
	     * If the navigationBack is any other value, it goes to the HeadOfDepPickCourseToShowStats.fxml file.
	     *
	     * @param event ActionEvent that triggers this method.
	     * @throws IOException If there is an error loading the form.
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
			AnchorPane root;
			if(Session.getInstance().getCurrentUser().getRole() == Role.LECTURER)
				root = loader.load(getClass().getResource("/gui/LecturerMainForm.fxml").openStream());
			else
			{
				if(navigationBack.equals("headOfDepToLecturer"))
					root = loader.load(getClass().getResource("/gui/HeadOfDepPickLecturerToShowStats.fxml").openStream());
				else if(navigationBack.equals("headOfDepToStudent"))
					root = loader.load(getClass().getResource("/gui/HeadOfDepPickStudentToShowStats.fxml").openStream());
				else
					root = loader.load(getClass().getResource("/gui/HeadOfDepPickCourseToShowStats.fxml").openStream());
			}
			
			Scene scene = new Scene(root);
			primaryStage1.setScene(scene);
			primaryStage1.show();
		}


}
