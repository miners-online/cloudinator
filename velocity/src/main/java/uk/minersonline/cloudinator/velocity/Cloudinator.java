package uk.minersonline.cloudinator.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.ME1312.SubData.Server.DataClient;
import net.ME1312.SubData.Server.SubDataProtocol;
import net.ME1312.SubData.Server.SubDataServer;
import org.slf4j.Logger;
import uk.minersonline.cloudinator.server.Server;
import uk.minersonline.cloudinator.server.ServerManager;

import java.io.IOException;

@Plugin(
		id = "cloudinator",
		name = "Cloudinator",
		version = "1.0-SNAPSHOT",
		description = "A Minecraft cloud server sync and creation system. ",
		url = "https://github.com/miners-online/cloudinator",
		authors = {"ajh123"}
)
public class Cloudinator {

	@Inject
	private Logger logger;

	@Inject
	private ProxyServer proxy;

	private ServerManager serverManager;

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		this.serverManager = new VelocityServerManager(this.proxy);
		SubDataProtocol protocol = new SubDataProtocol();
		protocol.registerMessage("cloudinator", "add_server", new AddServerMessageIn(this.serverManager));

		java.util.logging.Logger logger1 = java.util.logging.Logger.getLogger("Cloudinator");
		try {
			SubDataServer server = protocol.open(logger1, null, 2048, null);
			server.whitelist("10.0.0.109");
			server.whitelist("10.0.0.160");
			server.whitelist("10.0.0.12");
			server.on.connect(this::onClientConnect);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean onClientConnect(DataClient client) {
		client.on.close(this::onClientClosed);
		return true;
	}

	private boolean onClientClosed(DataClient client) {
		for (Server server : serverManager.getServers()) {
			if (server.getAdditionalInfo() == client) {
				serverManager.removeServer(server);
			}
		}
		return true;
	}
}
