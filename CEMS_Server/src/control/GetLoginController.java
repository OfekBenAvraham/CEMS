package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.HeadOfDepartment;
import entities.Lecturer;
import entities.Message;
import entities.Student;
import entities.User;
import enums.Answers;
import enums.Operations;
import enums.Role;

/**
 * Controller for handling login related operations. This class provides methods
 * for lecturer, head of department and student login. It also provides a method
 * for handling user logout.
 */
public class GetLoginController {

	/**
	 * Handles lecturer login process. This method takes in a Message object which
	 * contains the lecturer login details, processes the login request and returns
	 * a Message object containing the result of the operation.
	 *
	 * @param msg The Message object containing the login details
	 * @return A Message object containing the result of the login operation
	 */
	public static Message lecturerLogin(Message msg) {
		System.out.println("Lecturer login");

		Message resMessage = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>) msg.getObj();
		Lecturer lecturer;
		User user;
		ResultSet rs = Query.selectTable2Where("islecturer", "userName", "password", data.get(0), data.get(1));
		try {
			if (!rs.next()) {
				System.out.println("NOT FOUND");
				resMessage = new Message(Operations.LecturerLogin, Answers.IncorrectLecturer, new Lecturer());
			} else {
				if (rs.getString(10).equals("FALSE")) { // Check if the user already login to the system
					// The user not login.
					user = new User();
					user.setIsLoggedIn("TRUE"); // sets logged in to true.
					user.setFirstName(rs.getString(1));
					user.setLastName(rs.getString(2));
					user.setUserID(rs.getString(3));
					user.setEmail(rs.getString(4));
					user.setUsername(rs.getString(5));
					user.setPassword(rs.getString(4));
					user.setPhoneNumber(rs.getString(9));
					user.setRole(Role.LECTURER);
					Query.updateOnlineStatusForLoggedUser(rs.getString(3));
					lecturer = new Lecturer(user.getRole(), user.getUsername(), user.getPassword(), user.getFirstName(),
							user.getLastName(), user.getUserID(), user.getEmail(), user.getPhoneNumber());
					resMessage = new Message(Operations.LecturerLogin, Answers.OfflineLecturer, lecturer);
					System.out.println(lecturer.getLecturerFullName());
					System.out.println("Lecturer offline - > can be login");
				} else {
					resMessage = new Message(Operations.LecturerLogin, Answers.OnlineLecturer, new Lecturer());
					System.out.println("User Online");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;

	}

	/**
	 * Handles head of department login process. This method takes in a Message
	 * object which contains the head of department login details, processes the
	 * login request and returns a Message object containing the result of the
	 * operation.
	 *
	 * @param msg The Message object containing the login details
	 * @return A Message object containing the result of the login operation
	 */
	public static Message headOfDepLogin(Message msg) {
		System.out.println("Head Of Department Login");

		Message resMessage = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>) msg.getObj();
		HeadOfDepartment headOfDep;
		User user;
		ResultSet rs = Query.selectTable2Where("isheadofdepartment", "userName", "password", data.get(0), data.get(1));
		try {
			if (!rs.next()) {
				System.out.println("NOT FOUND");
				resMessage = new Message(Operations.HeadOfDepartmentLogin, Answers.IncorrectHeadOfDepartment,
						new HeadOfDepartment());
			} else {
				if (rs.getString(10).equals("FALSE")) { // Check if the user already logged in to the system
					// The user not login.
					user = new User();
					user.setIsLoggedIn("TRUE"); // sets logged in to true.
					user.setFirstName(rs.getString(1));
					user.setLastName(rs.getString(2));
					user.setUserID(rs.getString(3));
					user.setEmail(rs.getString(4));
					user.setUsername(rs.getString(5));
					user.setPassword(rs.getString(4));
					user.setPhoneNumber(rs.getString(9));
					user.setRole(Role.HEAD_OF_DEPARTMENT);
					Query.updateOnlineStatusForLoggedUser(rs.getString(3));
					String fieldCode = Query.getFieldCodeForHeadOfDep(user.getUserID());
					
					
					headOfDep = new HeadOfDepartment(user.getRole(),user.getUsername(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getUserID(),user.getEmail(),user.getPhoneNumber());
					headOfDep.setFieldCode(fieldCode);
					resMessage = new Message(Operations.HeadOfDepartmentLogin, Answers.OfflineHeadOfDepartment,
							headOfDep);
					System.out.println(headOfDep.getFullName());
					System.out.println("Head Of Department offline - > can be login");
				} else {
					resMessage = new Message(Operations.HeadOfDepartmentLogin, Answers.OnlineHeadOfDepartment,
							new HeadOfDepartment());
					System.out.println("User Online");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;

	}

	/**
	 * Handles student login process. This method takes in a Message object which
	 * contains the student login details, processes the login request and returns a
	 * Message object containing the result of the operation.
	 *
	 * @param msg The Message object containing the login details
	 * @return A Message object containing the result of the login operation
	 */
	public static Message studentLogin(Message msg) {
		System.out.println("Student Login");

		Message resMessage = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>) msg.getObj();
		Student student;
		User user;
		ResultSet rs = Query.selectTable2Where("isstudent", "userName", "password", data.get(0), data.get(1));
		try {
			if (!rs.next()) {
				System.out.println("NOT FOUND");
				resMessage = new Message(Operations.StudentLogin, Answers.IncorrectStudent, new Student());
			} else {
				if (rs.getString(10).equals("FALSE")) { // Check if the user already login to the system
					// The user not login.
					user = new User();
					user.setIsLoggedIn("TRUE"); // sets logged in to true.
					user.setFirstName(rs.getString(1));
					user.setLastName(rs.getString(2));
					user.setUserID(rs.getString(3));
					user.setEmail(rs.getString(4));
					user.setUsername(rs.getString(5));
					user.setPassword(rs.getString(4));
					user.setPhoneNumber(rs.getString(9));
					user.setRole(Role.STUDENT);
					Query.updateOnlineStatusForLoggedUser(rs.getString(3));

					student = new Student(user.getRole(), user.getUsername(), user.getPassword(), user.getFirstName(),
							user.getLastName(), user.getUserID(), user.getEmail(), user.getPhoneNumber());
					resMessage = new Message(Operations.StudentLogin, Answers.OfflineStudent, student);
					System.out.println(student.getFullName());
					System.out.println("Student offline - > can be login");
				} else {
					resMessage = new Message(Operations.StudentLogin, Answers.OnlineStudent, new Student());
					System.out.println("User Online");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;

	}

	/**
	 * Handles user logout process. This method takes in a Message object which
	 * contains the user logout details, processes the logout request and returns a
	 * Message object containing the result of the operation.
	 *
	 * @param receivedMessage The Message object containing the logout details
	 * @return A Message object containing the result of the logout operation
	 */
	public static Message userLogout(Message receivedMessage) {
		Message resMessage = new Message(Operations.UserLogout, Answers.Logout);
		String userID = (String) receivedMessage.getObj();
		Query.updateOnlineStatusLogout(userID);
		return resMessage;
	}

}
