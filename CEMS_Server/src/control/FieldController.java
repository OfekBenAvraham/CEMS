package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.Course;
import entities.Field;
import entities.Message;
/**
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */

public class FieldController {
	/**
	 * This method retrieves all fields from the database.
	 *
	 * @return A list of Field objects.
	 * 
	 */
	public static ArrayList<Field> getAllFields() {
		String query = "SELECT * FROM fields";
		ResultSet rs = Query.getQuery(query);
		ArrayList<Field> fields = new ArrayList<>();
		try {
			while (rs.next()) {
				Field field = new Field(rs.getString("fieldName"), rs.getString("fieldCode"));
				fields.add(field);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fields;
	}

	/**
	 * This method retrieves all courses associated with a specific field from the
	 * database.
	 *
	 * @param message Message object containing field code.
	 * @return A list of Course objects associated with the specified field.
	 */
	public static ArrayList<Course> getCoursesByField(Message message) {
		ArrayList<String> data = (ArrayList<String>) message.getObj();
		String lecturerId = data.get(0);
		String fieldCode = data.get(1);
		String query = "SELECT c.* " + "FROM courses c " + "JOIN lecturerincourse lic ON c.courseCode = lic.courseCode "
				+ "WHERE c.fieldCode = '" + fieldCode +  "' AND lic.lecturerId = '" + lecturerId + "';";
		ResultSet rs = Query.getQuery(query);
		ArrayList<Course> courses = new ArrayList<>();
		try {
			while (rs.next()) {
				Course course = new Course(rs.getString("courseName"), rs.getString("courseCode"),
						rs.getString("fieldCode"));
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
}
