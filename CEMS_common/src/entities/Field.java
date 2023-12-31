package entities;

import java.io.Serializable;

/**
 * This class represents a Field of study in the system.
 */
public class Field implements Serializable {
	private static final long serialVersionUID = -624777505144900922L;
	private String fieldName;
	private String fieldCode;

	/**
     * Initializes a new Field object with the specified name and code.
     *
     * @param fieldName the name of the field
     * @param fieldCode the code of the field
     */
	public Field(String fieldName, String fieldCode) {
		this.fieldName = fieldName;
		this.fieldCode = fieldCode;
	}

	 /**
     * Returns the name of the field.
     *
     * @return the name of the field
     */
	public String getFieldName() {
		return fieldName;
	}

	/**
     * Sets the name of the field.
     *
     * @param fieldName the name to set
     */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
     * Returns the code of the field.
     *
     * @return the code of the field
     */
	public String getFieldCode() {
		return fieldCode;
	}

    /**
     * Sets the code of the field.
     *
     * @param fieldCode the code to set
     */
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
}
