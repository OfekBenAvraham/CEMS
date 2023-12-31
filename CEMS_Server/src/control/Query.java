package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import entities.Course;
import entities.CourseDoneByStudent;
import entities.Exam;
import entities.Field;
import entities.QuestionInExam;
import enums.ExamStatus;
import enums.ExamType;
import entities.Request;
import entities.Student;
import entities.StudentInComputerizedExam;

/**
 * Query is a utility class responsible for handling operations related to the
 * database. This includes inserting, updating, and deleting data from different
 * tables in the database.
 * 
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Maayan Avittan
 * @author Guy Pariente
 * @author Almog Elbaz
 * @author Paz Fayer
 */
public class Query {

// ************************* Database ******************************

	/**
	 * Inserts a new user record in the users table.
	 * 
	 * @param firstName          the first name of the user
	 * @param lastName           the last name of the user
	 * @param userID             the unique identifier for the user
	 * @param email              the email of the user
	 * @param userName           the username chosen by the user
	 * @param password           the password set by the user
	 * @param phoneNumber        the phone number of the user
	 * @param isLecturer         flag to determine if the user is a lecturer
	 * @param isStudent          flag to determine if the user is a student
	 * @param isLoggingIn        flag to determine if the user is currently logged
	 *                           in
	 * @param isHeadOfDepartment flag to determine if the user is a head of
	 *                           department
	 */
	public static void insertUsers(String firstName, String lastName, String userID, String email, String userName,
			String password, String phoneNumber, String islecturer, String isstudent, String isLogginIn,
			String isheadofdepartment) {
		UpdateQuery(
				"INSERT INTO users (firstName, lastName, userID, email, userName, password, islecturer, isstudent, phoneNumber, isLoggedIn, isheadofdepartment) VALUES ('"
						+ firstName + "','" + lastName + "','" + userID + "','" + email + "','" + userName + "','"
						+ password + "','" + islecturer + "','" + isstudent + "','" + phoneNumber + "','" + isLogginIn
						+ "','" + isheadofdepartment + "');");
	}

	/**
	 * Inserts a lecturer into a course in the database.
	 * 
	 * @param lecturerId - The unique identifier of the lecturer.
	 * @param courseCode - The code of the course.
	 */
	public static void insertLecturerInCourse(String lecturerId, String courseCode) {
		UpdateQuery("INSERT INTO lecturerincourse (lecturerId, courseCode) VALUES ('" + lecturerId + "','" + courseCode
				+ "');");
	}

	/**
	 * Inserts a student into a course in the database.
	 * 
	 * @param courseCode - The code of the course.
	 * @param studentID  - The unique identifier of the student.
	 */
	public static void insertStudentInCourse(String courseCode, String studentID) {
		UpdateQuery("INSERT INTO studentincourse (courseCode, studentID) VALUES ('" + courseCode + "','" + studentID
				+ "');");
	}

	/**
	 * Inserts a field into the database.
	 * 
	 * @param fieldName - The name of the field.
	 * @param fieldCode - The code of the field.
	 */
	public static void insertFields(String fieldName, String fieldCode) {
		UpdateQuery("INSERT INTO fields (fieldName, fieldCode) VALUES ('" + fieldName + "','" + fieldCode + "');");
	}

	/**
	 * Inserts a course into the database.
	 * 
	 * @param courseName - The name of the course.
	 * @param courseCode - The code of the course.
	 * @param fieldCode  - The code of the field to which the course belongs.
	 */
	public static void insertCourses(String courseName, String courseCode, String fieldCode) {
		UpdateQuery("INSERT INTO courses (courseName, courseCode, fieldCode) VALUES ('" + courseName + "','"
				+ courseCode + "','" + fieldCode + "');");
	}

	/**
	 * Inserts a new record in the headofdepartment table.
	 * 
	 * @param headOfDepartmentID the unique identifier for the head of department
	 * @param firstName          the first name of the head of department
	 * @param lastName           the last name of the head of department
	 */
	public static void insertHeadOfDepartment(String headOfDepartmentID, String firstName, String lastNamet) {
		UpdateQuery("INSERT INTO headofdepartment (headofdepartmentId, firstName, lastName ) VALUES ('"
				+ headOfDepartmentID + "','" + firstName + "','" + lastNamet + "');");
	}

	/**
	 * Inserts a new lecturer record in the lecturers table.
	 * 
	 * @param lecturerID the unique identifier for the lecturer
	 * @param firstName  the first name of the lecturer
	 * @param lastName   the last name of the lecturer
	 */
	public static void insertLecturer(String lecturerID, String firstName, String lastName) {
		UpdateQuery("INSERT INTO lecturers (id, firstName, lastName ) VALUES ('" + lecturerID + "','" + firstName
				+ "','" + lastName + "');");
	}

