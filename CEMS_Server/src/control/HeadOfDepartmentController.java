package control;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.*;
import enums.Answers;
import enums.Role;

/**
 * This controller class manages the actions of the Head of Department role. 
 * It communicates with the database to fetch, modify or store data related to this role.
 */
public class HeadOfDepartmentController {
	
	/**
	 * Retrieves all users from the database.
	 *
	 * @param receivedMessage A message containing the user details.
	 * @return A response message with the list of users or null if no users found.
	 */
	public static Message getUsers(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getUsers();
		if (rs == null) {
			return null;
		}
		ArrayList<User> users = new ArrayList<User>();
		try {
			if (!rs.next()) {
				return null;
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					User user = new User(rs.getString("userID"), rs.getString("firstName"), rs.getString("lastName"));
					String lecturer = rs.getString("islecturer");
					String student = rs.getString("isstudent");
					String headOfDep = rs.getString("isheadofdepartment");
					if(lecturer.toLowerCase().equals("true") && headOfDep.toLowerCase().equals("true"))
					{
						user.setRole(Role.LECTURER);
						users.add((user));
						User u = new User(rs.getString("userID"), rs.getString("firstName"), rs.getString("lastName"));
						u.setRole(Role.HEAD_OF_DEPARTMENT);
						users.add((u));
					}
					else if(lecturer.toLowerCase().equals("true") && student.toLowerCase().equals("true"))
					{
						user.setRole(Role.STUDENT);
						users.add((user));
						User u = new User(rs.getString("userID"), rs.getString("firstName"), rs.getString("lastName"));
						u.setRole(Role.LECTURER);
						users.add((u));
					}
					else if(headOfDep.toLowerCase().equals("true"))
					{
						user.setRole(Role.HEAD_OF_DEPARTMENT);
						users.add((user));
					}
					else if(lecturer.toLowerCase().equals("true")) {
						user.setRole(Role.LECTURER);
						users.add((user));
					}
					else {
						user.setRole(Role.STUDENT);
						users.add((user));
					}
					
				}
			}
			System.out.println(users);
			resMessage = new Message(receivedMessage.getOperation(), users);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Retrieves exams statistics based on the student's ID.
	 *
	 * @param receivedMessage A message containing the student ID.
	 * @return A response message with the list of exams or null if no exams found.
	 */
	public static Message getExamsToStatsByStudentId(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getExamsToStatsByStudentId((ArrayList<String>) receivedMessage.getObj());
		if (rs == null) {
			return null;
		}
		ArrayList<ExamsDisplayData> exams = new ArrayList<ExamsDisplayData>();
		try {
				while (rs.next()) {
					ExamsDisplayData ex = new ExamsDisplayData(rs.getString("course"), rs.getString("courseName"), 
							rs.getString("executionCode"), rs.getString("id"), rs.getTimestamp("executionDateTime"),rs.getString("executeExamSerialNum"));
					System.out.println(ex);
					exams.add((ex));
				}
			resMessage = new Message(receivedMessage.getOperation(), exams);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}

	/**
	 * Retrieves exams statistics based on the course code.
	 *
	 * @param receivedMessage A message containing the course code.
	 * @return A response message with the list of exams or null if no exams found.
	 */
	public static Message getExamsToStatsByCourseCode(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getExamsToStatsByCourseCode((ArrayList<String>) receivedMessage.getObj());
		if (rs == null) {
			return null;
		}

		ArrayList<ExamsDisplayData> exams = new ArrayList<ExamsDisplayData>();
		try {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					ExamsDisplayData ex = new ExamsDisplayData(rs.getString("course"), rs.getString("courseName"), 
							rs.getString("executionCode"), rs.getString("id"), rs.getTimestamp("executionDateTime"),rs.getString("executeExamSerialNum"));
					System.out.println(ex);
					exams.add((ex));
				}
			resMessage = new Message(receivedMessage.getOperation(), exams);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	/**
	 * Retrieves requests that have not been responded to yet.
	 *
	 * @param receivedMessage A message containing request data.
	 * @return A response message with the list of unresponded requests or an appropriate message if no such requests are found.
	 */
	public static Message getRequestsNotResponded(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getNotRespondedRequests();
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.NotRespondedRequestsNotFound);
			return resMessage;
		}

		ArrayList<Request> requests = new ArrayList<Request>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.NotRespondedRequestsNotFound);
			} else {
				rs.beforeFirst(); 
				while (rs.next()) {
					if (rs.getInt(6) > 0) {
						Request req = new Request(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getInt(5), rs.getInt(6) , rs.getString(8));
						requests.add((req));
					}
				}
			}
			resMessage = new Message(receivedMessage.getOperation(), Answers.NotRespondedRequestsFound, requests);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Accepts a request to change the exam time.
	 *
	 * @param recievedMessage A message containing details of the request.
	 * @return A response message indicating the result of the operation.
	 */
	// When head of dep approves change time request. need to update in DB
	public static Message acceptExamTimeChangeRequest(Message recievedMessage) {
		Message resMessage = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> info = (ArrayList<String>) recievedMessage.getObj();
		System.out.println(info);
		if (Query.updateExamTimeAfterApproveOfTimeChange(info.get(1), info.get(0))
				&& Query.updateRequestStatusApproved(info.get(3), "APPROVED"))
			resMessage = new Message(recievedMessage.getOperation(), Answers.updatedApprovedRequestSuccessfully);
		else
			resMessage = new Message(recievedMessage.getOperation(),Answers.ErrorInDB);

		return resMessage;
	}
	
