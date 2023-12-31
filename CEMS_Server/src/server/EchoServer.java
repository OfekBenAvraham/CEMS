package server;
// This file contains material supporting section 3.7 of the textbook:

import java.io.IOException;
import java.util.ArrayList;
import enums.Answers;
import enums.Operations;
import enums.RepositoryOperations;
import control.GetLoginController;
import control.CourseController;
import control.FieldController;
import control.HeadOfDepartmentController;
import control.LecturerController;
import control.StudentController;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import control.mysqlConnection;
import entities.Course;
import entities.Exam;
import entities.ExecutedExam;
import entities.Field;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import gui.ServerPortController;
import javafx.application.Platform;

/**
 * 
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Maayan Avittan
 * @author Guy Pariente
 * @author Almog Elbaz
 * @author Paz Fayer
 */
public class EchoServer extends AbstractServer {
	private ServerPortController serverPortController;
	// Instance variables **********************************************

	/**
	 * The success indication of server-client connection.
	 */
	public boolean connectionSuccessfull = false;
	public boolean connectionToDBSuccessfull = false;
	/**
	 * The server's port number.
	 */
	private int port;

	private String password;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ServerController.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port, String password) {
		super(port);
		this.port = port;
		this.password = password;
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client. There are a few
	 * different cases.
	 *
	 * @param message The message received from the client.
	 * @param client  The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object message, ConnectionToClient client) {

		if (message instanceof Message) {
			Message recievedMsg = (Message) message;
			Message ans;
			switch (recievedMsg.getOperation()) {
			// ******************* Login *********************
			case LecturerLogin: {
				ans = GetLoginController.lecturerLogin(recievedMsg);
				try {
					client.sendToClient(ans);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
				break;
			case HeadOfDepartmentLogin: {
				ans = GetLoginController.headOfDepLogin(recievedMsg);
				try {
					client.sendToClient(ans);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
				break;
			case StudentLogin: {
				ans = GetLoginController.studentLogin(recievedMsg);
				try {
					client.sendToClient(ans);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case UserLogout: {
				ans = GetLoginController.userLogout(recievedMsg);
				try {
					client.sendToClient(ans);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
				break;

			// ******************* Student ************************
			case StudentViewGrades: {
				ans = StudentController.studentViewGrades(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoStudentGradesFound) {
						client.sendToClient("Grades Not Found.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case GetDuration: {
				ans = StudentController.StudentGetDuration(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoSpecificData) {
						client.sendToClient("Please Update The Database.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case GetExamStatisticsStudent: {
				ans = StudentController.StudentGetStatistics(recievedMsg,recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoStatFound) {
						client.sendToClient("Statistics Not Found.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case GetExamStatistics: {
				ans = StudentController.StudentGetStatisticsForLecturer(recievedMsg,recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoStatFound) {
						client.sendToClient("Statistics Not Found.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case StudentGetExam: {
				ans = StudentController.studentGetExam(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoExamFound) {
						client.sendToClient("Exam Not Found.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentStartComputerizedExam: {
				ans = StudentController.studentStartComputerizedExam(recievedMsg, recievedMsg.getObj()); 
				try {
					if (ans.getAnswer() == Answers.NoQuestionsAvailable) {
						client.sendToClient("There Are No Questions Available.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentComputerizedExamLockCheck: {
				ans = StudentController.StudentComputerizedExamLockCheck(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoSpecificData) {
						client.sendToClient("Please Update The Database.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentComputerizedExamTimeChangeCheck: {
				ans = StudentController.StudentComputerizedExamTimeChangeCheck(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoSpecificData) {
						client.sendToClient("Please Update The Database.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentFinishComputerizedExam: {
				ans = StudentController.StudentFinishComputerizedExam(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.StudentAnswersSavedInDB) {
						client.sendToClient("Student's Answers Have Been Saved Successfully\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentExamCheckIfCheated: {
				ans = StudentController.StudentCheckIfCheated(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoStudentDidThisExamYet) {
						client.sendToClient("No Other Student Has Taken This Exam");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case StudentCheckIfDidTheExam: {
				ans = StudentController.StudentCheckIfDidTheExam(recievedMsg, recievedMsg.getObj());
				try {
					if (ans.getAnswer() == Answers.NoSpecificData) {
						client.sendToClient("Please Update The Database.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
//					********************** Head Of Department *********************
			case HeadOfDepGetRequests: {
				ans = HeadOfDepartmentController.getRequestsNotResponded(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.NotRespondedRequestsNotFound) {
						client.sendToClient("Requests Not Found.\n");
					} else {
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case HeadOfDepAcceptTimeChangeRequest: {
				ans = HeadOfDepartmentController.acceptExamTimeChangeRequest(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.updatedApprovedRequestSuccessfully) {
						sendToAllClients(recievedMsg); // sending to all clients that are connected. in the client we
														// will validate which lecturer should be alerted.
					} else {
						client.sendToClient("Error in updating request.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case HeadOfDepRejectTimeChangeRequest: {
				ans = HeadOfDepartmentController.rejectExamTimeChangeRequest(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.updatedRejectedRequestSuccessfully) {
						sendToAllClients(recievedMsg);
					} else {
						client.sendToClient("Error in updating request.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case GetLecturers:
				Message lecturers = HeadOfDepartmentController.getLecturers(recievedMsg);
				try {
					if (lecturers.getObj() == null) {
						lecturers.setOperation(Operations.NoResponse);
						client.sendToClient(lecturers);
					} else
						client.sendToClient(lecturers);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GetStudents:
				Message students = HeadOfDepartmentController.getStudents(recievedMsg);
				try {
					if (students.getObj() == null) {
						students.setOperation(Operations.NoResponse);
						client.sendToClient(students);
					} else
						client.sendToClient(students);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case GetCourses:
				Message coursesToSend = HeadOfDepartmentController.getCourses(recievedMsg);
				try {
					if (coursesToSend.getObj() == null) {
						coursesToSend.setOperation(Operations.NoResponse);
						client.sendToClient(coursesToSend);
					} else
						client.sendToClient(coursesToSend);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case HeadOfDepGetExamByCourseCode:
				Message examsByCourse = HeadOfDepartmentController.getExamsToStatsByCourseCode(recievedMsg);
				try {
					if (examsByCourse.getObj() == null) {
						examsByCourse.setOperation(Operations.NoResponse);
						client.sendToClient(examsByCourse);
					} else
						client.sendToClient(examsByCourse);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case HeadOfDepGetExamByStudentId:
				Message examsByStudentId = HeadOfDepartmentController.getExamsToStatsByStudentId(recievedMsg);
				try {
					if (examsByStudentId.getObj() == null) {
						examsByStudentId.setOperation(Operations.NoResponse);
						client.sendToClient(examsByStudentId);
					} else
						client.sendToClient(examsByStudentId);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case HeadOfDepGetUsers:
				Message users = HeadOfDepartmentController.getUsers(recievedMsg);
				try {
					if (users.getObj() == null) {
						users.setOperation(Operations.NoResponse);
						client.sendToClient(users);
					} else
						client.sendToClient(users);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			// **************** Lecturer Methods ******************

			case LecturerGetExamsToShowStats:
				Message answer = LecturerController.getExamsForStats(recievedMsg);
				try {
					if (answer.getObj() == null) {
						answer.setOperation(Operations.NoResponse);
						client.sendToClient(answer);
					} else
						client.sendToClient(answer);
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case LecturerApprovesExamCheck:
				LecturerController.updateExamFinalGradeAndNotes(recievedMsg);
				sendToAllClients(recievedMsg);
				break;
			case LecturerGetStudentAnswersAfterCheck:
				Message studentAnswers = LecturerController.getStudentAnswersByExamByID(recievedMsg);
				try {
					client.sendToClient(studentAnswers);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case LecturerGetExamAfterCheck:
				ArrayList<String> data = (ArrayList<String>) recievedMsg.getObj();
				String examIdRecieved = data.get(0);
				ArrayList<QuestionInExam> questionsInExam = LecturerController.getQuestionsInExamByID(examIdRecieved);
				try {
					Message toSend = new Message(Operations.LecturerGetExamAfterCheck, questionsInExam);
					client.sendToClient(toSend);
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case LecturerGetSingleExamToValidate:
				ans = LecturerController.getSingleExamToValidate(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.StudentsToValidateFound) {
						client.sendToClient(ans);
					} else {
						{
							ans.setOperation(Operations.NoResponse);
							client.sendToClient(ans);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case LecturerGetStudentsIdToValidate:
				ans = LecturerController.getStudentsIdToValidate(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.StudentsToValidateFound) {
						client.sendToClient(ans);
					} else {
						ans.setOperation(Operations.NoResponse);
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case LecturerGetDoneExamsByCourseChosen:
				ans = LecturerController.getExamsDoneByCourse(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.ExamsToValidateFound) {
						client.sendToClient(ans);
					} else {
						ans.setOperation(Operations.NoResponse);
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case LecturerGetCoursesToValidateExams: {
				ans = LecturerController.getCoursesToValidateExam(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.CoursesByLectuerFound) {
						client.sendToClient(ans);
					} else {
						ans.setOperation(Operations.NoResponse);
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case LecturerGetActiveExams: {
				ans = LecturerController.getExamsInProgress(recievedMsg);
				try {
					if (ans.getAnswer() == Answers.ExamsInProgressFound) {
						client.sendToClient(ans);
					} else {
						ans.setOperation(Operations.NoResponse);
						client.sendToClient(ans);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			
			case LecturerSetTimeToChange: {
				// ans = LecturerController.askHeadOfDepartmentForTimeChange;
			}
				break;
			case AddRequestToDB: {
				boolean res;
				res = LecturerController.AddRequestInDb(recievedMsg);
				try {
					if (res == true) {
						client.sendToClient(recievedMsg);
					} else {
						{
							recievedMsg.setOperation(Operations.NoResponse);
							client.sendToClient(recievedMsg);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;

			case lecturerLockExam: {
				Message resMessage;
				resMessage = LecturerController.LockExam(recievedMsg);
				try {
					if (resMessage.getAnswer() == Answers.UpdatedLockedExamSucessfully)
						client.sendToClient(resMessage);
					else {
						resMessage.setOperation(Operations.NoResponse);
						client.sendToClient(resMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			// **************** Question Methods ******************
			case Port:
				try {
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Disconect:
				try {
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case GetExamRepositoryToActive:
				try {					
					ArrayList<Exam> examRepository = LecturerController.getExamRepositoryToActive((String)recievedMsg.getObj());
					Message reciveMessage = new Message(Operations.GetExamRepositoryToActive, examRepository);
					client.sendToClient(reciveMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case GetFields:
				try {
					ArrayList<Field> fields = FieldController.getAllFields();
					Message reciveMessage = new Message(Operations.GetFields, fields);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GetCoursesByField:
				try {
					ArrayList<Course> courses = FieldController.getCoursesByField(recievedMsg);
					Message reciveMessage = new Message(Operations.GetCoursesByField, courses);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GetQuestionsByCourse:
				try {
					ArrayList<Question> questions = CourseController.getQuestionsByCourse(recievedMsg);
					Message reciveMessage = new Message(Operations.GetQuestionsByCourse, questions);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case AddQuestion:
				try {
					String add = LecturerController.saveNewQuestion(recievedMsg);
					Message reciveMessage = new Message(Operations.AllWorks);
					if (add == null) {
						client.sendToClient("Error occurred at adding question");
					} else {
						client.sendToClient(reciveMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case UpdateQuestion:
				try {
					LecturerController.updateQuestion(recievedMsg);
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GetQuestionRepository:
				try {
					ArrayList<String> operations = (ArrayList<String>) (recievedMsg.getObj());
					ArrayList<Question> questionRepository = LecturerController
							.getQuestionRepository(operations.get(1));
					Message reciveMessage;
					if (RepositoryOperations.Personal.name().equals(operations.get(0))) {
						reciveMessage = new Message(Operations.GetPersonalQuestionRepository, questionRepository);
					} else {
						reciveMessage = new Message(Operations.GetQuestionRepository, questionRepository);
					}
					if (questionRepository == null) {
						client.sendToClient("No repository found with this ID");
					} else {
						client.sendToClient(reciveMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case GetExamRepository:
				try {
					ArrayList<String> operations = (ArrayList<String>) (recievedMsg.getObj());
					ArrayList<Exam> examRepository = LecturerController.getExamRepository(operations.get(1));
					Message reciveMessage;
					if (RepositoryOperations.Personal.name().equals(operations.get(0))) {
						reciveMessage = new Message(Operations.GetPersonalExamRepository, examRepository);
					} else {
						reciveMessage = new Message(Operations.GetExamRepository, examRepository);
					} 
					if (examRepository == null) {
						client.sendToClient("No repository found with this ID");
					} else {
						client.sendToClient(reciveMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case RepositoryOwner:
				try {
					String lecturerId = (String) (recievedMsg.getObj());
					String lecturerFullName = LecturerController.getLecturerNameById(lecturerId);
					Message repo = new Message(Operations.RepositoryOwner, lecturerFullName);
					client.sendToClient(repo);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case AddExam:
				try {
					String examId = LecturerController.saveNewExam(recievedMsg);
					Message reciveMessage = new Message(Operations.AddExam, examId);
					if (examId == null) {
						client.sendToClient("Error occurred at adding exam");
					} else {
						client.sendToClient(reciveMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case ActivateExam:
				try {
					Exam e = (Exam)recievedMsg.getObj();
					LecturerController.setActiveExam(e);
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case AddQuestionInExam:
				try {
					LecturerController.saveQuestionsInExam(recievedMsg);
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case ShowQuestionsInExamToActive:
				try {
					ArrayList<QuestionInExam> questionInExam = LecturerController
							.getQuestionsInExamByID((String) recievedMsg.getObj());
					Message reciveMessage = new Message(Operations.ShowQuestionsInExamToActive, questionInExam);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case ShowQuestionsInExam:
				try {
					ArrayList<QuestionInExam> questionInExam = LecturerController
							.getQuestionsInExamByID((String) recievedMsg.getObj());
					Message reciveMessage = new Message(Operations.ShowQuestionsInExam, questionInExam);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DefineCode:
				try {
					LecturerController.insertExecutedExam((ExecutedExam) recievedMsg.getObj());
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DeleteQuestionById:
				try {
					String examsWithTheQuestion = LecturerController.deleteQuestion(recievedMsg);
					Message reciveMessage;
					if (examsWithTheQuestion == null) {
						reciveMessage = new Message(Operations.AllWorks);
					} else {
						reciveMessage = new Message(Operations.DeleteQuestionById, examsWithTheQuestion);
					}
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case DeleteExamById:
				try {
					LecturerController.deleteExam(recievedMsg);
					Message reciveMessage = new Message(Operations.AllWorks);
					client.sendToClient(reciveMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Invalid command received: " + message + " from " + client);
				break;
			}
		} else

		{
			display("Error - there is no Message object ");
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections. Connect to the DB.
	 */
	protected void serverStarted() {
		display("Server listening for connections on port " + getPort());
		mysqlConnection.connecttoDB(password);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		display("Server has stopped listening for connections.");
	}

	/**
	 * This method tries connect to clients.
	 */
	public void runServer() {
		try {
			listen(); // Start listening for connections
			connectionSuccessfull = true;
		} catch (Exception ex) {
			display("ERROR - Could not listen for clients!");
		}
	}

	/**
	 * This method displays a message into the console.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) {
		System.out.println("> " + message);
	}

	/**
	 * Method invoked whenever a client is connected to the server.
	 * This method refreshes the server port controller, then calls the superclass method to complete
	 * the connection process.
	 * 
	 * @param client the ConnectionToClient object representing the client that has just connected.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		Platform.runLater(() -> ServerPortController.serverPortController.refresh());
		super.clientConnected(client);
	}

	/**
	 * Method invoked whenever a client disconnects from the server.
	 * This method refreshes the server port controller, then calls the superclass method to complete
	 * the disconnection process.
	 * 
	 * @param client the ConnectionToClient object representing the client that has just disconnected.
	 */
	@Override
	public void clientDisconnected(ConnectionToClient client) {
		Platform.runLater(() -> ServerPortController.serverPortController.refresh());
		super.clientDisconnected(client);
	}
}
