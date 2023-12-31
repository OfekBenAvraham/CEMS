package gui_controllers;

import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import client.ClientController;
import client.ClientUI;
import controllers.RequestObserver;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.ExamsDisplayData;
import entities.Message;
import entities.Request;
import entities.Session;

public class PendingTimeChangeRequestsController implements Initializable {
	/**
	 * The reference to the PendingTimeChangeRequestsController instance.
	 */
	public static PendingTimeChangeRequestsController pendingTimeChangeRequestController;
	
	/**
     * The list of pending time change requests.
     */
	public static ArrayList<Request> requests;
	
	/**
     * The field code associated with the pending time change requests.
     */
	public static String fieldCode;
	
	@FXML
	public TableView<Request> pendingTbl;

	@FXML
	private TableColumn<Request, String> requestIdCol;
	
	@FXML
	private TableColumn<Request, String> examIdCol;


	@FXML
	private TableColumn<Request, String> textCol;
	@FXML
	private TableColumn<Request, String> examLecturerCol;
	
	@FXML
	private TableColumn<Request, String> examLecturerId;

	@FXML
	private TableColumn<Request, Integer> examDurationCol;

	@FXML
	private TableColumn<Request, Integer> examExtraTimeCol;
	
	@FXML
	private TableColumn<Request,String> examStatusCol;
	
	@FXML
	private Label headOfDepNameLbl;
	
	@FXML
	private Button approveBtn;
	@FXML
	private Button rejectBtn;

	@FXML
	private Button backBtn;
	@FXML
	private Label answerLbl;


	ObservableList<Request> data;
	ClientController client;
	ScheduledExecutorService executorService;
	
	/**
	 * Initializes the pending time change requests view.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pendingTimeChangeRequestController = this;
		headOfDepNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		requestIdCol.setCellValueFactory(new PropertyValueFactory<Request, String>("RequestID"));
		examIdCol.setCellValueFactory(new PropertyValueFactory<Request, String>("examId"));
		examLecturerCol.setCellValueFactory(new PropertyValueFactory<Request, String>("lecturerName"));
		examLecturerId.setCellValueFactory(new PropertyValueFactory<Request, String>("lecturerId"));
		examDurationCol.setCellValueFactory(new PropertyValueFactory<Request, Integer>("duration"));
		textCol.setCellValueFactory(new PropertyValueFactory<Request, String>("freeText"));
		examExtraTimeCol.setCellValueFactory(new PropertyValueFactory<Request, Integer>("extraTime"));
		examStatusCol.setCellValueFactory(cellData -> {
		    if (cellData.getValue().getStatus() != null)
		        return new SimpleStringProperty(cellData.getValue().getStatus().toString());
		    else
		        return new SimpleStringProperty("");
		}); 
		if(requests == null)
			return;
		data = FXCollections.observableArrayList(requests);
		pendingTbl.setItems(data);
		executorService = Executors.newSingleThreadScheduledExecutor();

		executorService.scheduleAtFixedRate(() -> {
		    Platform.runLater(() -> {
		    	loadTable();
		    });
		}, 0, 5, TimeUnit.SECONDS);
		
	}
	
	/**
	 * Stops the scheduled executor service.
	 */
	public void stop() {
	    executorService.shutdown();
	    try {
	        if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
	            executorService.shutdownNow();
	        } 
	    } catch (InterruptedException e) {
	        executorService.shutdownNow();
	    }
	}
	
	/**
	 * Adds a request to the list of requests.
	 *
	 * @param req The request to be added.
	 */
	public void addRequest(Request req)
	{
		requests.add(req);
	}

	/**
	 * Loads the table with the pending time change requests.
	 */
	public void loadTable() {
		Message msg = new Message(Operations.HeadOfDepGetRequests);
		ClientUI.client.accept(msg);
		data = FXCollections.observableArrayList(requests);
		pendingTbl.setItems(data);
	}

	/**
	 * Approves the selected time change request.
	 *
	 * @param event The action event.
	 * @throws Exception if an error occurs during the approval process.
	 */
	public void approveRequest(ActionEvent event) throws Exception {
	    Request selectedRequest = pendingTbl.getSelectionModel().getSelectedItem();
	    if (selectedRequest != null) 
	    {
	        String examId = selectedRequest.getExamId();
	        int extraTime = selectedRequest.getExtraTime();
	        String lecturerId = selectedRequest.getLecturerId();
	        String requestId = String.valueOf(selectedRequest.getRequestID());
	        ArrayList<String> info = new ArrayList<String>(3);
	        info.add(examId);
	        info.add(String.valueOf(extraTime));
	        info.add(lecturerId); 
	        info.add(requestId);
	        Message msg = new Message(Operations.HeadOfDepAcceptTimeChangeRequest, info);
	        ClientUI.client.accept(msg);
	        answerLbl.setText("Time Request Approved");
	        loadTable();

	    } 
	    else {
	       answerLbl.setText("Please select request.");
	       return;
	    }
	}

	/**
	 * Rejects the selected time change request.
	 *
	 * @param event The action event.
	 * @throws Exception if an error occurs during the rejection process.
	 */
	public void rejectRequest(ActionEvent event) throws Exception {
		
	    Request selectedRequest = pendingTbl.getSelectionModel().getSelectedItem();
	    if (selectedRequest != null) {
	    	ArrayList<String> info = new ArrayList<String>(2);
	        String examId = selectedRequest.getExamId();
	        String lecturerId = selectedRequest.getLecturerId();
	        String requestId = String.valueOf(selectedRequest.getRequestID());
	        info.add(examId);
	        info.add(lecturerId);
	        info.add(requestId);
	        Message msg = new Message(Operations.HeadOfDepRejectTimeChangeRequest, info);
	        ClientUI.client.accept(msg);
	        answerLbl.setText("Time Request Rejected");
	        loadTable();

	    } else {
	       answerLbl.setText("Please select request.");
	       return;
	    }

	}

	/**
	 * Navigates back to the previous page.
	 *
	 * @param event The action event.
	 * @throws IOException if an error occurs during the navigation process.
	 */
	@FXML
	void getBackBtn(ActionEvent event) throws IOException {
		stop();
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
