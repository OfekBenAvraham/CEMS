package gui_controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import enums.Operations;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import client.ClientController;
import client.ClientUI;
import controllers.RequestObserver;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.ExamsDisplayData;
import entities.Message;
import entities.Request;
import entities.Session;

/**
 * The LecturerSetTimeToAddController class is responsible for managing the view for setting the additional time for an exam.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerSetTimeToAddController implements Initializable {

    /**
     * The reference to the LecturerSetTimeToAddController instance.
     */
    public static LecturerSetTimeToAddController lecturerSetTimeToAddController;
    public static ExamsDisplayData exam;
    /**
     * The ID of the exam to change the time.
     */
    public static String examIdToChangeTime;

    /**
     * The duration of the exam.
     */
    public static int examDuration;
    
	@FXML
	private Button btnRequest;
	@FXML
	private Button btnExit;
	@FXML
	private Spinner<Integer> timeToAddSpr;
	@FXML
	private Label feebackLbl;
	
	@FXML
	private TextArea freeTexttxtArea;
	
	@FXML
	private Label lecturerNameLbl;
	
	/**
     * Initializes the lecturer set time to add view.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerSetTimeToAddController = this;
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360);
		timeToAddSpr.setValueFactory(valueFactory);
	}
	
	 /**
     * Handles the action when the request button is clicked.
     *
     * @param event The action event.
     * @throws Exception if an error occurs while sending the request.
     */
	public void requestTimeChange(ActionEvent event) throws Exception {
	
		String executedExamId = this.examIdToChangeTime;
		feebackLbl.setText("Request Sent to your Head Of Department, You will be updated.");
		String lecturerId = Session.getInstance().getCurrentUser().getUserID().toString();
		String freeText = freeTexttxtArea.getText();
		String lecturerName = Session.getInstance().getCurrentUser().getFullName();
		int duration = examDuration;
		int extraTime = timeToAddSpr.getValue();
		Request request = new Request(executedExamId,freeText,lecturerName,duration,extraTime,lecturerId,exam.getExecuteExamSerialNumber()); 
		Message msg = new Message(Operations.AddRequestToDB, request);
		ClientUI.client.accept(msg);
		
		ArrayList<String> requestDetails = new ArrayList<>();
		requestDetails.add(executedExamId);
		requestDetails.add(String.valueOf(timeToAddSpr.getValue()));
		
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsInProgress.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	 /**
     * Sets the ID of the exam to change the time.
     *
     * @param examId The exam ID.
     */
	@SuppressWarnings("static-access")
	public void setExamIdToChange(String examId)
	{
		this.examIdToChangeTime = examId;
	}
	
	 /**
     * Sets the duration of the exam.
     *
     * @param duration The duration in minutes.
     */
	@SuppressWarnings("static-access")
	public void setDuration(int duration)
	{
		this.examDuration = duration;
	}
	
	 /**
     * Handles the action when the exit button is clicked.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while closing the window.
     */
	@FXML
	void getExit(ActionEvent event) throws IOException { 
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
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamsInProgress.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	


}
