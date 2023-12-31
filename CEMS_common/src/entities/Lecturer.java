package entities;

import java.io.Serializable;

import enums.Role;


/**
 * The Lecturer class represents a specific type of user in the system. 
 * A Lecturer has a specific field of expertise. 
 * This class extends the User class and adds the unique attributes relevant to a lecturer.
 */
public class Lecturer extends User implements Serializable {

	private static final long serialVersionUID = 8774058927467205608L;
	private String field;
	
	/**
	 * Constructs a new Lecturer object by providing details of the lecturer.
	 *
	 * @param role        the role of the user.
	 * @param username    the username of the lecturer.
	 * @param password    the password of the lecturer.
	 * @param firstName   the first name of the lecturer.
	 * @param lastName    the last name of the lecturer.
	 * @param id          the ID of the lecturer.
	 * @param email       the email of the lecturer.
	 * @param phoneNumber the phone number of the lecturer.
	 */
	// **** CONSTRUCTORS ****//
	@SuppressWarnings("static-access")
	public Lecturer(Role role, String username, String password, String firstName, String lastName, String id,
			String email, String phoneNumber) {
		super(role.LECTURER, username, password, firstName, lastName, id, email, phoneNumber);
	}
	
	/**
	 * Constructs a new Lecturer object by providing minimal details.
	 *
	 * @param lecturerId the ID of the lecturer.
	 * @param firstName  the first name of the lecturer.
	 * @param lastName   the last name of the lecturer.
	 * @param field      the field of expertise of the lecturer.
	 */
	public Lecturer(String lecturerId,String firstName,String lastName, String field)
	{
		super(lecturerId,firstName,lastName);
		this.field = field;
	}
 
	/**
	 * Default constructor for the Lecturer class.
	 */
	public Lecturer() {
		super();
	}

	public Lecturer(String id, String firstName, String lastName) {
		super(id,firstName,lastName);
	}

	// Getters
	/**
	 * @return the username of the lecturer.
	 */
	public String getLecturerUserName() {
		return super.getUsername();
	}

	/**
	 * @return the first name of the lecturer.
	 */
	public String getFirstName() {
		return super.getFirstName();
	}

	/**
	 * @return the last name of the lecturer.
	 */
	public String getLastName() {
		return super.getLastName();
	}

	/**
	 * @return the full name of the lecturer.
	 */
	public String getLecturerFullName() {
		return super.getFirstName() + " " + super.getLastName();
	}

	/**
	 * @return the ID of the lecturer.
	 */
	public String getLecturerId() {
		return super.getUserID();
	}

	/**
	 * @return the email of the lecturer.
	 */
	public String getEmail() {
		return super.getEmail();
	}

	/**
	 * @return the password of the lecturer.
	 */
	public String getPassword() {
		return super.getPassword();
	}

	/**
	 * @return the phone number of the lecturer.
	 */
	public String getPhoneNumber() {
		return super.getPhoneNumber();
	}

	/**
	 * Returns a string representation of the Lecturer object.
	 *
	 * @return a string representation of the Lecturer object.
	 */
	@Override
	public String toString() {
		return "Lecturer [field=" + field + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
}
