package entities;

import java.io.Serializable;

import enums.Answers;
import enums.Operations;

/**
 * Class Message represents an instance of a message that can be exchanged
 * between server and client. The message contains information about the
 * operation, data, and answer.
 *
 * @author Paz Fayer
 * @author Maayan Avittan
 */
public class Message implements Serializable  {
	/**
	 *  giving serial number
	 */
	private static final long serialVersionUID = 6475598174552396305L;
	/**
	 * 
	 */
	private Operations operation;
	private Answers answer;
	private Object obj;

	/**
	 * Constructs a new Message object with the specified operation.
	 *
	 * @param operation the operation associated with the message
	 */
	public Message(Operations operation) {
		super();
		this.operation = operation;
	}

	/**
	 * Constructs a new Message object with the specified operation and data.
	 *
	 * @param operation the operation associated with the message
	 * @param obj       the data associated with the message
	 */
	public Message(Operations operation, Object obj) {
		super();
		this.operation = operation;
		this.obj = obj;
	}

	/**
	 * Constructs a new Message object with the specified operation and answer.
	 *
	 * @param operation the operation associated with the message
	 * @param answer    the answer associated with the message
	 */
	public Message(Operations operation, Answers answer) {
		super();
		this.operation = operation;
		this.answer = answer;
	}

	/**
	 * Constructs a new Message object with the specified operation, answer, and
	 * data.
	 *
	 * @param operation the operation associated with the message
	 * @param answer    the answer associated with the message
	 * @param obj       the data associated with the message
	 */
	public Message(Operations operation, Answers answer, Object obj) {
		super();
		this.operation = operation;
		this.answer = answer;
		this.obj = obj;
	}

	/**
	 * Returns the operation associated with the message.
	 *
	 * @return the operation associated with the message
	 */
	public Operations getOperation() {
		return operation;
	}

	/**
	 * Sets the operation associated with the message.
	 *
	 * @param operation the operation to be set
	 */
	public void setOperation(Operations operation) {
		this.operation = operation;
	}

	/**
	 * Returns the answer associated with the message.
	 *
	 * @return the answer associated with the message
	 */
	public Answers getAnswer() {
		return answer;
	}

	/**
	 * Sets the answer associated with the message.
	 *
	 * @param answer the answer to be set
	 */
	public void setAnswer(Answers answer) {
		this.answer = answer;
	}

	/**
	 * Returns the data associated with the message.
	 *
	 * @return the data associated with the message
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * Sets the data associated with the message.
	 *
	 * @param obj the data to be set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * Returns a string representation of the Message object.
	 *
	 * @return a string representation of the Message object
	 */
	@Override
	public String toString() {
		return "Message [operation=" + operation + ", answer=" + answer + ", obj=" + obj + "]";
	}
}
