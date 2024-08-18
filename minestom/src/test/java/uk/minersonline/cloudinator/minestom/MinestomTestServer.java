package uk.minersonline.cloudinator.minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

public class MinestomTestServer {
	public static void main(String[] args) {
		MinecraftServer server = MinecraftServer.init();

		DimensionType fullbright = DimensionType.builder().ambientLight(1.0f).respawnAnchorWorks(true).build();
		DynamicRegistry.Key<DimensionType> fullbrightKey =
				MinecraftServer.getDimensionTypeRegistry().register(NamespaceID.from("idk"), fullbright);

		Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer(fullbrightKey);
		instance.setGenerator(new DemoGenerator());
		instance.enableAutoChunkLoad(true);

		Pos spawn = new Pos(0, 60, 0);
		MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent.class, event -> {
			event.setSpawningInstance(instance);
			event.getPlayer().setRespawnPoint(spawn);
		});

		Cloudinator.init("10.0.0.160", 25565, "Lobby1", "lobby", "10.0.0.109");
		server.start("0.0.0.0", 25565);
	}
}
