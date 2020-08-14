package tsg.luckpermslock.Utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Utils {

	public String getPlayerName(UUID id) {
		if (Bukkit.getPlayer(id) != null) {
			return Bukkit.getPlayer(id).getName();
		}
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getUniqueId().equals(id)) {
				return player.getName();
			}
		}
		return null;
	}

	public UUID getPlayerUUID(String name) {
		if (Bukkit.getPlayer(name) != null) {
			return Bukkit.getPlayer(name).getUniqueId();
		}
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equals(name)) {
				return player.getUniqueId();
			}
		}
		return null;
	}
}
