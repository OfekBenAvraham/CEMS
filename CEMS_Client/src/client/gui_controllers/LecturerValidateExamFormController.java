package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Exam;
import entities.ExamsDisplayData;
import entities.Message;
import entities.Session;
import enums.Operations;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The controller class for the LecturerValidateExamForm.fxml file.
 * It handles the validation of exams by lecturers.
  * @author Almog Elbaz
 *  @author Guy Pariente
 */
public class LecturerValidateExamFormController implements Initializable {
	
	/**
	 * The instance of the controller itself.
	 */
	public static LecturerValidateExamFormController lecturerValidateExamFormController;
	
	/**
	 * The details of the exam to be validated.
	 */
	public static ExamsDisplayData exam;
	
	/**
	 * The information needed for labels.
	 */
	public static ArrayList<String> info;
	
	/**
	 * The student ID to be validated.
	 */
	public static String studentId;
	
	/**
	 * The data to be passed back.
	 */
	public static ArrayList<String> dataToPassBack;
	
	@FXML
	private Text textError;

	@FXML
	private Button backBtn;
	
	@FXML
	private Spinner<Integer> newGradeSpinner;
	
	@FXML
	private Label studentIdLbl;
	
	@FXML
	private Label errorLbl;
	
	@FXML
	private Label gradeLbl;
	
	@FXML
	private Label cheatedLbl;
	
	@FXML
	private Button approveBtn;
	
	@FXML
	private Button showExamBtn;
	
	@FXML
	private TextArea commentsTxtArea;
	
	@FXML
	private Label lecturerNameLbl;
	
	@FXML
	private Label feedbackLbl;
	
	
    /**
     * Initializes the lecturer validation form.
     *
     * @param location  The URL location of the FXML file.
     * @param resources The ResourceBundle resources.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerValidateExamFormController = this;
		if(studentId == null)
			return;
		studentIdLbl.setText(studentId);
		if(info == null)
			return;
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		gradeLbl.setText(info.get(0));
		double gradeDouble = Double.parseDouble(info.get(0));
		String cheated = info.get(1);
		if(cheated.toLowerCase().equals("true"))
		{
			cheatedLbl.setText("Suspicion of copying");
		}
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
		newGradeSpinner.setValueFactory(valueFactory);
	}
	
	 /**
     * Handles the action of approving the exam after validation.
     *
     * @param event The ActionEvent triggered by the approve button.
     * @throws IOException If an I/O error occurs.
     */
	@FXML
	void approveExam(ActionEvent event) throws IOException {
		String finalGrade = newGradeSpinner.getValue().toString();
		String notes = commentsTxtArea.getText();
		String examId = exam.getExamId();
		String executedExamSerialNumber = exam.getExecuteExamSerialNumber();
		ArrayList<String> data = new ArrayList<String>();
		data.add(studentId);
		data.add(examId);
		data.add(finalGrade);
		data.add(notes);
		data.add(executedExamSerialNumber);
		Message msg = new Message(Operations.LecturerApprovesExamCheck,data);
		ClientUI.client.accept(msg);
		feedbackLbl.setText("Approved Sucessfully");
		approveBtn.setDisable(true);
	}
	
	/**
     * Shows the exam after validation.
     *
     * @param event The ActionEvent triggered by the show exam button.
     * @throws IOException If an I/O error occurs.
     */
	@FXML
	void ShowExam(ActionEvent event) throws IOException {
		LecturerShowExamAfterCheckController.studentId = studentId;
		ArrayList<String> data = new ArrayList<String>();
		data.add(exam.getExamId());
		Message msgToRecieveQuestions = new Message(Operations.LecturerGetExamAfterCheck,data);
		ClientUI.client.accept(msgToRecieveQuestions);
		data.add(studentId);
		data.add(exam.getExecuteExamSerialNumber());
		Message msgToRecivedExamAnswers = new Message(Operations.LecturerGetStudentAnswersAfterCheck,data);
		ClientUI.client.accept(msgToRecivedExamAnswers);
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage1 = new Stage();
		AnchorPane root = loader.load(getClass().getResource("/gui/LecturerShowExamAfterCheck.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}
	
	/**
     * Handles the action of going back to the previous form.
     *
     * @param event The ActionEvent triggered by the back button.
     * @throws IOException If an I/O error occurs.
     */
	@FXML
	void getBack(ActionEvent event) throws IOException {
        Message msg = new Message(Operations.LecturerGetStudentsIdToValidate, dataToPassBack);
        ClientUI.client.accept(msg);
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
		AnchorPane root = loader
				.load(getClass().getResource("/gui/LecturerShowExamsNotValidatedByCourse.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}




}
