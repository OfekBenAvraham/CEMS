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

/**
 * The LecturerShowExamsInProgressController class is responsible for managing the view displaying exams in progress.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerShowExamsInProgressController implements Initializable {

    /**
     * The reference to the LecturerShowExamsInProgressController instance.
     */
    public static LecturerShowExamsInProgressController lecturerShowExamsInProgressController;

    /**
     * The list of exams in progress.
     */
    public static ArrayList<ExamsDisplayData> examsInProgress;

    /**
     * The list of all exams.
     */
    private static ArrayList<ExamsDisplayData> exams;
    
	@FXML
	private TableView<ExamsDisplayData> examsInProgTbl; 

	@FXML
	private TableColumn<ExamsDisplayData, String> executionCodeCol;

	@FXML
	private TableColumn<ExamsDisplayData, String> courseNameCol;

	@FXML
	private TableColumn<ExamsDisplayData, Timestamp> dateCol;

	@FXML
	private TableColumn<ExamsDisplayData, Integer> durationCol;
	
	@FXML
	private TableColumn<ExamsDisplayData, String> examIdCol;

	@FXML
	private Button lockExamBtn;
	@FXML
	private Button reqTimeAddBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Label feedbackLbl;
	@FXML
	private Label lecturerNameLbl;
	
	
	 /**
     * Initializes the lecturer show exams in progress view.
     *
     * @param location  The URL location of the FXML file.
     * @param resources The ResourceBundle resources.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		lecturerShowExamsInProgressController = this;
		loadTable();
	}
	
	/**
     * Loads the exams in progress table.
     */
	private void loadTable() {
		String lectId = Session.getInstance().getCurrentUser().getUserID().toString();
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		Message msg = new Message(Operations.LecturerGetActiveExams,lectId);
		ClientUI.client.accept(msg); 
		if(examsInProgress == null)
			return;
		exams = new ArrayList<ExamsDisplayData>();
		for (ExamsDisplayData ex : examsInProgress) {
			exams.add(ex.getExamDisplayObj());
		}
		executionCodeCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("examCode"));
		examIdCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("examId"));
		courseNameCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, String>("courseName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, Timestamp>("date"));
		durationCol.setCellValueFactory(new PropertyValueFactory<ExamsDisplayData, Integer>("duration"));
		ObservableList<ExamsDisplayData> data = FXCollections.observableArrayList(exams);
	    examsInProgTbl.setItems(data);
	}
	
    /**
     * Locks the selected exam.
     *
     * @param event The action event.
     * @throws Exception if an error occurs while locking the exam.
     */
	@FXML
	public void lockExam(ActionEvent event) throws Exception
	{
		ExamsDisplayData selectedExam =  (ExamsDisplayData)examsInProgTbl.getSelectionModel().getSelectedItem();
		if(selectedExam == null)
		{
			feedbackLbl.setText("No Exam Chosen");
			return;
		}
		ArrayList<String> data = new ArrayList<String>(2);
		data.add(selectedExam.getExamCode());
		data.add(selectedExam.getExamId());
		data.add(selectedExam.getExecuteExamSerialNumber());
		Message msg = new Message(Operations.lecturerLockExam, data);
		ClientUI.client.accept(msg);
		String message = "Exam " + selectedExam.getExamId().toString() + " Is Locked";
		feedbackLbl.setText(message);
		loadTable();
	}
	
	 /**
     * Requests a time change for the selected exam.
     *
     * @param event The action event.
     * @throws Exception if an error occurs while requesting a time change.
     */
	@FXML
	public void requestTimeChangeForExam(ActionEvent event) throws Exception
	{
		ExamsDisplayData selectedExam =  (ExamsDisplayData)examsInProgTbl.getSelectionModel().getSelectedItem();
		if(selectedExam == null)
		{
			feedbackLbl.setText("No Exam Chosen");
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerSetTimeToAdd.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
		LecturerSetTimeToAddController.exam = selectedExam;
		LecturerSetTimeToAddController.lecturerSetTimeToAddController.setExamIdToChange(selectedExam.getExamId().toString()); // sets the examId that we will use in the next page.
		LecturerSetTimeToAddController.lecturerSetTimeToAddController.setDuration(selectedExam.getDuration());
	}
	
	 /**
     * Handles the action when the back button is clicked.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while loading the view.
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerMainForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
