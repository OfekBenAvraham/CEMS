package controllers;

import java.util.ArrayList;

import entities.Message;

import entities.Student;
import enums.Answers;
import enums.Operations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import gui_controllers.LoginPageController;
import entities.HeadOfDepartment;
import entities.Lecturer;

/**
 * The Login class is a controller that handles the process of logging in.
 * It provides methods to handle different types of user login such as Lecturer, Head of Department, and Student.
 */
public class Login extends AbstractController {

	private static ActionEvent event;
	public static LoginPageController loginPageController;

	
	/**
	 * Handles user login requests.
	 *
	 * @param username the user's username.
	 * @param password the user's password.
	 * @param loginBtn the login button event.
	 * @param userType the type of the user, can be "Lecturer", "Head Of Department", or "Student".
	 */
	public static void userRequestLogin(String username, String password, ActionEvent loginBtn, String userType) {

		event = loginBtn;
		Message sendMessage;
		ArrayList<String> data = new ArrayList<String>();
		data.add(username);
		data.add(password);

		switch (userType) {
		case "Lecturer":
			sendMessage = new Message(Operations.LecturerLogin, data);
			SendToServer(sendMessage);
			break;
		case "Head Of Department":
			Message sendMessage1 = new Message(Operations.HeadOfDepartmentLogin, data);
			SendToServer(sendMessage1);
			break;
		case "Student":
			sendMessage = new Message(Operations.StudentLogin, data);
			SendToServer(sendMessage);
			break;
		default:
			break;
		}
	}

	
	/**
	 * Handles the response from the server for Lecturer login.
	 *
	 * @param msg the Message received from the server.
	 */
	public static void receiveLecturerLogin(Message msg) {
		Message recivedMessage = (Message) msg;

		switch (msg.getAnswer()) {
		// ************ Lecturer Login ****************
		case OfflineLecturer:// can login
			Lecturer lecturer = (Lecturer) recivedMessage.getObj();
			LoginPageController.loginPageController.setLecturerLogin(lecturer, event);
			break;
		case IncorrectLecturer:// there is not user
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController.setErrorLabel("Incorrect username or password.");
				}
			});

			break;
		case OnlineLecturer:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController
							.setErrorLabel("The user is already logged in to the system.");
				}
			});
			break;
		default:
			break;

		}
	}

	
	/**
	 * Handles the response from the server for Head of Department login.
	 *
	 * @param msg the Message received from the server.
	 */
	public static void receiveHeadOfDepartmentLogin(Message msg) {
		Message recivedMessage = (Message) msg;
		switch (msg.getAnswer()) {
		// ************ Head Of Department Login ****************
		case OfflineHeadOfDepartment: {
			HeadOfDepartment headOfDep = (HeadOfDepartment) recivedMessage.getObj();
			LoginPageController.loginPageController.setHeadOfDepartmentLogin(headOfDep, event);
			break;
		}
		case IncorrectHeadOfDepartment:
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController.setErrorLabel("Incorrect username or password.");
				}
			});
		}
		break;
		case OnlineHeadOfDepartment: {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController.setErrorLabel("The user is already logged in to the system.");
				}
			});
		}
		break;
		default:
			break;

		}
	}

	
	/**
	 * Handles the response from the server for Student login.
	 *
	 * @param msg the Message received from the server.
	 */
	public static void receiveStudentLogin(Message msg) {
		Message recivedMessage = (Message) msg;
		switch (msg.getAnswer()) {
		// ************ Student Login ****************
		case OfflineStudent: {
			Student student = (Student) recivedMessage.getObj();
			LoginPageController.loginPageController.setStudentLogin(student, event);
			break;
		}
		case IncorrectStudent: {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController.setErrorLabel("Incorrect username or password.");
				}
			});
		}
		break;
		case OnlineStudent: {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController
							.setErrorLabel("The user is already logged in to the system.");
				}
			});
		}
		break;
		default:
			break;

		}
	}

	/**
	 * Handles the response from the server for user logout.
	 *
	 * @param msg the Message received from the server.
	 */
	public static void receiveUserLogout(Message msg) {
		Message recivedMessage = (Message) msg;
		if (recivedMessage.getAnswer() == Answers.Logout) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginPageController.loginPageController
							.setErrorLabel("Logout Sucessfully.");
				}
			});
		}

	}

}
