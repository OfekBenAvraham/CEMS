package gui_controllers;

import entities.User;
import javafx.fxml.Initializable;

/**
 * Abstract controller class that all controller classes should extend.
 * This class serves as a blueprint for the controller classes, providing a method
 * for setting the current user, and forcing all subclasses to implement Initializable interface.
 */
public abstract class ControllerInterface implements Initializable {
	  
	/**
     * Abstract method for setting the user.
     * Any class that extends this abstract class must provide an implementation for this method.
     *
     * @param user the User object to be set.
     */
	public abstract void setUser(User user);

}
