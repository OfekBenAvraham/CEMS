package gui_controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.Timestamp;
import java.util.ArrayList;

import client.ClientUI;
import entities.Course;
import entities.ExamsDisplayData;
import entities.Message;
import entities.Session;
import enums.Operations;


/**
 * The LecturerShowExamByLecturerIdController class is responsible for managing the view displaying exams for a lecturer.
 * @author Almog Elbaz
 * @author Guy Pariente 
 */
public class LecturerShowExamByLecturerIdController {

    /**
     * The reference to the LecturerShowExamByLecturerIdController instance.
     */
    public static LecturerShowExamByLecturerIdController lecturerShowExamByLecturerIdController;

    /**
     * The list of exams.
     */
    public static ArrayList<ExamsDisplayData> exams;

    /**
     * The additional information for the exams.
     */
    public static ArrayList<String> info;

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
    * Initializes the lecturer show exams view.
    */
    @FXML
    public void initialize() {
    	lecturerShowExamByLecturerIdController = this;
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
     * Shows the exams to validate for the selected exam.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while loading the view.
     */
	@FXML
	void showExamsToValidate(ActionEvent event) throws IOException {
		ExamsDisplayData selectedExam = examsTbl.getSelectionModel().getSelectedItem();
	    if (selectedExam != null) 
	    {
	    	String lecturerId = Session.getInstance().getCurrentUser().getUserID().toString();
	    	String examId = selectedExam.getExamId();
	    	String executionCode = selectedExam.getExamCode();
	    	String date = selectedExam.getDate().toString();
	    	String courseName = selectedExam.getCourseName();
	    	ArrayList<String> toNextController = new ArrayList<String>();
	    	toNextController.add(courseName);
	    	toNextController.add(date);
	    	toNextController.add(selectedExam.getExecuteExamSerialNumber());
	    	LecturerShowExamsNotValidatedController.dataToShow = toNextController;
	    	LecturerShowExamsNotValidatedController.examDetails = selectedExam;
	    	LecturerValidateExamFormController.exam = selectedExam;
	        // Get the examId and duration
	        // add them to the arrayList
	        ArrayList<String> info = new ArrayList<String>();
	        info.add(lecturerId);
	        info.add(examId);
	        info.add(executionCode); 
	        info.add(date);
	        info.add(courseName);
	        info.add(selectedExam.getExecuteExamSerialNumber());
	        LecturerShowExamsNotValidatedController.dataToPass = info;
	        Message msg = new Message(Operations.LecturerGetStudentsIdToValidate, info);
	        ClientUI.client.accept(msg);
	    } 
	    else {
	    	errorLbl.setText("Please Select Exam.");
	    	return;
	    }
		
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsNotValidatedByCourse.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	
	 /**
     * Handles the action when the exit button is clicked.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while loading the view.
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerPickCourseToValidateExams.fxml").openStream());
		
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
}
