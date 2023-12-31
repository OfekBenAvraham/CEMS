package gui_controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientUI;
import entities.Exam;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
import entities.Session;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The LecturerShowExamAfterCheckController class is responsible for managing the view displaying the exam after it has been checked by a lecturer.
 * @author Almog Elbaz
 * @author Guy Pariente
 */
public class LecturerShowExamAfterCheckController implements Initializable {

    /**
     * The reference to the LecturerShowExamAfterCheckController instance.
     */
    public static LecturerShowExamAfterCheckController lecturerShowExamAfterCheckController;

    /**
     * The student's answers for the exam.
     */
    public static ArrayList<String> studentAnswers;

    /**
     * The exam being displayed.
     */
    public static Exam exam;

    /**
     * The list of questions in the exam.
     */
    public static ArrayList<QuestionInExam> questionsInExam;

    /**
     * The ID of the student.
     */
    public static String studentId;
	
	@FXML
	private TableView<QuestionInExam> questionsTbl;
	@FXML
	private TableView<String> studentAnswerTbl;
	@FXML
	private TableColumn<String,String> studentAnswerCol;
	@FXML
	private TableColumn<QuestionInExam, String> questionIdCol;
	@FXML
	private TableColumn<QuestionInExam, String> questionTextCol;
	@FXML
	private TableColumn<QuestionInExam, String> optionOneCol;
	@FXML
	private TableColumn<QuestionInExam, String> optionTwoCol;
	@FXML
	private TableColumn<QuestionInExam, String> optionThreeCol;
	@FXML
	private TableColumn<QuestionInExam, String> optionFourCol;
	@FXML
	private TableColumn<QuestionInExam, String> correctAnsCol;
	@FXML
	private TableColumn<QuestionInExam,String> studentAnsCol;
	@FXML
	private TableColumn<QuestionInExam,Double> questionPtsCol;
	@FXML
	private Label studentIdLabel;
	@FXML
	private Button exitBtn;
	@FXML
	private Label lecturerNameLbl;
	
	
	 /**
     * Initializes the lecturer show exam after check view.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(studentId == null)
			return;
		
		studentIdLabel.setText(studentId); 
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if (questionsInExam == null) 
			return;
		if(studentAnswers == null)
			return;
		
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		
        ArrayList<String> individualAnswers = new ArrayList<String>();
        for (String answer : studentAnswers.get(0).split(",")) {
            individualAnswers.add(answer);
        }
        
		questionIdCol.setCellValueFactory(new PropertyValueFactory<>("examId"));
		questionTextCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
		optionOneCol.setCellValueFactory(new PropertyValueFactory<>("option1"));
		optionTwoCol.setCellValueFactory(new PropertyValueFactory<>("option2"));
		optionThreeCol.setCellValueFactory(new PropertyValueFactory<>("option3"));
		optionFourCol.setCellValueFactory(new PropertyValueFactory<>("option4"));
		correctAnsCol.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
		questionPtsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
		ObservableList<QuestionInExam> data = FXCollections.observableArrayList(questionsInExam);
		questionsTbl.setItems(data);
		
        studentAnswerCol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue());
        });
        ObservableList<String> answerData = FXCollections.observableArrayList(individualAnswers);
        studentAnswerTbl.setItems(answerData);
    }
	/**
     * Handles the action when the exit button is clicked.
     *
     * @param event The action event.
     * @throws IOException if an I/O error occurs while closing the window.
     */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		questionsInExam.clear();
		((Node) event.getSource()).getScene().getWindow().hide();
	}
}