	/**
	 * Inserts a new student record in the students table.
	 * 
	 * @param studentID the unique identifier for the student
	 * @param firstName the first name of the student
	 * @param lastName  the last name of the student
	 */
	public static void insertStudent(String studentID, String firstName, String lastName) {
		UpdateQuery("INSERT INTO students (userId, firstName, lastName) VALUES ('" + studentID + "','" + firstName
				+ "','" + lastName + "');");
	}

//	************************* Student ******************************
	/**
	 * Retrieves the duration time for a given execution code.
	 *
	 * @param code the execution code
	 * @return the result set containing the duration time
	 */
	public static ResultSet getDurationTime(String code) {
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom(
				"SELECT exams.duration " + "FROM exams " + "JOIN executedexams ON exams.id = executedexams.examID "
						+ "WHERE executedexams.executionCode = " + code + ";");

		return rs;
	}

//	/**
//	 * Retrieves the statistics for a given execution code.
//	 *
//	 * @param code the execution code
//	 * @return the result set containing the statistics
//	 */
//	public static ResultSet getStatistics(String code) {
//		ResultSet rs; // get table from the DB
//		rs = resultqueryFrom("SELECT finalGrade " + "FROM studentincomputerizedexam "
//				+ "WHERE finalGrade IS NOT NULL AND executeExamSerialNum = " + code + ";");
//
//		return rs;
//	}
//	
	/**
	 * 
	 * Retrieves the statistics for a given execution code.
	 * 
	 * @param selectedGrade the execution code
	 * 
	 * @return the result set containing the statistics
	 */
	public static ResultSet getStatistics(Object selectedGrade) {
		CourseDoneByStudent ob = (CourseDoneByStudent) selectedGrade;
		String executedExamId = ob.getExamID();
		Student s = (Student) ob.getStudent();
		String sID = s.getStudentId();
		double grade = ob.getGrade();
		String sGrade = String.valueOf(grade);

		System.out.println("executedExamId" + executedExamId);
		System.out.println("sID" + sID);
		System.out.println("sGrade" + sGrade);
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom(
				"SELECT finalGrade " + "FROM cems.studentincomputerizedexam " + "WHERE finalGrade IS NOT NULL "
						+ "AND executedExamId = '" + executedExamId + "' " + "AND executeExamSerialNum = ( "
						+ "    SELECT executeExamSerialNum " + "    FROM cems.studentincomputerizedexam "
						+ "    WHERE userId = '" + sID + "' AND finalGrade = " + sGrade + ")");
		return rs;
	}
	
	public static ResultSet getStatisticsForLecturer(Object selectedGrade) {
		ArrayList<String> data = (ArrayList<String>) selectedGrade;
		//examid,serialnumberofexecutedExam
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom("SELECT finalGrade FROM studentincomputerizedexam " +
							"WHERE finalGrade IS NOT NULL AND executeExamSerialNum = " + "'" + data.get(1) + "';");
		return rs;
	}

