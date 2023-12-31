package entities;

import java.io.Serializable;

import enums.Role;



/**
 * The HeadOfDepartment class represents a specific type of user in the system. 
 * A HeadOfDepartment has a specific role, as well as a unique field code. 
 * This class extends the User class and adds the unique attributes relevant to a head of a department.
 */
public class HeadOfDepartment extends User implements Serializable {

	/**
	 * Serial version UID for serialization and de-serialization.
	 */
	private static final long serialVersionUID = 5612860941061963316L;

	/**
	 * The field code associated with the head of the department.
	 */
	private String fieldCode;

	/**
	 * Constructor for creating a head of department with a specific role and personal information.
	 *
	 * @param role The role of the user.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @param firstName The first name of the user.
	 * @param lastName The last name of the user.
	 * @param id The id of the user.
	 * @param email The email of the user.
	 * @param phoneNumber The phone number of the user.
	 */
	@SuppressWarnings("static-access")
	public HeadOfDepartment(Role role, String username, String password, String firstName, String lastName, String id,
			String email, String phoneNumber) {
		super(role.HEAD_OF_DEPARTMENT, username, password, firstName, lastName, id, email, phoneNumber);
	}

	/**
	 * Default constructor for creating a head of department with no initial values.
	 */
	public HeadOfDepartment() {
		super();
	}
	
	/**
	 * Constructor for creating a head of department with a specific field code.
	 *
	 * @param fieldCode The field code of the head of department.
	 */
	public HeadOfDepartment(String fieldCode)
	{
		super();
		this.fieldCode=fieldCode;
	}
	
	/**
	 * Gets the field code of the head of department.
	 *
	 * @return The field code of the head of department.
	 */
	public String getFieldCode()
	{
		return this.fieldCode;
	}
	
	/**
	 * Sets the field code of the head of department.
	 *
	 * @param fieldCode The new field code of the head of department.
	 */
	public void setFieldCode(String fieldCode)
	{
		this.fieldCode = fieldCode;
	}
	
 
	
}
