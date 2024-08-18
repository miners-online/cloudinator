package uk.minersonline.cloudinator.server;

import java.util.*;

public abstract class ServerManager {
	private final Map<String, List<Server>> servers = new HashMap<>();

	public void addServer(Server server, String groupName) {
		List<Server> group = findGroupByName(groupName);
		group.add(server);
	}

	public void removeServer(Server server) {
		// Iterate over each group in the map
		for (Map.Entry<String, List<Server>> entry : servers.entrySet()) {
			// Try to remove the server from the group
			if (entry.getValue().remove(server)) {
				// If the server was removed and the group is now empty, remove the group
				if (entry.getValue().isEmpty()) {
					servers.remove(entry.getKey());
				}
				// Server found and removed, exit the method
				return;
			}
		}
	}

	public List<Server> getServers() {
		// Create a list to hold all servers
		List<Server> allServers = new ArrayList<>();
		// Iterate over each group and add all servers to the list
		for (List<Server> group : servers.values()) {
			allServers.addAll(group);
		}
		return Collections.unmodifiableList(allServers);
	}

	public List<String> getGroups() {
		return List.copyOf(servers.keySet());
	}

	public List<Server> findGroupByName(String groupName) {
		if (!servers.containsKey(groupName)) {
			servers.put(groupName, new ArrayList<>());
		}
		return servers.get(groupName);
	}

	@Override
	public String toString() {
		return "ServerManager{" +
				"servers=" + servers +
				'}';
	}
}
