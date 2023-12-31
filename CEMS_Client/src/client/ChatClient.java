// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;

import common.ChatIF;
import controllers.Login;
import entities.Course;
import entities.CourseDoneByStudent;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
import entities.ExamsDisplayData;
import entities.Exam;
import entities.ExecutedExam;
import entities.Request;
import entities.Field;
import entities.StudentInComputerizedExam;
import entities.HeadOfDepartment;
import entities.Lecturer;
import entities.User;
import entities.Session;
import entities.ExamStatistics;
import entities.Student;
import enums.RequestStatus;
import gui_controllers.ConnectFormController;
import gui_controllers.HeadOfDepPickCourseToShowStatsController;
import gui_controllers.HeadOfDepPickLecturerToShowStatsController;
import gui_controllers.HeadOfDepPickStudentToShowStatsController;
import gui_controllers.HeadOfDepartmentViewUsersController;
import gui_controllers.LecturerAddExamToRepositoryFormController;
import gui_controllers.LecturerExamRepositoryFormController;
import gui_controllers.LecturerExamRepositoryToActivateFormController;
import gui_controllers.LecturerMainFormController;
import gui_controllers.LecturerMainQuestionRepositoryFormController;
import gui_controllers.LecturerPersonalExamRepositoryFormController;
import gui_controllers.LecturerPersonalQuestionRepositoryFormController;
import gui_controllers.LecturerPickCourseToValidateExamController;
import gui_controllers.LecturerPickExamToSeeStatsController;
import gui_controllers.LecturerQuestionRepositoryFormController;
import gui_controllers.LecturerSearchQuestionRepositoryFormController;
import gui_controllers.LecturerShowExamAfterCheckController;
import gui_controllers.LecturerShowExamByLecturerIdController;
import gui_controllers.LecturerShowExamFormController;
import gui_controllers.LecturerShowExamToActiavteFormController;
import javafx.collections.FXCollections;
import gui_controllers.LecturerShowExamsInProgressController;
import gui_controllers.LecturerShowExamsNotValidatedController;
import gui_controllers.LecturerValidateExamFormController;
import gui_controllers.PendingTimeChangeRequestsController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import ocsf.client.AbstractClient;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	StudentInComputerizedExam studentInComputerizedExam;
	boolean isComputerizedExamLocked;
	boolean checkForAnswers;
	int timeChange;
	ArrayList<String> otherStudentsAnswers = new ArrayList<String>();
	ObservableList<ExamStatistics> statistics = FXCollections.observableArrayList();
	ObservableList<CourseDoneByStudent> gradesList = FXCollections.observableArrayList();
	ExecutedExam exam;
	public static boolean awaitResponse = false;
	int duration;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}


	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.println("--> handleMessageFromServer");
		System.out.println("Message in chat client: " + msg.toString());
		awaitResponse = false;
		if (msg instanceof Message) {
			Message recievedMessage = (Message) msg;
			switch (recievedMessage.getOperation()) 
			{
			// ************** LOGIN ********************
			case LecturerLogin:
				Login.receiveLecturerLogin(recievedMessage);
				break;

			case HeadOfDepartmentLogin:
				Login.receiveHeadOfDepartmentLogin(recievedMessage);
				break;

			case StudentLogin:
				Login.receiveStudentLogin(recievedMessage);
				break;

			case UserLogout:
				Login.receiveUserLogout(recievedMessage);
				break;

//			**********************student***********************
			case GetExamStatisticsStudent: 
				ArrayList<ExamStatistics> incomingStatisticsData = (ArrayList<ExamStatistics>) recievedMessage.getObj();
				this.statistics.clear();
				for (ExamStatistics statisticsData : incomingStatisticsData) {
					this.statistics.add(statisticsData);
				}
			case GetExamStatistics: 
				ArrayList<ExamStatistics> incomingStatisticsDataLect = (ArrayList<ExamStatistics>) recievedMessage.getObj();
				this.statistics.clear();
				for (ExamStatistics statisticsData : incomingStatisticsDataLect) {
					this.statistics.add(statisticsData);
				}
				break;
			case GetDuration: 
				this.duration = (int) recievedMessage.getObj();
				break;
			case StudentViewGrades: 
				ArrayList<CourseDoneByStudent> incomingGradesData = (ArrayList<CourseDoneByStudent>) recievedMessage.getObj();
				this.gradesList.clear();
				for (CourseDoneByStudent gradeData : incomingGradesData) {
					this.gradesList.add(gradeData); // add the request to the ObservableList
				}
				break;
			case StudentGetExam:
				ExecutedExam incomingExexutedExam = (ExecutedExam) recievedMessage.getObj();
				exam = null;
				this.exam = incomingExexutedExam;
				break;
			case StudentStartComputerizedExam: 
				StudentInComputerizedExam s = (StudentInComputerizedExam) recievedMessage.getObj();
				this.studentInComputerizedExam = s;
				break;
			case StudentComputerizedExamLockCheck: 
				this.isComputerizedExamLocked = (boolean) recievedMessage.getObj();
				break;
			case StudentComputerizedExamTimeChangeCheck: 
				this.timeChange = (int) recievedMessage.getObj();
				break;
			case StudentExamCheckIfCheated: 
				this.otherStudentsAnswers = (ArrayList<String>) recievedMessage.getObj();
				break;
			case StudentFinishComputerizedExam:
				break;
			case StudentCheckIfDidTheExam: 
				this.checkForAnswers = (boolean) recievedMessage.getObj();
				break;
			// ************* HEAD OF DEPARTMENT *************
			case HeadOfDepGetRequests:
				ArrayList<Request> incomingRequests = (ArrayList<Request>) recievedMessage.getObj();
				PendingTimeChangeRequestsController.requests = incomingRequests;
				PendingTimeChangeRequestsController.pendingTimeChangeRequestController.pendingTbl.refresh();
				break;
			case HeadOfDepAcceptTimeChangeRequest:
				ArrayList<String> data = (ArrayList<String>) recievedMessage.getObj();
				String lecturerId = data.get(2);
				if (lecturerId.equals(Session.getInstance().getCurrentUser().getUserID())) {
					Platform.runLater(() -> {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Request Approved");
						alert.setHeaderText(null);
						String text = lecturerId + ", Your request for " + data.get(1) + " time addition to exam "
								+ data.get(0) + " has been approved.";
						alert.setContentText(
								text + " Your Email is: " + Session.getInstance().getCurrentUser().getEmail());
						alert.showAndWait();
					});
				}
				break;

			case HeadOfDepRejectTimeChangeRequest:
				ArrayList<String> dataReq = (ArrayList<String>) recievedMessage.getObj();
				String lecturerIdForReject = dataReq.get(1);
				if (lecturerIdForReject.equals(Session.getInstance().getCurrentUser().getUserID())) {
					Platform.runLater(() -> {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Request Rejected");
						alert.setHeaderText(null);
						String text = lecturerIdForReject + ", Your request for time addition to exam " + dataReq.get(0)
								+ " has been rejected.";
						alert.setContentText(
								text + " Your Email is: " + Session.getInstance().getCurrentUser().getEmail());
						alert.showAndWait();
					});
				}
				break;
			case GetLecturers:
				HeadOfDepPickLecturerToShowStatsController.lecturers = (ArrayList<Lecturer>) recievedMessage.getObj();
				break;
			case GetCourses:
				HeadOfDepPickCourseToShowStatsController.courses = (ArrayList<Course>) recievedMessage.getObj();
				break;
			case GetStudents:
				HeadOfDepPickStudentToShowStatsController.students = (ArrayList<Student>) recievedMessage.getObj();
				break;
				
			case HeadOfDepGetExamByCourseCode:
				LecturerPickExamToSeeStatsController.exams = (ArrayList<ExamsDisplayData>) recievedMessage.getObj();
				break;
			case HeadOfDepGetExamByStudentId:
				LecturerPickExamToSeeStatsController.exams = (ArrayList<ExamsDisplayData>) recievedMessage.getObj();
				break;
			case HeadOfDepGetUsers:
				HeadOfDepartmentViewUsersController.users = (ArrayList<User>) recievedMessage.getObj();
				break;	
			case NoResponse:
				break;
			// ********** Lecturer *************
			case LecturerGetExamsToShowStats:
				LecturerPickExamToSeeStatsController.exams = (ArrayList<ExamsDisplayData>) recievedMessage.getObj();
				break;
			case LecturerApprovesExamCheck:
				ArrayList<String> dataSent = (ArrayList<String>) recievedMessage.getObj();
				// Alert the student that a new grade has arrived
				String studentIdToSendSMS = dataSent.get(0);
	            if (studentIdToSendSMS.equals(Session.getInstance().getCurrentUser().getUserID())) {
	                Platform.runLater(() -> {
	                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                    alert.setTitle("New Grade Available!");
	                    alert.setHeaderText(null);
	                    String text = studentIdToSendSMS + ", There's a new grade available to see.";
	                    alert.setContentText(text+ " Your Email is: " + Session.getInstance().getCurrentUser().getEmail());
	                    alert.showAndWait();
	                });
	            }
				break;
			case LecturerGetStudentAnswersAfterCheck:
				LecturerShowExamAfterCheckController.studentAnswers = (ArrayList<String>) recievedMessage.getObj();
				break;
			case LecturerGetExamAfterCheck:
				LecturerShowExamAfterCheckController.questionsInExam = (ArrayList<QuestionInExam>) recievedMessage.getObj();
				break;
			case LecturerGetSingleExamToValidate:
				LecturerValidateExamFormController.info = (ArrayList<String>) recievedMessage.getObj();
				break;
			case LecturerGetStudentsIdToValidate:
				LecturerShowExamsNotValidatedController.students = (ArrayList<String>) recievedMessage.getObj();
				break;
			case LecturerGetDoneExamsByCourseChosen:
			LecturerShowExamByLecturerIdController.exams = (ArrayList<ExamsDisplayData>) recievedMessage.getObj();
				break;
			case LecturerGetCoursesToValidateExams: 
				ArrayList<Course> clientCourses = (ArrayList<Course>) recievedMessage.getObj();
				LecturerPickCourseToValidateExamController.courses = clientCourses;
				break;
			case GetFields:
				ArrayList<Field> fieldArr = (ArrayList<Field>) recievedMessage.getObj();
				LecturerMainFormController.fields = fieldArr;
				break;
			case GetCoursesByField:
				ArrayList<Course> courses = (ArrayList<Course>) recievedMessage.getObj();
				LecturerMainFormController.courses = courses;
				break;
			case AllWorks:
				break;
			case GetPersonalQuestionRepository:
				LecturerPersonalQuestionRepositoryFormController.questions = (ArrayList<Question>) recievedMessage
						.getObj();
				break;
			case RepositoryOwner:
				LecturerMainFormController.repositoryOwner = (String)recievedMessage.getObj();
				break;
			case GetQuestionRepository:
				LecturerQuestionRepositoryFormController.questions = (ArrayList<Question>) recievedMessage.getObj();
				break;
			case GetPersonalExamRepository:
				LecturerPersonalExamRepositoryFormController.exams = (ArrayList<Exam>) recievedMessage.getObj();
				break;
			case GetExamRepository:
				LecturerExamRepositoryFormController.exams = (ArrayList<Exam>) recievedMessage.getObj();
				break;
			case GetExamRepositoryToActive:
				LecturerExamRepositoryToActivateFormController.exams = (ArrayList<Exam>) recievedMessage.getObj();
				break;
			case GetQuestionsByCourse:
				ArrayList<QuestionInExam> arrQuestionsInExam = new ArrayList<>();
				for (Question q : (ArrayList<Question>) recievedMessage.getObj()) {
					QuestionInExam temp = new QuestionInExam(q);
					arrQuestionsInExam.add(temp);
				}
				LecturerAddExamToRepositoryFormController.questionsInExam = arrQuestionsInExam;
				break;
			case AddExam:
				LecturerAddExamToRepositoryFormController.examId = (String) recievedMessage.getObj();
				break;
			case ShowQuestionsInExam:
				LecturerShowExamFormController.questionsInExam = (ArrayList<QuestionInExam>) recievedMessage.getObj();
				break;
			case ShowQuestionsInExamToActive:
				LecturerShowExamToActiavteFormController.questionsInExam = (ArrayList<QuestionInExam>) recievedMessage
						.getObj();
				break;
			case DeleteQuestionById:
				LecturerPersonalQuestionRepositoryFormController.isDeleted = (String) recievedMessage.getObj();
				break;
			case LecturerGetActiveExams:
				LecturerShowExamsInProgressController.examsInProgress = (ArrayList<ExamsDisplayData>) recievedMessage.getObj();
				break;
			case lecturerLockExam:
				System.out.println("Updated Exam to Locked.");
				break;
			case AddRequestToDB:
				Request req = (Request) recievedMessage.getObj();
				PendingTimeChangeRequestsController.pendingTimeChangeRequestController.addRequest(req);
				break;
			default:
				System.out.println("Command not found.");
			}
		} else {
			System.out.println("No Message Recieved.");
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					System.out.println("waiting...");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			ConnectFormController.connectedToServer = "Faild";
		}
	}
	
	/**
	 * Retrieves the list of courses done by the student along with the grades.
	 *
	 * @return ObservableList of CourseDoneByStudent objects representing the grades for each course.
	 */
	public ObservableList<CourseDoneByStudent> getGrades() {
		return this.gradesList;
	}
	
	/**
	 * Retrieves the information of the student in the computerized exam.
	 *
	 * @return The StudentInComputerizedExam object representing the student in the computerized exam.
	 */
	public StudentInComputerizedExam getStudentInComputerizedExam() {
		return this.studentInComputerizedExam;
	}
	/**
	 * Checks if the computerized exam is locked.
	 *
	 * @return True if the computerized exam is locked, false otherwise.
	 */
	public boolean getIsComputerizedExamLocked() {
		return this.isComputerizedExamLocked;
	}
	/**
	 * Retrieves the amount of time changed for the student.
	 *
	 * @return The integer representing the amount of time changed.
	 */
	public int getTimeChange() {
		return this.timeChange;
	}
	/**
	 * Retrieves the answers provided by other students in the exam.
	 *
	 * @return ArrayList of Strings representing the answers from other students.
	 */
	public ArrayList<String> getOtherStudentsAnswers(){
		return this.otherStudentsAnswers;
	}
	/**
	 * Retrieves the list of statistics for each exam.
	 *
	 * @return ObservableList of ExamStatistics objects representing the statistics for each exam.
	 */
	public ObservableList<ExamStatistics> getStatistic() {
		return this.statistics;
	}
	/**
	 * Checks if the student's answers have been reviewed.
	 *
	 * @return True if the answers have been reviewed, false otherwise.
	 */
	public boolean getCheckForAnswers() {
		return this.checkForAnswers;
	}
	/**
	 * Retrieves the duration of the exam.
	 *
	 * @return The integer representing the duration of the exam.
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * Retrieves the executed exam based on the code the student typed.
	 *
	 * @return The ExecutedExam object representing the executed exam.
	 */
	public ExecutedExam getExecutedExam() {
		return this.exam;
	}
	
	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
