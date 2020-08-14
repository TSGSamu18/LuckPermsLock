package tsg.luckpermslock.lock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import tsg.luckpermslock.lock.events.EntityEvents;
import tsg.luckpermslock.lock.events.PlayerEvents;
import tsg.luckpermslock.main.LuckPermsLock;

public class LockManager {

	public String password;
	boolean hidePassword;

	public int attempts;
	public int messageTaskDelay;
	public int kickTaskDelay;

	Set<String> commands;
	public Set<String> permissions;
	public HashMap<UUID, Integer> blockedPlayer;

	private void fillPermissions() {
		try {
			permissions = new HashSet<String>(LuckPermsLock.instance.config.getStringList("Lock.Permissions"));
		} catch (NullPointerException e) {
			LuckPermsLock.instance.log.severe("Lock.Permissions list not found");
		}
	}

	public void setListeners() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(LuckPermsLock.instance, ListenerPriority.HIGHEST, PacketType.Play.Client.CHAT) {
					@Override
					public void onPacketReceiving(PacketEvent e) {
						UUID id = e.getPlayer().getUniqueId();
						if (blockedPlayer.containsKey(id)) {
							e.setCancelled(true);
							if (!(e.getPacket().getStrings().read(0).equals(password))) {
								String name = Bukkit.getPlayer(id).getName();
								if (blockedPlayer.get(id) == attempts) {
									for (String c : commands) {
										Bukkit.getScheduler().callSyncMethod(LuckPermsLock.instance,
												() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
														c.replaceAll("%player%", name).replaceAll("&", "§")));
									}
									return;
								}
								blockedPlayer.put(id, blockedPlayer.get(id) + 1);
								e.getPlayer().sendMessage(LuckPermsLock.instance.messages.WRONG_PASSWORD
										.replaceAll("%attempts%", String.valueOf(attempts - blockedPlayer.get(id)) + 1));
							} else {
								blockedPlayer.remove(id);
								e.getPlayer().sendMessage(LuckPermsLock.instance.messages.LOGIN_SUCCESSFUL);
							}
						}
					}
				});
		PluginManager manager = LuckPermsLock.instance.getServer().getPluginManager();
		manager.registerEvents(new PlayerEvents(), LuckPermsLock.instance);
		manager.registerEvents(new EntityEvents(), LuckPermsLock.instance);
	}

	public void setup() {
		password = LuckPermsLock.instance.config.getString("Lock.Password");
		hidePassword = LuckPermsLock.instance.config.getBoolean("Lock.HidePassword");
		if (hidePassword) {
			LuckPermsLock.instance.config.set("Lock.Password", "");
			LuckPermsLock.instance.saveConfig();
		}
		attempts = LuckPermsLock.instance.config.getInt("Lock.Attempts");
		messageTaskDelay = LuckPermsLock.instance.config.getInt("Lock.Message-delay");
		kickTaskDelay = LuckPermsLock.instance.config.getInt("Lock.Kick-task");
		blockedPlayer = new HashMap<UUID, Integer>();
		commands = new HashSet<String>(LuckPermsLock.instance.config.getStringList("Lock.Commands"));
		fillPermissions();
		setListeners();
	}
}
