package uk.minersonline.cloudinator.messages;

import net.ME1312.Galaxi.Library.Map.ObjectMap;
import net.ME1312.SubData.Server.Protocol.MessageObjectIn;
import net.ME1312.SubData.Server.DataClient;

public abstract class AbstractAddServerMessageIn implements MessageObjectIn<String> {
	private String address;
	private int port;
	private String serverName;
	private String serverGroup;

	@Override
	public final void receive(DataClient client, ObjectMap<String> data) {
		address = data.getString("address");
		port = data.getInt("port");
		serverName = data.getString("serverName");
		serverGroup = data.getString("serverGroup");
		onReceive(client);
	}

	public abstract void onReceive(DataClient client);

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerGroup() {
		return serverGroup;
	}
}
