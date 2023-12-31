package entities;

public class UserToDB {
	private String firstName, lastName, userID, email, userName, password, phoneNumber, islecturer, isstudent,
			isLogginIn, isheadofdepartment;


    /**
     * Constructs a new UserToDB object with the specified user information.
     *
     * @param firstName           the first name of the user
     * @param lastName            the last name of the user
     * @param userID              the ID of the user
     * @param email               the email of the user
     * @param userName            the username of the user
     * @param password            the password of the user
     * @param phoneNumber         the phone number of the user
     * @param islecturer          the lecturer status of the user
     * @param isstudent           the student status of the user
     * @param isLogginIn          the login status of the user
     * @param isheadofdepartment  the head of department status of the user
     */
	public UserToDB(String firstName, String lastName, String userID, String email, String userName, String password,
			String phoneNumber, String islecturer, String isstudent, String isLogginIn, String isheadofdepartment) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.islecturer = islecturer;
		this.isstudent = isstudent;
		this.isLogginIn = isLogginIn;
		this.isheadofdepartment = isheadofdepartment;
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
     * Retrieves the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Retrieves the lecturer status of the user.
     *
     * @return the lecturer status
     */
    public String getIslecturer() {
        return islecturer;
    }

    /**
     * Sets the lecturer status of the user.
     *
     * @param islecturer the lecturer status to set
     */
    public void setIslecturer(String islecturer) {
        this.islecturer = islecturer;
    }

    /**
     * Retrieves the student status of the user.
     *
     * @return the student status
     */
    public String getIsstudent() {
        return isstudent;
    }

    /**
     * Sets the student status of the user.
     *
     * @param isstudent the student status to set
     */
    public void setIsstudent(String isstudent) {
        this.isstudent = isstudent;
    }

    /**
     * Retrieves the login status of the user.
     *
     * @return the login status
     */
    public String getIsLogginIn() {
        return isLogginIn;
    }

    /**
     * Sets the login status of the user.
     *
     * @param isLogginIn the login status to set
     */
    public void setIsLogginIn(String isLogginIn) {
        this.isLogginIn = isLogginIn;
    }

    /**
     * Retrieves the head of department status of the user.
     *
     * @return the head of department status
     */
    public String getIsheadofdepartment() {
        return isheadofdepartment;
    }

    /**
     * Sets the head of department status of the user.
     *
     * @param isheadofdepartment the head of department status to set
     */
    public void setIsheadofdepartment(String isheadofdepartment) {
        this.isheadofdepartment = isheadofdepartment;
    }
}

