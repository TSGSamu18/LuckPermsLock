package tsg.luckpermslock.check;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.luckperms.api.event.user.UserLoadEvent;
import tsg.luckpermslock.main.LuckPermsLock;

public class CheckManager {

	boolean alert;
	String alertPermission;
	Set<String> commands;

	Set<String> permissions;
	public Set<UUID> allowedPlayer;

	public void fillPermissions() {
		try {
			permissions = new HashSet<String>(LuckPermsLock.instance.config.getStringList("Check.Permissions"));
		} catch (NullPointerException e) {
			LuckPermsLock.instance.log.severe("Check.Permissions list not found");
		}
	}

	public void onUserLoad(UserLoadEvent event) {
		Bukkit.getScheduler().runTask(LuckPermsLock.instance, () -> {
			Player player = Bukkit.getPlayer(event.getUser().getUniqueId());
			try {
				for (String permission : permissions) {
					if (player.hasPermission(permission)) {
						if (allowedPlayer.contains(player.getUniqueId())) {
							return;
						}
						if (alert) {
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								if (p1.hasPermission(alertPermission)) {
									p1.sendMessage(LuckPermsLock.instance.messages.ALERT
											.replaceAll("%player%", player.getName())
											.replaceAll("%permission%", permission));
								}
							}
						}
						for (String c : commands) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("%player%", player.getName())
									.replaceAll("%permission%", permission).replaceAll("&", "§"));
						}
						break;
					}
				}
			} catch (NullPointerException ignored) {
			}
		});
	}

	public void setup() {
		alert = LuckPermsLock.instance.config.getBoolean("Check.Alert");
		alertPermission = LuckPermsLock.instance.config.getString("Check.Alert-permission");
		commands = new HashSet<String>(LuckPermsLock.instance.config.getStringList("Check.Commands"));
		fillPermissions();
	}
	
	public void loadList() {
		LuckPermsLock.instance.checkManager.allowedPlayer = new HashSet<UUID>();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(LuckPermsLock.instance.fileManager.list);
		for (String s : config.getStringList("Allowed")) {
			LuckPermsLock.instance.checkManager.allowedPlayer.add(UUID.fromString(s));
		}
	}

	public void saveList() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(LuckPermsLock.instance.fileManager.list);
		for (UUID id : LuckPermsLock.instance.checkManager.allowedPlayer) {
			config.set("Allowed", null);
			config.set("Allowed", new HashSet<String>());
			config.getStringList("Allowed").add(id.toString());
		}
		try {
			config.save(LuckPermsLock.instance.fileManager.list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
