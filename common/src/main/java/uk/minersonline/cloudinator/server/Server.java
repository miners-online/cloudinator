package uk.minersonline.cloudinator.server;

public class Server {
	private String name;
	private String address;
	private int port;
	private Object additionalInfo;

	public Server(String name, String address, int port, Object additionalInfo) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.additionalInfo = additionalInfo;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Object getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Object additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "Server{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				", port=" + port +
				", additionalInfo=" + additionalInfo +
				'}';
	}
}
