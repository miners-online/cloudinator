package uk.minersonline.cloudinator.messages;

import net.ME1312.SubData.Client.DataSender;
import net.ME1312.SubData.Client.Protocol.MessageObjectOut;
import net.ME1312.Galaxi.Library.Map.ObjectMap;

public class AddServerMessageOut implements MessageObjectOut<String> {
	private final ObjectMap<String> data = new ObjectMap<>();

	public AddServerMessageOut(String address, int port, String serverName, String serverGroup) {
		data.set("address", address);
		data.set("port", port);
		data.set("serverName", serverName);
		data.set("serverGroup", serverGroup);
	}

	@Override
	public ObjectMap<String> send(DataSender sender) {
		return data;
	}
}
