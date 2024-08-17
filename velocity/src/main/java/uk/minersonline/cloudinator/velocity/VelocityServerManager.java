package uk.minersonline.cloudinator.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import uk.minersonline.cloudinator.server.Server;
import uk.minersonline.cloudinator.server.ServerManager;

import java.net.InetSocketAddress;

public class VelocityServerManager extends ServerManager {
	private final ProxyServer proxyServer;

	public VelocityServerManager(ProxyServer proxyServer) {
		this.proxyServer = proxyServer;
	}

	@Override
	public void addServer(Server server, String groupName) {
		super.addServer(server, groupName);
		proxyServer.registerServer(new ServerInfo(
			server.getName(), InetSocketAddress.createUnresolved(server.getAddress(), server.getPort())
		));
	}

	@Override
	public void removeServer(Server server) {
		super.removeServer(server);
		proxyServer.unregisterServer(new ServerInfo(
			server.getName(), InetSocketAddress.createUnresolved(server.getAddress(), server.getPort())
		));
	}
}
