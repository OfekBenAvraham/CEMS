package enums;

/**
 * Represents the status of a request in the system. This enumeration contains
 * three possible statuses: REJECTED, APPROVED, and NOT_RESPONDED_YET.
 */
public enum RequestStatus {
	REJECTED, APPROVED, NOT_RESPONDED_YET;

	/**
	 * Returns a string representation of the RequestStatus object.
	 *
	 * @return a string representation of the RequestStatus object.
	 * @throws IllegalArgumentException if the enum type does not exist.
	 */
	@Override
	public String toString() {
		switch (this) {
		case REJECTED:
			return "Rejected";
		case APPROVED:
			return "Approved";
		case NOT_RESPONDED_YET:
			return "Not Responded Yet";
		default:
			throw new IllegalArgumentException();
		}
	}

}