	/**
	 * Rejects a request to change the exam time.
	 *
	 * @param recievedMessage A message containing details of the request.
	 * @return A response message indicating the result of the operation.
	 */
	// When head of department reject change time request. need to update in DB
	public static Message rejectExamTimeChangeRequest(Message recievedMessage) {
		Message resMessage = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> info = (ArrayList<String>) recievedMessage.getObj();
		if (Query.updateRequestStatusRejected(info.get(2), "REJECTED"))
			resMessage = new Message(recievedMessage.getOperation(), Answers.updatedRejectedRequestSuccessfully);
		else
			resMessage = new Message(recievedMessage.getOperation(),Answers.ErrorInDB);

		return resMessage;
	}

	/**
	 * Retrieves all lecturers from the database.
	 *
	 * @param receivedMessage A message containing the operation details.
	 * @return A response message with the list of lecturers or null if no lecturers found.
	 */
	public static Message getLecturers(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getLecturers();
		if (rs == null) {
			return resMessage;
		}
		ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();
		try {
				while (rs.next()) {
					Lecturer lect = new Lecturer(rs.getString("id"),rs.getString("firstName"),rs.getString("lastName"));
					lecturers.add(lect);
					}
			resMessage = new Message(receivedMessage.getOperation(),lecturers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(lecturers);
		return resMessage;
	}
	
	/**
	 * Retrieves all students from the database.
	 *
	 * @param receivedMessage A message containing the operation details.
	 * @return A response message with the list of students or null if no students found.
	 */
	public static Message getStudents(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getStudents();
		if (rs == null) {
			return resMessage;
		}
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			if (!rs.next()) {
				Student stud = new Student(rs.getString("userId"), rs.getString("firstName"),rs.getString("lastName"));
				students.add(stud);
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					Student stud = new Student(rs.getString("userId"), rs.getString("firstName"),rs.getString("lastName"));
					students.add(stud);
					}
				}
			resMessage = new Message(receivedMessage.getOperation(), students);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Retrieves all courses from the database.
	 *
	 * @param receivedMessage A message containing the operation details.
	 * @return A response message with the list of courses or null if no courses found.
	 */
	public static Message getCourses(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getCourses();
		if (rs == null) {
			return resMessage;
		}
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			if (!rs.next()) {
				return null;
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					Course crs = new Course(rs.getString("courseName"), rs.getString("courseCode"),rs.getString("fieldCode"));
					courses.add(crs);
					}
				}
			resMessage = new Message(receivedMessage.getOperation(), courses);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Prints the ResultSet for checking.
	 *
	 * @param rs The ResultSet to be printed.
	 * @throws SQLException if a database access error occurs.
	 */
	// print method for resultsets for checking.
	public static void printResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = rs.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}

}