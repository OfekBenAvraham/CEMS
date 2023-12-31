package entities;

/**
 * The Session class manages the current session for a user.
 * This class uses the singleton pattern to ensure only one session is active at a time.
 */
public class Session {
	

    private static Session instance;

 
    private User currentUser;

    /**
     * Private constructor to restrict the instantiation of the Session class from other classes.
     */
    private Session() {}

    /**
     * Creates a singleton Session instance.
     *
     * @return the single instance of Session
     */
    public static Session getInstance() {
        if(instance == null) {
            instance = new Session();
        }
        return instance;
    }
    /**
     * Gets the current user of this session.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     * Sets the current user of this session.
     *
     * @param currentUser the user to set as the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    /**
     * Log out the current user by setting the current user to null.
     */
    public void logout() {
        currentUser = null;
    }
}