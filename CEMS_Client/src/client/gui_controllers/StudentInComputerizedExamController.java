package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ClientController;
import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.StudentInComputerizedExam;
import entities.User;
import enums.Operations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class is the controller for the StudentInComputerizedExam GUI.
 * It handles the user interactions and manages the display of questions
 * and answers in a computerized exam for a student.
 *
 * Controller class for the student doing exam.
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 *
 */
 
public class StudentInComputerizedExamController extends ControllerInterface implements Initializable {

	public static StudentInComputerizedExamController studentInComputerizedExamController;
	public static Student student;
	private StudentInComputerizedExam studentInComputerizedExam;
	private int currentQuestionIndex = 0;
	private int timeRemaining;
	private ArrayList<String> studentAnswersList = new ArrayList<String>();
	private Thread timerThread;
	private Thread statusThread;
	private ToggleGroup answers = new ToggleGroup();
	private boolean isExamLocked = false;
	private String studentAnswer; 
	private boolean flag = true;
	int durationChange = 0;
	private int HowLongTheExamLast;
	ClientController client;
	
	@FXML
	private Label lblDescriptionForExaminee;
	@FXML
	private Label lblFirstName;
	@FXML
	private Button exit;
	@FXML
	private Label lblQuestion;
	@FXML
	private Button btnNext; 
	@FXML
	private Button btnPrevious;
	@FXML
	private Label timerLabel;
	@FXML
	private TextArea txtQuestion;
	@FXML
	private Label lblQuestionNum;
	@FXML
	private RadioButton option1;
	@FXML
	private RadioButton option2;
	@FXML
	private RadioButton option3;
	@FXML
	private RadioButton option4;
	@FXML
	private Label errorLabel;

    /**
     * Initializes the controller and sets up the initial state of the GUI.
     * 
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		studentInComputerizedExamController = this;
		Student student = (Student) Session.getInstance().getCurrentUser();
		if (student != null) {
			StudentMainOptionPageController.student = student;
			this.lblFirstName.setText(student.getFullName());
		String code = StudentTypeCodeForExamController.code;
		Message msg = new Message(Operations.StudentStartComputerizedExam, code);
		ClientUI.client.accept(msg);
		StudentInComputerizedExam response = ClientUI.client.getStudentInComputerizedExam();
		if (response != null) {
			studentInComputerizedExam = response;
			studentInComputerizedExam.setStudentInComputerizedExam(StudentMainOptionPageController.student); 
			lblDescriptionForExaminee.setText(studentInComputerizedExam.getDescriptionForExaminee());
			startTimer(studentInComputerizedExam.getDuration()*60);
			displayQuestion(currentQuestionIndex);
		}
		checkExamStatus();
		}
	}
	
    /**
     * Handles the action when the "Next" button is clicked.
     * Saves the answer chosen by the student and proceeds to the next question.
     * 
     * @param event The event representing the button click.
     * @throws IOException if an I/O error occurs when loading the new window.
     */
	@FXML
	void clickNext(ActionEvent event) throws IOException {
	    RadioButton selectedAnswer = (RadioButton) answers.getSelectedToggle();
	    if (selectedAnswer != null) {
	        studentAnswer = selectedAnswer.getId();
	        if (studentAnswersList.size() <= currentQuestionIndex) {
	            studentAnswersList.add(studentAnswer);
	        } else {
	            studentAnswersList.set(currentQuestionIndex, studentAnswer);
	        }
	        System.out.println(studentAnswersList);
	        currentQuestionIndex++;
	        clearRadioButtons();
	        if (currentQuestionIndex < studentInComputerizedExam.getQuestionInExecutedExam().size()) {
	            displayQuestion(currentQuestionIndex);
	        } else {
	            openNewWindow();
	        }
	    } else {
	        errorLabel.setVisible(true);
	    }
	}
	
    /**
     * Handles the action when the "Previous" button is clicked.
     * Displays the previous question.
     * 
     * @param event The event representing the button click.
     * @throws IOException if an I/O error occurs when loading the new window.
     */
	@FXML
	void clickPrevious(ActionEvent event) throws IOException {
		if (currentQuestionIndex > 0) {
			currentQuestionIndex--;
			clearRadioButtons();
			displayQuestion(currentQuestionIndex);
		}
	}
	
	private void clearRadioButtons() {
	    answers.selectToggle(null);
	}

