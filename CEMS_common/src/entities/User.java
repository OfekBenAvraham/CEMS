package entities;

import java.util.Objects;
import java.io.Serializable;
import enums.Role;

/**
 * The User class represents a user in the system.
 * It implements the Serializable interface to support serialization.
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = -1698509248999662092L;
	private Role role; 
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	private String userID;
	protected String email;
	protected String phoneNumber;
	private String isLoggedIn;
	

	  /**
     * Constructs a new User object with the specified role, username, password, first name, last name,
     * user ID, email, and phone number.
     *
     * @param role       the role of the user
     * @param username   the username of the user
     * @param password   the password of the user
     * @param firstName  the first name of the user
     * @param lastName   the last name of the user
     * @param userID     the ID of the user
     * @param email      the email of the user
     * @param phoneNumber the phone number of the user
     */
	public User(Role role, String username, String password, String firstName, String lastName, String userID,
			String email, String phoneNumber) {
		this.role = role;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	  /**
     * Constructs a new empty User object.
     */
	public User() {
	}
	
	 /**
     * Constructs a new User object with the specified user ID, first name, and last name.
     *
     * @param userID    the ID of the user
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     */
	public User(String userID,String firstName, String lastName)
	{
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	  /**
     * Retrieves the role of the user.
     *
     * @return the user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the user's role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the full name of the user.
     *
     * @return the full name
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return the user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the ID of the user.
     *
     * @param userID the user ID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the email of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the phone number of the user.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the login status of the user.
     *
     * @return the login status
     */
    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Sets the login status of the user.
     *
     * @param isLoggedIn the login status to set
     */
    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * Generates the hash code for the User object based on its properties.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, password, phoneNumber, role, userID, username);
    }

    /**
     * Determines whether the current User object is equal to the specified object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(phoneNumber, other.phoneNumber) && role == other.role
				&& Objects.equals(userID, other.userID) && Objects.equals(username, other.username);
	} 

}
