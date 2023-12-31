package entities;

import enums.ClientConnectionStatus;

public class Client {

	// Instance variables **********************************************

	private String ip;
	private String hostName;
	private ClientConnectionStatus status;

	// Constructors ****************************************************

	/**
	 * @param ip
	 * @param hostName
	 * @param status
	 */
	public Client(String ip, String hostName, ClientConnectionStatus status) {
		this.ip = ip;
		this.hostName = hostName;
		this.status = status;
	}

	// Instance methods ************************************************

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip The ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName The hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the status
	 */
	public ClientConnectionStatus getStatus() {
		return status;
	}

	/**
	 * @param status The status to set
	 */
	public void setStatus(ClientConnectionStatus status) {
		this.status = status;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Client other = (Client) obj;

		return ip.equals(other.ip) && hostName.equals(other.hostName) && status == other.status;
	}
}