	  /**
     * Starts the timer for the exam.
     * 
     * @param duration The duration of the exam in seconds.
     */
	public void startTimer(int duration) {
		timeRemaining = duration; 
		timerThread = new Thread(() -> {
			while (timeRemaining > 0 && !isExamLocked) {
				Platform.runLater(() -> {
					timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeRemaining--;
			}

			if (isExamLocked) {
				Platform.runLater(() -> {
					Stage currentStage = (Stage) timerLabel.getScene().getWindow();
					currentStage.close();					// Open a new window
					openNewWindow();
				});
			} else {
				Platform.runLater(() -> {
					Stage currentStage = (Stage) timerLabel.getScene().getWindow();
					currentStage.close();
					openNewWindow();
				});
			}
		});

		timerThread.start();
	}

	 /**
     * Opens a new window after the exam is finished or the time is up.
     * Calculates the time taken by the student to solve the exam,
     * performs automatic check, checks for cheating, and sends the
     * student's answers to the database.
     */
	@SuppressWarnings("deprecation")
	private void openNewWindow() {
        timerThread.stop();; 
        statusThread.stop(); 
        studentInComputerizedExam.setAnswers(studentAnswersList);

        HowLongTheExamLast = (int) Math.round(((studentInComputerizedExam.getDuration()*60) + durationChange - timeRemaining) / 60.0);

        studentInComputerizedExam.setHowLongTheExamLast(HowLongTheExamLast);
        
        studentInComputerizedExam.CheckStudentExam();
        
		Message msgForCheatCheck = new Message(Operations.StudentExamCheckIfCheated, studentInComputerizedExam);
		ClientUI.client.accept(msgForCheatCheck);
		ArrayList<String> response = ClientUI.client.getOtherStudentsAnswers();
        studentInComputerizedExam.CheckForCheating(response); 
        
        Message msg = new Message(Operations.StudentFinishComputerizedExam,  studentInComputerizedExam);;
        ClientUI.client.accept(msg); 
        
		try {
			Stage primaryStage = (Stage) timerLabel.getScene().getWindow();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
			primaryStage.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/StudentExamIsOver.fxml"));
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Exam Over");
			newStage.setScene(new Scene(root));
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the question at the given index in the GUI.
	 * 
	 * @param currentQuestionIndex The index of the question to display.
	 */
	public void displayQuestion(int currentQuestionIndex) {
		int showQuestionNum = currentQuestionIndex + 1;
		lblQuestionNum.setText("" + showQuestionNum);
		errorLabel.setVisible(false); 

		String questionText = studentInComputerizedExam.getQuestionInExecutedExam().get(currentQuestionIndex)
				.getQuestion().getQuestionText();
		if (questionText != null) {
			txtQuestion.setText(questionText);
			txtQuestion.setEditable(false); // can't be edited
		}

		option1.setToggleGroup(answers);
		String option1Text = studentInComputerizedExam.getQuestionInExecutedExam().get(currentQuestionIndex)
				.getQuestion().getOption1();
		if (option1Text != null) {
			option1.setText(option1Text);
		}

		option2.setToggleGroup(answers);
		String option2Text = studentInComputerizedExam.getQuestionInExecutedExam().get(currentQuestionIndex)
				.getQuestion().getOption2();
		if (option2Text != null) {
			option2.setText(option2Text);
		}

		option3.setToggleGroup(answers);
		String option3Text = studentInComputerizedExam.getQuestionInExecutedExam().get(currentQuestionIndex)
				.getQuestion().getOption3();
		if (option3Text != null) {
			option3.setText(option3Text);
		}

		option4.setToggleGroup(answers);
		String option4Text = studentInComputerizedExam.getQuestionInExecutedExam().get(currentQuestionIndex)
				.getQuestion().getOption4();
		if (option4Text != null) {
			option4.setText(option4Text);
		}
	}

	/**
	 * Checks the status of the exam (whether it is locked or the duration has changed) periodically.
	 * If the exam is locked, it closes the current window and opens a new window.
	 */
	private void checkExamStatus() {
		statusThread = new Thread(() -> {

			while (true) {
				isExamLocked = checkIfExamIsLocked();
				int durationChange = getDurationChange();

				if (isExamLocked) {
					Platform.runLater(() -> {
						Stage currentStage = (Stage) timerLabel.getScene().getWindow();
						currentStage.close();
						openNewWindow();
					});
					break;
				}

				int currentDuration = timeRemaining + durationChange;

				if (currentDuration != timeRemaining) {
					if (flag == true) {
						timeRemaining = currentDuration;
						flag = false;
					}
				}

				try {
					Thread.sleep(5000); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		statusThread.start();
	}

	/**
	 * Checks if the exam is locked by sending a request to the database.
	 * 
	 * @return true if the exam is locked, false otherwise.
	 */
	private boolean checkIfExamIsLocked() {
		String code = StudentTypeCodeForExamController.code;
		Message msg = new Message(Operations.StudentComputerizedExamLockCheck, code);
		ClientUI.client.accept(msg);
		isExamLocked = ClientUI.client.getIsComputerizedExamLocked();
		return isExamLocked;
	}

	/**
	 * Retrieves the duration change of the exam from the database.
	 * 
	 * @return The duration change in seconds.
	 */
	private int getDurationChange() {
		String code = StudentTypeCodeForExamController.code;
		Message msg = new Message(Operations.StudentComputerizedExamTimeChangeCheck, code);
		ClientUI.client.accept(msg);
		durationChange = ClientUI.client.getTimeChange();
		durationChange = durationChange * 60;
		return durationChange;
	}
	
	
	/**
	 * Handles the functionality of the "Exit" button.
	 * Goes back to the last page (studentMainOptionPage).
	 * 
	 * @param event The event representing the button click.
	 * @throws IOException if an I/O error occurs when loading the new window.
	 */
	@FXML
	void ExitFromExamButton(ActionEvent event) throws IOException {
		RadioButton selectedAnswer = (RadioButton) answers.getSelectedToggle();
	    if (selectedAnswer != null) {
	        studentAnswer = selectedAnswer.getId();
	        if (studentAnswersList.size() <= currentQuestionIndex) {
	            studentAnswersList.add(studentAnswer);
	        } else {
	            studentAnswersList.set(currentQuestionIndex, studentAnswer);
	        }
	    }
		int i;
		for(i = studentAnswersList.size(); i < studentInComputerizedExam.getQuestionInExecutedExam().size(); i++) {
			studentAnswersList.add("option0");
		}
		openNewWindow();		
	}
	
	/**
	 * Sets the current user as the student for the controller.
	 * 
	 * @param user The current user.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void setUser(User user) {
		studentInComputerizedExamController.student = (Student) user;
		this.lblFirstName.setText(student.getFullName());

	}

}
