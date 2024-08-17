package uk.minersonline.cloudinator.velocity;

import net.ME1312.SubData.Server.DataClient;
import uk.minersonline.cloudinator.messages.AbstractAddServerMessageIn;
import uk.minersonline.cloudinator.server.Server;
import uk.minersonline.cloudinator.server.ServerManager;


public class AddServerMessageIn extends AbstractAddServerMessageIn {
	private final ServerManager serverManager;

	public AddServerMessageIn(ServerManager serverManager) {
		this.serverManager = serverManager;
	}

	@Override
	public void onReceive(DataClient client) {
		this.serverManager.addServer(
				new Server(this.getServerName(), this.getAddress(), this.getPort(), client),
				this.getServerGroup()
		);
	}
}
