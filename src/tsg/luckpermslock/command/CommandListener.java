package tsg.luckpermslock.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tsg.luckpermslock.main.LuckPermsLock;

public class CommandListener extends Command {

	List<String> commandAliases;

	boolean blockAddFromGame;

	public CommandListener(String name) {
		super(name);
		commandAliases = new ArrayList<String>();
		fillCommandAliases();
		this.description = "LuckPermsLock command!";
		this.setAliases(commandAliases);
		blockAddFromGame = LuckPermsLock.instance.config.getBoolean("Command.AddCommandOnlyConsole");
	}

	@Override
	public boolean execute(CommandSender se, String s, String[] ar) {
		if (!(se instanceof Player)) {
			if (ar.length == 0) {
				for (String h : LuckPermsLock.instance.messages.HELP) {
					se.sendMessage(
							h.replaceAll("&", "§").replaceAll("%prefix%", LuckPermsLock.instance.messages.PREFIX));
				}
			} else if (ar.length == 1) {
				if (ar[0].equalsIgnoreCase("help")) {
					for (String h : LuckPermsLock.instance.messages.HELP) {
						se.sendMessage(
								h.replaceAll("&", "§").replaceAll("%prefix%", LuckPermsLock.instance.messages.PREFIX));
					}
				} else if (ar[0].equalsIgnoreCase("list")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						se.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					se.sendMessage(LuckPermsLock.instance.messages.LIST);
					for (UUID id : LuckPermsLock.instance.checkManager.allowedPlayer) {
						se.sendMessage(LuckPermsLock.instance.messages.LIST_FORMAT.replaceAll("%player%",
								LuckPermsLock.instance.utils.getPlayerName(id)));
					}
				} else if (ar[0].equalsIgnoreCase("reload")) {
					LuckPermsLock.instance.reload();
					se.sendMessage(LuckPermsLock.instance.messages.ON_RELOAD);
				} else if (ar[0].equalsIgnoreCase("disable")) {
					LuckPermsLock.instance.hookManager.unhook();
					se.sendMessage(LuckPermsLock.instance.messages.PLUGIN_DISABLED);
				} else if (ar[0].equalsIgnoreCase("enable")) {
					LuckPermsLock.instance.hookManager.hook();
					se.sendMessage(LuckPermsLock.instance.messages.PLUGIN_ENABLED);
				} else {
					se.sendMessage(LuckPermsLock.instance.messages.INCORRECT_USAGE);
				}
			} else if (ar.length == 2) {
				if (ar[0].equalsIgnoreCase("add")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						se.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					try {
						se.sendMessage((LuckPermsLock.instance.checkManager.allowedPlayer
								.add(LuckPermsLock.instance.utils.getPlayerUUID(ar[1])))
										? LuckPermsLock.instance.messages.ON_PLAYER_ADD.replaceAll("%player%", ar[1])
										: LuckPermsLock.instance.messages.PLAYER_ALREADY_IN_LIST.replaceAll("%player%",
												ar[1]));
					} catch (NullPointerException e) {
						se.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else if (ar[0].equalsIgnoreCase("remove")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						se.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					try {
						se.sendMessage((LuckPermsLock.instance.checkManager.allowedPlayer
								.remove(LuckPermsLock.instance.utils.getPlayerUUID(ar[1])))
										? LuckPermsLock.instance.messages.ON_PLAYER_REMOVE.replaceAll("%player%", ar[1])
										: LuckPermsLock.instance.messages.PLAYER_NOT_FOUND_IN_LIST
												.replaceAll("%player%", ar[1]));
					} catch (NullPointerException e) {
						se.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else if (ar[0].equalsIgnoreCase("login")) {
					if (!LuckPermsLock.instance.hookManager.lockEnabled) {
						se.sendMessage(LuckPermsLock.instance.messages.LOCK_HOOK_DISABLED);
						return true;
					}
					try {
						LuckPermsLock.instance.lockManager.blockedPlayer
								.remove(LuckPermsLock.instance.utils.getPlayerUUID(ar[1]));
						Bukkit.getPlayer(LuckPermsLock.instance.utils.getPlayerUUID(ar[1]))
								.sendMessage(LuckPermsLock.instance.messages.LOGIN_SUCCESSFUL);
					} catch (NullPointerException e) {
						se.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else {
					se.sendMessage(LuckPermsLock.instance.messages.INCORRECT_USAGE);
				}
			}
		} else {
			Player p = (Player) se;
			if (ar.length == 0) {
				if (!p.hasPermission("luckpermslock.help")) {
					p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
					return true;
				}
				for (String h : LuckPermsLock.instance.messages.HELP) {
					p.sendMessage(
							h.replaceAll("&", "§").replaceAll("%prefix%", LuckPermsLock.instance.messages.PREFIX));
				}
			} else if (ar.length == 1) {
				if (ar[0].equalsIgnoreCase("help")) {
					if (!p.hasPermission("luckpermslock.help")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					for (String h : LuckPermsLock.instance.messages.HELP) {
						p.sendMessage(
								h.replaceAll("&", "§").replaceAll("%prefix%", LuckPermsLock.instance.messages.PREFIX));
					}
				} else if (ar[0].equalsIgnoreCase("list")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						p.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					if (!p.hasPermission("luckpermslock.list")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					p.sendMessage(LuckPermsLock.instance.messages.LIST);
					for (UUID id : LuckPermsLock.instance.checkManager.allowedPlayer) {
						p.sendMessage(LuckPermsLock.instance.messages.LIST_FORMAT.replaceAll("%player%",
								LuckPermsLock.instance.utils.getPlayerName(id)));
					}
				} else if (ar[0].equalsIgnoreCase("reload")) {
					if (!p.hasPermission("luckpermslock.reload")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					LuckPermsLock.instance.reload();
					p.sendMessage(LuckPermsLock.instance.messages.ON_RELOAD);
				} else if (ar[0].equalsIgnoreCase("disable")) {
					if (!p.hasPermission("luckpermslock.disable")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					LuckPermsLock.instance.hookManager.unhook();
					p.sendMessage(LuckPermsLock.instance.messages.PLUGIN_DISABLED);
				} else if (ar[0].equalsIgnoreCase("enable")) {
					if (!p.hasPermission("luckpermslock.enable")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					LuckPermsLock.instance.hookManager.hook();
					p.sendMessage(LuckPermsLock.instance.messages.PLUGIN_ENABLED);
				} else {
					p.sendMessage(LuckPermsLock.instance.messages.INCORRECT_USAGE);
				}
			} else if (ar.length == 2) {
				if (ar[0].equalsIgnoreCase("add")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						p.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					if (blockAddFromGame) {
						p.sendMessage(LuckPermsLock.instance.messages.ADD_BLOCKED_IN_GAME);
						return true;
					}
					if (!p.hasPermission("luckpermslock.add")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					try {
						p.sendMessage((LuckPermsLock.instance.checkManager.allowedPlayer
								.add(LuckPermsLock.instance.utils.getPlayerUUID(ar[1])))
										? LuckPermsLock.instance.messages.ON_PLAYER_ADD.replaceAll("%player%", ar[1])
										: LuckPermsLock.instance.messages.PLAYER_ALREADY_IN_LIST.replaceAll("%player%",
												ar[1]));
					} catch (NullPointerException e) {
						p.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else if (ar[0].equalsIgnoreCase("remove")) {
					if(!LuckPermsLock.instance.hookManager.checkEnabled) {
						p.sendMessage(LuckPermsLock.instance.messages.CHECKHOOK_DISABLED);
						return true;
					}
					if (!p.hasPermission("luckpermslock.remove")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					try {
						p.sendMessage((LuckPermsLock.instance.checkManager.allowedPlayer
								.remove(LuckPermsLock.instance.utils.getPlayerUUID(ar[1])))
										? LuckPermsLock.instance.messages.ON_PLAYER_REMOVE.replaceAll("%player%", ar[1])
										: LuckPermsLock.instance.messages.PLAYER_NOT_FOUND_IN_LIST
												.replaceAll("%player%", ar[1]));
					} catch (NullPointerException e) {
						p.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else if (ar[0].equalsIgnoreCase("login")) {
					if (!p.hasPermission("luckpermslock.login")) {
						p.sendMessage(LuckPermsLock.instance.messages.NO_PERMISSION);
						return true;
					}
					if (!LuckPermsLock.instance.hookManager.lockEnabled) {
						p.sendMessage(LuckPermsLock.instance.messages.LOCK_HOOK_DISABLED);
						return true;
					}
					try {
						LuckPermsLock.instance.lockManager.blockedPlayer
								.remove(LuckPermsLock.instance.utils.getPlayerUUID(ar[1]));
						Bukkit.getPlayer(LuckPermsLock.instance.utils.getPlayerUUID(ar[1]))
								.sendMessage(LuckPermsLock.instance.messages.LOGIN_SUCCESSFUL);
					} catch (NullPointerException e) {
						p.sendMessage(LuckPermsLock.instance.messages.PLAYER_NOT_FOUND.replaceAll("%player%", ar[1]));
					}
				} else {
					p.sendMessage(LuckPermsLock.instance.messages.INCORRECT_USAGE);
				}
			}
		}
		return true;
	}

	public void fillCommandAliases() {
		if (LuckPermsLock.instance.config.isList("Command.Aliases")) {
			for (String alias : LuckPermsLock.instance.config.getStringList("Command.Aliases")) {
				commandAliases.add(alias);
			}
		} else {
			LuckPermsLock.instance.log.severe("Error command alias not found");
		}
	}
}
