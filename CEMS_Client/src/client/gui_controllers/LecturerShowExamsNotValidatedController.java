package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.ExamsDisplayData;
import entities.Message;
import entities.Session;
import entities.User;
import enums.Operations;
import javafx.beans.property.SimpleStringProperty;
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
 * The LecturerShowExamsNotValidatedController class is responsible for managing the view displaying exams not validated by a lecturer.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerShowExamsNotValidatedController implements Initializable {

    /**
     * The reference to the LecturerShowExamsNotValidatedController instance.
     */
    public static LecturerShowExamsNotValidatedController lecturerShowExamsNotValidatedController;

    /**
     * The list of student IDs to be displayed in the table view.
     */
    public static ArrayList<String> students;

    /**
     * The data to be shown in the view.
     */
    public static ArrayList<String> dataToShow;

    /**
     * The exam details associated with the students.
     */
    public static ExamsDisplayData examDetails;

    /**
     * The selected student's ID to be validated.
     */
    public static String studentNumber;

    /**
     * The data to be passed back.
     */
    public static ArrayList<String> dataToPass;

    /**
     * The data to be returned.
     */
    public static ArrayList<String> dataToReturn;
	
    @FXML
    private TableView<String> studentsTbl;

    @FXML
    private TableColumn<String,String> studentIdCol;
    
    @FXML
    private Button validateBtn;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Label errorLbl;
    
    @FXML
    private Label courseNameLbl;
    
    @FXML
    private Label studentsCounterLbl;
    
    @FXML
    private Label dateOfExamLbl;
    
    @FXML
    private Label lecturerNameLbl;
    
    
    /**
     * Initializes the lecturer show exams not validated view.
     *
     * @param location  The URL location of the FXML file.
     * @param resources The ResourceBundle resources.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 lecturerShowExamsNotValidatedController= this;
		 studentIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
		 lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		 if(dataToShow == null)
			 return;
	     if(students == null)
	         return;
		 String courseName = dataToShow.get(0);
		 String date = dataToShow.get(1);
		 courseNameLbl.setText(courseName);
		 dateOfExamLbl.setText(date); 
		 ObservableList<String> data = FXCollections.observableArrayList(students);
		 studentsTbl.setItems(data);
		 int numberOfRows = studentsTbl.getItems().size();
		 String numberOfStudents = String.valueOf(numberOfRows);
		 studentsCounterLbl.setText(numberOfStudents);
		 studentsTbl.refresh();
	}
    
	
    /**
     * Handles the action when the validate button is clicked.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while loading the view.
     */
	@FXML
	void showExamToValidate(ActionEvent event) throws IOException {
		String studentSelected = studentsTbl.getSelectionModel().getSelectedItem();
	    if (studentSelected != null) 
	    {
	    	String lecturerId = Session.getInstance().getCurrentUser().getUserID(); 	
	    	ArrayList<String> info = new ArrayList<>();
	    	info.add(studentSelected); 
	        if(examDetails == null)
	        	return;
	        info.add(examDetails.getExamId());
	        info.add(examDetails.getExamCode());
	        info.add(examDetails.getExecuteExamSerialNumber());
	        LecturerValidateExamFormController.studentId = studentSelected;
	        Message msg = new Message(Operations.LecturerGetSingleExamToValidate,info);
	        ClientUI.client.accept(msg);
	    } 
	    else {
	    	errorLbl.setText("Please Select Student.");
	    	return;
	    }
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerValidateExamForm.fxml").openStream());
		//LecturerValidateExamFormController.info = info;
		LecturerValidateExamFormController.dataToPassBack = dataToPass;
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
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
		
        Message msg = new Message(Operations.LecturerGetDoneExamsByCourseChosen, dataToReturn);
        ClientUI.client.accept(msg);
		
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsByLecturerId.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
    /**
     * Refreshes the table view.
     */
	public void refresh() {
		 ObservableList<String> data = FXCollections.observableArrayList(students);
		 studentsTbl.setItems(data);
		 int numberOfRows = studentsTbl.getItems().size();
		 String numberOfStudents = String.valueOf(numberOfRows);
		 studentsCounterLbl.setText(numberOfStudents);
		 studentsTbl.refresh();
	}
}