	/**
	 * Retrieves the grades of a student with a given student ID.
	 *
	 * @param studentID the student ID
	 * @return the result set containing the student grades
	 */
	public static ResultSet getStudentGrades(String studentID) {
		ResultSet rs;
		String query = "SELECT c.courseName, s.executedExamId, s.finalGrade, s.note "
				+ "FROM cems.studentincomputerizedexam s " + "JOIN cems.exams e ON s.executedExamId = e.id "
				+ "JOIN cems.courses c ON e.course = c.courseCode " + "WHERE s.userId = '" + studentID
				+ "' AND s.finalGrade IS NOT NULL";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Retrieves the execution type of a student exam for a given execution code.
	 *
	 * @param code the execution code
	 * @return the result set containing the execution type
	 */
	public static ResultSet getStudentExam(String code) {
		ResultSet rs; // get table from the DB
		String query = "SELECT exams.executionType FROM cems.executedexams "
				+ "JOIN cems.exams ON cems.executedexams.examID = cems.exams.id "
				+ "WHERE cems.executedexams.executionCode = '" + code + "'";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Retrieves the details of a student's computerized exam for a given execution
	 * code.
	 *
	 * @param code the execution code
	 * @return the result set containing the details of the computerized exam
	 */
	public static ResultSet studentstartComputerizedExam(String code) {
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom(
				"SELECT q.questiontext, q.option1, q.option2, q.option3, q.option4, e.duration, qie.points, q.correctanswer,  ee.examID, ee.serialNumber, e.descriptionForExaminee\n"
						+ "FROM cems.questions q\n" + "JOIN cems.questioninexam qie ON q.id = qie.questionId\n"
						+ "JOIN cems.executedexams ee ON qie.examId = ee.examID\n"
						+ "JOIN cems.exams e ON ee.examID = e.id\n" + "WHERE ee.executionCode = '" + code
						+ "' AND ee.status = '" + ExamStatus.active.name() + "';");
		return rs;
	}

	/**
	 * Checks the lock status of an executed exam for a given execution code.
	 *
	 * @param code the execution code
	 * @return the result set containing the lock status
	 */
	public static ResultSet LockCheck(String code) {
		ResultSet rs; // get table from the DB
		String query = "SELECT `status` FROM cems.executedexams WHERE executionCode = '" + code + "'";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Checks the time change status of an executed exam for a given execution code.
	 *
	 * @param code the execution code
	 * @return the result set containing the time change status
	 */
	public static ResultSet TimeChangeCheck(String code) {
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom("SELECT `timeChange` FROM cems.executedexams WHERE executionCode ='" + code + "';");
		return rs;
	}

	/**
	 * Sends the student's answers to the database.
	 *
	 * @param studentInComputerizedExam the student's computerized exam
	 * @param studentAnswers            the student's answers
	 */

	public static void SendingStudentAnswersToDB(Object studentInComputerizedExam, String studentAnswers) {
		StudentInComputerizedExam sICE = (StudentInComputerizedExam) studentInComputerizedExam;
		String studentID = sICE.getStudentInComputerizedExam().getUserID();
		String examID = sICE.getQuestionInExecutedExam().get(0).getExamId(); // Assuming this returns a string value
		Double grade = sICE.getGradeFromAutoCheck();
		boolean cheated = sICE.isCheated();
		int howLong = sICE.getHowLongTheExamLast();
		int serialNumber = sICE.getSerialNumber();
		String castForBoolean = cheated ? "TRUE" : "FALSE"; // if cheated then set to TRUE, else FALSE
		String query = "INSERT INTO cems.studentincomputerizedexam (executedExamId, userId, studentanswers, grade, cheated, howLong, executeExamSerialNum) "
				+ "VALUES ('" + examID + "','" + studentID + "','" + studentAnswers + "','" + grade + "','"
				+ castForBoolean + "','" + howLong + "','" + serialNumber + "')";
		UpdateQuery(query);
	}

	/**
	 * Checks if a student has cheated in an exam.
	 *
	 * @param eID the exam ID
	 * @param sID the student ID
	 * @return the result set containing the student's answers if they cheated
	 */
	public static ResultSet CheckIfCheated(String eID, String sID) {
		ResultSet rs;
		rs = resultqueryFrom("SELECT studentanswers FROM cems.studentincomputerizedexam WHERE executedExamId = " + eID
				+ " AND userId != " + sID + ";"); // get the other students's answers to check if the specific student
													// is cheated
		return rs;
	}

	/**
	 * Checks if a student has taken a particular exam.
	 *
	 * @param examCode  the exam code
	 * @param studentID the student ID
	 * @return the result set containing the answer status
	 */
	public static ResultSet CheckIfDidExam(String examCode, String studentID) {
		ResultSet rs;
		String query = "SELECT CASE WHEN s.userId IS NULL THEN 'No answer' ELSE CASE WHEN s.studentanswers IS NULL OR s.studentanswers = '' THEN 'No answer' ELSE 'Has answer' END END AS answerStatus FROM cems.executedexams e LEFT JOIN cems.studentincomputerizedexam s ON s.executedExamId = e.examID AND s.userId = '"
				+ studentID + "' WHERE e.executionCode = '" + examCode + "'";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Retrieves the student's answers for a particular exam from the database.
	 * 
	 * @param data an ArrayList containing the student's ID at index 1 and the exam
	 *             ID at index 0.
	 * @return a ResultSet containing the student's answers for the specified exam.
	 */
	public static ResultSet getStudentAnswerdByExamAndStudentID(ArrayList<String> data) {
		ResultSet rs;
		System.out.println(data);
		rs = resultqueryFrom("SELECT studentanswers FROM cems.studentincomputerizedexam " + "WHERE userId = " + "'"
				+ data.get(1) + "'" + " AND executeExamSerialNum = " + "'" + data.get(2) + "'" + ";");
		return rs;
	}

	// **************** Lecturer Methods ******************

	/**
	 * Retrieves the IDs of students whose exams need validation from a lecturer.
	 * 
	 * @param data an ArrayList containing the lecturer's ID at index 0, the exam ID
	 *             at index 1, and the execution code at index 2.
	 * @return a ResultSet containing the IDs of students whose exams need
	 *         validation.
	 */
	public static ResultSet getStudentsIdRsToValidate(ArrayList<String> data) {
		String lecturerId = data.get(0);
		String examId = data.get(1);
		String executionCode = data.get(2);
		String serialNumber = data.get(5);
		System.out.println(lecturerId + "**" + examId + "**" + executionCode + "**" + serialNumber);
		ResultSet rs;
		rs = resultqueryFrom("SELECT s.userId FROM studentincomputerizedexam AS s "
				+ "JOIN executedexams AS e ON s.executedExamId = e.examID " + "JOIN exams AS ex ON e.examID = ex.id "
				+ "	WHERE s.finalGrade IS NULL AND e.executionCode = '" + executionCode + "' AND s.executedExamId = '"
				+ examId + "' AND ex.lecturerId = '" + lecturerId + "' AND s.executeExamSerialNum = '" + serialNumber
				+ "';");
		return rs;
	}

	/**
	 * Retrieves the grade for a specific student and exam from the database.
	 * 
	 * @param data an ArrayList containing the student's ID at index 0 and the
	 *             executed exam ID at index 1.
	 * @return a ResultSet containing the grade for the specified student and exam.
	 */
	public static ResultSet getGradeByStudentId(ArrayList<String> data) {
		String studentId = data.get(0);
		String executedExamId = data.get(1);
		String executionExamSerialNumber = data.get(3);
		System.out.println(studentId + "****"  +  executedExamId + "****" + executionExamSerialNumber);
		ResultSet rs;
		rs = resultqueryFrom("SELECT grade,cheated FROM cems.studentincomputerizedexam " + "WHERE executedExamId = "
				+ "'" + executedExamId + "' AND userId = '" + studentId + "' AND executeExamSerialNum = '"
				+ executionExamSerialNumber + "';");
		return rs;
	}

	/**
	 * Retrieves exams, related to a particular lecturer, from the database for
	 * statistical analysis.
	 * 
	 * @param info an ArrayList containing the lecturer's ID at index 0.
	 * @return a ResultSet containing the exams related to the specified lecturer.
	 */
	public static ResultSet getExamsToStatsByLecturerId(ArrayList<String> info) {
		String lecturerId = info.get(0);
		ResultSet rs;
		String query = "SELECT DISTINCT e.course,e.id, c.courseName, ee.executionDateTime, ee.executionCode, sice.executeExamSerialNum "
				+ "FROM exams e " + "JOIN executedexams ee ON e.id = ee.examID "
				+ "JOIN studentincomputerizedexam sice ON ee.serialNumber = sice.executeExamSerialNum "
				+ "JOIN courses c ON e.course = c.courseCode " + "WHERE e.lecturerId = '" + lecturerId
				+ "' AND sice.finalGrade IS NOT NULL;";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Retrieves the courses related to a particular lecturer from the database.
	 * 
	 * @param lecturerId the ID of the lecturer whose related courses are to be
	 *                   retrieved.
	 * @return a ResultSet containing the courses related to the specified lecturer.
	 */
	public static ResultSet getCoursesByLecturerId(String lecturerId) {
		ResultSet rs;
		rs = resultqueryFrom("SELECT lic.courseCode, c.courseName FROM cems.lecturerincourse AS lic "
				+ "JOIN cems.courses AS c ON lic.courseCode = c.courseCode " + "WHERE lic.lecturerId = " + lecturerId
				+ ";");
		return rs;
	}

	/**
	 * Retrieves the active exams related to a particular lecturer from the
	 * database.
	 * 
	 * @param lecturerId the ID of the lecturer whose related active exams are to be
	 *                   retrieved.
	 * @return a ResultSet containing the active exams related to the specified
	 *         lecturer.
	 */
	public static ResultSet getExamsFoundByLecturerId(String lecturerId) {
		ResultSet rs; // get table from the DB
		rs = resultqueryFrom(
				"SELECT e.executionCode,e.executionDateTime, e.serialNumber ,c.courseName,ex.duration, ex.id "
						+ "FROM executedexams e " + "JOIN exams ex ON e.examID = ex.id "
						+ "JOIN courses c ON ex.course = c.courseCode "
						+ "WHERE e.status = 'active' AND ex.lecturerId =  " + lecturerId + ";");
		return rs;
	}

	/**
	 * Updates the final grade and notes for a student after the grades have been
	 * approved.
	 * 
	 * @param data an ArrayList containing the student's ID at index 0, the executed
	 *             exam ID at index 1, the final grade at index 2, and the notes at
	 *             index 3.
	 */
	public static void updateFinalGradeAndNotesAfterApprovement(ArrayList<String> data) {
		UpdateQuery(
				"UPDATE cems.studentincomputerizedexam " + "SET note = " + "'" + data.get(3) + "'," + "finalGrade =  "
						+ data.get(2) + " " + "WHERE userId = " + "'" + data.get(0) + "'" + " AND executedExamId = "
						+ "'" + data.get(1) + "'" + " AND executeExamSerialNum = " + "'" + data.get(4) + "';");

		UpdateQuery("UPDATE studentincourse " + "SET grade = " + "'" + data.get(2) + "'" + "WHERE studentID = " + "'"
				+ data.get(0) + "'" + " AND courseCode IN ( " + "    SELECT course " + "    FROM exams "
				+ "    WHERE id IN (" + " SELECT executedExamId " + " FROM studentincomputerizedexam "
				+ "        WHERE userId = '" + data.get(0) + "'" + " AND executeExamSerialNum = " + "'" + data.get(4) + "'"
				+ ")" + ");");
	}

	/**
	 * Retrieves the completed exams for a specific course and lecturer from the
	 * database.
	 * 
	 * @param data an ArrayList containing the lecturer's ID at index 0 and the
	 *             course code at index 1.
	 * @return a ResultSet containing the completed exams for the specified course
	 *         and lecturer.
	 */
	public static ResultSet getDoneExamsByCourseAndLecturerId(ArrayList<String> data) {
		String lecturerId = data.get(0);
		String courseCode = data.get(1);
		ResultSet rs;
		rs = resultqueryFrom(
				"SELECT c.courseCode,c.courseName,e.executionCode,ex.id,e.executionDateTime, e.serialNumber, s.executeExamSerialNum "
						+ "FROM executedexams e " + "JOIN exams ex ON e.examID = ex.id "
						+ "JOIN courses c ON ex.course = c.courseCode "
						+ "JOIN studentincomputerizedexam s ON e.examId = s.executedExamId AND s.executeExamSerialNum = e.serialNumber "
						+ "WHERE e.status = 'locked' AND ex.lecturerId = '" + lecturerId + "' AND ex.course = '"
						+ courseCode + "'" + " AND s.finalGrade IS NULL;");

		return rs;
	}

	/**
	 * Updates the status of an exam to 'locked' in the database.
	 * 
	 * @param exam an ArrayList containing the exam code at index 0 and the exam ID
	 *             at index 1.
	 * @return a boolean indicating the result of the update operation.
	 */
	public static boolean LecturerUpdateExamStatusForLocked(ArrayList<String> exam) {
		boolean res;
		String examCode = exam.get(0);
		String examId = exam.get(1);
		res = UpdateQueryReturnValue("UPDATE executedexams SET status = \"locked\"" + " WHERE examID = " + examId
				+ " AND executionCode = '" + examCode + "';");
		if (res == true)
			return true;
		return false;
	}

	/**
	 * Inserts a new request into the database.
	 * 
	 * @param req a Request object representing the new request to be inserted.
	 * @return a boolean indicating the result of the insert operation.
	 */
	public static boolean insertNewRequest(Request req) {

		boolean res;
		res = UpdateQueryReturnValue(
				"INSERT INTO cems.requests (requestId, examId, freeText, lecturerName, duration, extraTime, requestStatus, lecturerId, executedExamSerialNum) "
						+ "VALUES (" + req.getRequestID() + "," + "'" + req.getExamId() + "'" + "," + "'"
						+ req.getFreeText() + "'," + "'" + req.getLecturerName() + "'," + +req.getDuration() + ","
						+ req.getExtraTime() + "," + "'NOT_RESPONDED_YET'" + "," + "'" + req.getLecturerId() + "',"
						+ "'" + req.getExecutedExamSerialNum() + "'" + ");");
		if (res == true)
			return true;
		return false;
	}

//	******************* Head Of Department Methods *****************

	/**
	 * Fetches all users from the database.
	 * 
	 * @return ResultSet containing all user data.
	 */
	public static ResultSet getUsers() {
		ResultSet rs;
		String query = "SELECT userID,firstName,lastName,islecturer,isstudent,isheadofdepartment FROM cems.users;";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Fetches the field code for a given Head of Department ID.
	 * 
	 * @param userId The user ID for the Head of Department.
	 * @return The field code for the given user ID.
	 */
	public static String getFieldCodeForHeadOfDep(String userId) {
		ResultSet rs;
		String query = "SELECT fieldCode FROM cems.headofdepartment WHERE headOfDepartmentId = " + "'" + userId + "'"
				+ ";";
		rs = resultqueryFrom(query);
		String fieldCode = "";
		try {
			if (rs.next()) {
				fieldCode = rs.getString("fieldCode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldCode;
	}

	/**
	 * Fetches exams and statistics for a given student ID.
	 * 
	 * @param info ArrayList containing the student ID.
	 * @return ResultSet containing exam and statistic data.
	 */
	public static ResultSet getExamsToStatsByStudentId(ArrayList<String> info) {
		String studentId = info.get(0);
		ResultSet rs;
		String query = "SELECT DISTINCT e.*, c.courseName, ee.executionDateTime, ee.executionCode, sice.executeExamSerialNum "
				+ "FROM exams e " + "JOIN executedexams ee ON e.id = ee.examID "
				+ "JOIN studentincomputerizedexam sice ON ee.serialNumber = sice.executeExamSerialNum "
				+ "JOIN courses c ON e.course = c.courseCode " + "WHERE sice.userId = '" + studentId
				+ "' AND sice.finalGrade IS NOT NULL;";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Fetches exams and statistics for a given course code.
	 * 
	 * @param info ArrayList containing the course code.
	 * @return ResultSet containing exam and statistic data.
	 */
	public static ResultSet getExamsToStatsByCourseCode(ArrayList<String> info) {
		String courseCode = info.get(0);
		ResultSet rs;
		String query = "SELECT DISTINCT e.*, c.courseName, ee.executionDateTime, ee.executionCode,executeExamSerialNum "
				+ "FROM exams e " + "JOIN executedexams ee ON e.id = ee.examID "
				+ "JOIN studentincomputerizedexam sice ON ee.serialNumber = sice.executeExamSerialNum "
				+ "JOIN courses c ON e.course = c.courseCode " + "WHERE c.courseCode = '" + courseCode
				+ "' AND sice.finalGrade IS NOT NULL;";
		rs = resultqueryFrom(query);
		return rs;
	}

	/**
	 * Fetches exams for a given lecturer ID.
	 * 
	 * @param lecturerId The ID of the lecturer.
	 * @return ResultSet containing the exams.
	 */
	public static ResultSet getExamsByLecturerId(String lecturerId) {
		ResultSet rs;
		rs = resultqueryFrom("SELECT * FROM cems.lecturers ");
		return rs;
	}

	/**
	 * Fetches all lecturers from the database.
	 * 
	 * @return ResultSet containing all lecturer data.
	 */
	public static ResultSet getLecturers() {
		ResultSet rs;
		rs = resultqueryFrom("SELECT l.id, l.firstName, l.lastName " + "FROM lecturers l  "

		);
		return rs;
	}

	/**
	 * Fetches all students from the database.
	 * 
	 * @return ResultSet containing all student data.
	 */
	public static ResultSet getStudents() {
		ResultSet rs;
		rs = resultqueryFrom("SELECT * FROM cems.students ");
		return rs;
	}

	/**
	 * Fetches all courses from the database.
	 * 
	 * @return ResultSet containing all course data.
	 */
	public static ResultSet getCourses() {
		ResultSet rs;
		rs = resultqueryFrom("SELECT * FROM cems.courses ");
		return rs;
	}

	/**
	 * Fetches all requests that have not yet been responded to.
	 * 
	 * @return ResultSet containing all non-responded request data.
	 */
	public static ResultSet getNotRespondedRequests() {
		ResultSet rs;
		rs = resultqueryFrom("SELECT DISTINCT requests.*" + "	FROM requests"
				+ "	JOIN lecturerincourse ON requests.lecturerId = lecturerincourse.lecturerId"
				+ " JOIN executedexams ON requests.examId = executedexams.examID"
				+ "	JOIN courses ON lecturerincourse.courseCode = lecturerincourse.courseCode"
				+ " JOIN fields ON fields.fieldCode = courses.fieldCode"
				+ " JOIN headofdepartment ON headofdepartment.fieldCode = courses.fieldCode"
				+ "	WHERE executedexams.status = \"active\" AND requests.requestStatus = \"NOT_RESPONDED_YET\";");

		return rs;
	}

	/**
	 * Updates exam time after approval of time change.
	 * 
	 * @param timeChangeInStr The time change as a String.
	 * @param examId          The ID of the exam to update.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean updateExamTimeAfterApproveOfTimeChange(String timeChangeInStr, String examId) {
		int timeChange = Integer.parseInt(timeChangeInStr);
		if (UpdateQueryReturnValue(
				"UPDATE executedexams SET timechange = " + timeChange + " WHERE examID =" + examId + ";")) {
			return true;
		} else
			return false;
	}

	/**
	 * Updates the status of a request to approved.
	 * 
	 * @param requestId     The ID of the request to update.
	 * @param requestStatus The new status for the request.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean updateRequestStatusApproved(String requestId, String requestStatus) {
		System.out.println(requestStatus.toString());
		if (UpdateQueryReturnValue("UPDATE requests SET requestStatus = " + "\"" + requestStatus + "\""
				+ " WHERE requestId = " + "'" + requestId + "'" + ";"))
			return true;
		else
			return false;

	}

	/**
	 * Updates the status of a request to rejected.
	 * 
	 * @param requestId     The ID of the request to update.
	 * @param requestStatus The new status for the request.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean updateRequestStatusRejected(String requestId, String requestStatus) {
		if (UpdateQueryReturnValue("UPDATE requests SET requestStatus = " + "\"" + requestStatus + "\""
				+ " WHERE requestId = " + "'" + requestId + "'" + ";"))
			return true;
		else
			return false;
	}

//	************************ Questions Methods *******************
	private static boolean isDeleted = true;

	/**
	 * Inserts a new question into the database.
	 * 
	 * @param id           - unique identifier of the question
	 * @param field        - field of study the question belongs to
	 * @param fieldName    - name of the field
	 * @param courses      - courses related to the question
	 * @param questionText - the question text
	 * @param lecturerName - name of the lecturer who added the question
	 * @param lecturerID   - unique identifier of the lecturer
	 * @param option1      - first option of the question
	 * @param option2      - second option of the question
	 * @param option3      - third option of the question
	 * @param option4      - fourth option of the question
	 * @param corretAnswer - the correct answer to the question
	 * @return message indicating the result of the operation
	 */
	public static String insertNewQuestion(String id, String field, String fieldName, String courses,
			String questionText, String lecturerName, String lecturerID, String option1, String option2, String option3,
			String option4, String corretAnswer) {
		UpdateQuery(
				"INSERT INTO questions (id, field, fieldname, courses, questionText, lecturerName, lecturerID, option1, option2, option3, option4, correctAnswer) VALUES ('"
						+ id + "','" + field + "','" + fieldName + "','" + courses + "','" + questionText + "','"
						+ lecturerName + "','" + lecturerID + "','" + option1 + "','" + option2 + "','" + option3
						+ "','" + option4 + "','" + corretAnswer + "') ;");
		fixIdAfterGettingQuestioNumber();
		String resMessage = "added";
		return resMessage;
	}

	/**
	 * Inserts a new question into an exam in the database.
	 * 
	 * @param questionId - unique identifier of the question
	 * @param points     - points assigned to the question in the exam
	 * @param examId     - unique identifier of the exam
	 */
	public static void insertNewQuestionInExam(String questionId, Double points, String examId) {
		UpdateQuery("INSERT INTO questioninexam (questionId, points, examId) VALUES ('" + questionId + "','" + points
				+ "','" + examId + "') ;");
	}

	/**
	 * Inserts a new exam into the database.
	 * 
	 * @param id                     - unique identifier of the exam
	 * @param duration               - duration of the exam in minutes
	 * @param descriptionForExaminee - description of the exam for the examinee
	 * @param descriptionForLecturer - description of the exam for the lecturer
	 * @param lecturerName           - name of the lecturer who created the exam
	 * @param locked                 - whether the exam is locked or not
	 * @param examType               - type of the exam
	 * @param lecturerId             - unique identifier of the lecturer
	 * @param course                 - course to which the exam belongs
	 * @param field                  - field of study the exam belongs to
	 * @return the final unique identifier of the exam
	 */
	public static String insertNewExam(String id, int duration, String descriptionForExaminee,
			String descriptionForLecturer, String lecturerName, Boolean locked, ExamType examType, String lecturerId,
			Course course, Field field) {
		UpdateQuery(
				"INSERT INTO exams (id, duration, descriptionForExaminee, descriptionForLecturer, authorName, locked, executionType, lecturerId, course, field) VALUES ('"
						+ id + "','" + duration + "','" + descriptionForExaminee + "','" + descriptionForLecturer
						+ "','" + lecturerName + "','" + 0 + "','" + examType.name() + "','" + lecturerId + "','"
						+ course.getCourseCode() + "','" + field.getFieldCode() + "') ;");
		String finalId = fixIdAfterGettingExamNumber();
		;
		return finalId;
	}

	/**
	 * Inserts an executed exam into the database.
	 * 
	 * @param examId - unique identifier of the exam
	 * @param date   - date and time the exam was executed
	 * @param code   - execution code of the exam
	 */
	public static void insertExecutedExam(String examId, LocalDateTime date, String code) {
		UpdateQuery("INSERT INTO executedexams (examID, executionDateTime, executionCode,  status) VALUES ('" + examId
				+ "','" + date + "','" + code + "','" + ExamStatus.locked.name() + "') ;");
	}

	/**
	 * Fixes the unique identifier of the exam after getting the exam number from
	 * the database.
	 * 
	 * @return the final unique identifier of the exam
	 */
	private static String fixIdAfterGettingExamNumber() {
		ResultSet rs = getQuery("SELECT * FROM exams WHERE id = 'temp'");
		String finalId = null;
		try {
			rs.next();
			int examNumber = rs.getInt("examNumber");
			String examField = rs.getString("field");
			String examCourse = rs.getString("course");
			String examNumberString = String.valueOf(examNumber);
			String lastExamNumber;
			if (examNumberString.length() == 1) {
				lastExamNumber = "0" + examNumberString;
			} else {
				lastExamNumber = examNumberString;
			}
			finalId = examField + examCourse + lastExamNumber;
			UpdateQuery("Update exams SET id = '" + finalId + "' WHERE id = 'temp'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finalId;
	}

	/**
	 * Fixes the unique identifier of the question after getting the question number
	 * from the database.
	 */
	private static void fixIdAfterGettingQuestioNumber() {
		ResultSet rs = getQuery("SELECT * FROM questions WHERE id = 'temp'");
		try {
			rs.next();
			int qNumber = rs.getInt("questionnumber");
			String finalId;
			String qField = rs.getString("field");
			String qNumberString = String.valueOf(qNumber);
			String lastQuestionNumber;
			if (qNumberString.length() == 1) {
				lastQuestionNumber = "00" + qNumberString;
			} else if (qNumberString.length() == 2) {
				lastQuestionNumber = "0" + qNumberString;
			} else {
				lastQuestionNumber = qNumberString;
			}
			finalId = qField + lastQuestionNumber;
			UpdateQuery("Update questions SET id = '" + finalId + "' WHERE id = 'temp'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a question from the database.
	 * 
	 * @param id - unique identifier of the question
	 */
	public static String deleteQuestion(String id) {
		UpdateQuery("DELETE FROM questions WHERE id = '" + id + "'");
		if (isDeleted) {
			return null;
		} else {
			String examsWithTheQuestion = "";
			isDeleted = true;
			String query = "SELECT * FROM questioninexam WHERE questionId = " + id;
			ResultSet rs = getQuery(query);
			try {
				while (rs.next()) {
					examsWithTheQuestion = examsWithTheQuestion + " " + rs.getString("examId");
				}
			} catch (SQLException e2) {
				examsWithTheQuestion = null;
				e2.printStackTrace();
			}
			return examsWithTheQuestion;
		}
	}

	/**
	 * Deletes an exam from the database.
	 * 
	 * @param id - unique identifier of the exam
	 */
	public static void deleteExam(String id) {
		UpdateQuery("DELETE FROM exams WHERE id = '" + id + "'");
	}

	/**
	 * Deletes questions in an exam from the database.
	 * 
	 * @param id - unique identifier of the exam
	 */
	public static void deleteQuestionsInExam(String id) {
		UpdateQuery("DELETE FROM questioninexam WHERE examId = '" + id + "'");
	}

	/**
	 * Updates a question in the database.
	 * 
	 * @param id           - unique identifier of the question
	 * @param questionText - updated question text
	 * @param option1      - updated first option of the question
	 * @param option2      - updated second option of the question
	 * @param option3      - updated third option of the question
	 * @param option4      - updated fourth option of the question
	 * @param corretAnswer - updated correct answer to the question
	 */
	public static void updateQuestion(String id, String questionText, String option1, String option2, String option3,
			String option4, String corretAnswer) {
		UpdateQuery("UPDATE questions SET questiontext = '" + questionText + "', option1 = '" + option1
				+ "', option2 = '" + option2 + "', option3 = '" + option3 + "', option4 = '" + option4
				+ "', correctanswer = '" + corretAnswer + "' WHERE id = '" + id + "'");
	}

	/**
	 * Updates the status of an exam in the 'executedexams' table to 'active'.
	 * 
	 * @param id the unique identifier of the exam
	 */
	public static void updateExamStatus(Exam exam) {
		UpdateQuery("UPDATE executedexams SET status = '" + ExamStatus.active.name() + "' WHERE examID = '"
				+ exam.getExamID() + "' AND serialNumber = '" + exam.getSerialNumber() + "'");
	}

//	***************** Login Methods **********************
	/**
	 * Perform query in form:
	 * 
	 * @param TableName
	 * @param col1      - the first where col
	 * @param col2      - the second where col
	 * @param par1      - (parameter1)
	 * @param par2      - (parameter2)
	 * @return {@link ResultSet} of query
	 */
	public static ResultSet selectTable2Where(String isRole, String col1, String col2, String par1, String par2) {
		ResultSet rs;
		rs = resultqueryFrom("SELECT * FROM cems.users WHERE " + col1 + " = '" + par1 + "' AND " + col2 + " = '" + par2
				+ "' AND " + isRole + " = " + "\"" + "TRUE" + "\"" + ";");
		return rs;
	}

	/**
	 * Updates the online status for a user who just logged in.
	 * 
	 * @param userId The ID of the user who just logged in.
	 */
	public static void updateOnlineStatusForLoggedUser(String userId) {
		UpdateQuery("UPDATE cems.users SET isLoggedIn = \'TRUE\' WHERE userID = " + '"' + userId + '"' + ';');
	}

	/**
	 * Updates the online status for a user who just logged out.
	 * 
	 * @param userId The ID of the user who just logged out.
	 */
	public static void updateOnlineStatusLogout(String userId) {
		UpdateQuery("UPDATE cems.users SET isLoggedIn = \'FALSE\' WHERE userID = " + '"' + userId + '"' + ';');
	}

	// *********************** Helpers Methods **********************
	/**
	 * This method performs an update query on the database. It takes a SQL update
	 * query string as parameter and executes it. It doesn't return any value.
	 * 
	 * @param query SQL update query
	 */
	private static void UpdateQuery(String query) {
		Connection connection = mysqlConnection.conn;
		Statement StatementOfResultSet;
		try {
			StatementOfResultSet = connection.createStatement();
			StatementOfResultSet.executeUpdate(query);
		} catch (SQLIntegrityConstraintViolationException e) {
			isDeleted = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method performs an update query and returns whether the operation was
	 * successful or not. It takes a SQL update query string as parameter and
	 * executes it. If the operation affects one or more rows, it returns true,
	 * otherwise false.
	 * 
	 * @param query SQL update query
	 * @return boolean indicating success of operation
	 */
	private static boolean UpdateQueryReturnValue(String query) {
		Connection connection = mysqlConnection.conn;
		Statement StatementOfResultSet;
		int rowsAffected; //
		System.out.println(query);
		try {
			StatementOfResultSet = connection.createStatement();
			rowsAffected = StatementOfResultSet.executeUpdate(query);
			if (rowsAffected > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method executes a SQL query and returns the ResultSet obtained from the
	 * execution. It takes a SQL query string as parameter and executes it.
	 * 
	 * @param query SQL query string
	 * @return ResultSet obtained from query execution
	 */
	private static ResultSet resultqueryFrom(String query) {
		Connection connection = mysqlConnection.conn;
		Statement StatementOfResultSet;
		ResultSet resultset;
		try {
			StatementOfResultSet = connection.createStatement();
			resultset = StatementOfResultSet.executeQuery(query);
			return resultset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method executes a SQL query and returns the ResultSet obtained from the
	 * execution. It takes a SQL query string as parameter and executes it.
	 * 
	 * @param sql SQL query string
	 * @return ResultSet obtained from query execution
	 */
	public static ResultSet getQuery(String sql) {
		PreparedStatement stmt = null;
		ResultSet rs;
		try {
			stmt = mysqlConnection.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method prints the ResultSet in a tabular form. It takes a ResultSet as
	 * parameter and prints it.
	 * 
	 * @param rs ResultSet to be printed
	 * @throws SQLException if a database access error occurs
	 */
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