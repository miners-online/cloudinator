package uk.minersonline.cloudinator.minestom;


import net.ME1312.SubData.Client.SubDataClient;
import net.ME1312.SubData.Client.SubDataProtocol;
import net.minestom.server.MinecraftServer;
import uk.minersonline.cloudinator.messages.AddServerMessageOut;

import java.io.IOException;
import java.net.InetAddress;


public class Cloudinator {
	public static void init(String ipAddress, int port, String severName, String serverGroup, String proxyAddress) {
		SubDataProtocol protocol = new SubDataProtocol();
		protocol.registerMessage("cloudinator", "add_server", AddServerMessageOut.class);

		SubDataClient client;
		try {
			client = protocol.open(null, InetAddress.getByName(proxyAddress), 2048, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		client.sendMessage(new AddServerMessageOut(ipAddress, port, severName, serverGroup));


		MinecraftServer.getSchedulerManager().buildShutdownTask(client::close);
	}
}