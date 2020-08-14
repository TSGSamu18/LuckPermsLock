package tsg.luckpermslock.file;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import tsg.luckpermslock.main.LuckPermsLock;

public class Messages {

	public String PREFIX;
	public String LIST;
	public String LIST_FORMAT;
	public String ALERT;
	public String ON_RELOAD;
	public String PLAYER_NOT_FOUND;
	public String ON_PLAYER_ADD;
	public String PLAYER_ALREADY_IN_LIST;
	public String ON_PLAYER_REMOVE;
	public String PLAYER_NOT_FOUND_IN_LIST;
	public String INCORRECT_USAGE;
	public String NO_PERMISSION;
	public String ADD_BLOCKED_IN_GAME;
	public String PLUGIN_ENABLED;
	public String PLUGIN_DISABLED;
	public String TASK_MESSAGE;
	public String TASK_KICK_MESSAGE;
	public String WRONG_PASSWORD;
	public String LOGIN_SUCCESSFUL;
	public String LOCK_HOOK_DISABLED;
	public String CHECKHOOK_DISABLED;
	public List<String> HELP;

	YamlConfiguration messages;

	public void setup() {
		messages = LuckPermsLock.instance.config;
		PREFIX = messages.getString("Messages.prefix").replaceAll("&", "§");
		LIST = traslator(messages.getString("Messages.list"));
		LIST_FORMAT = traslator(messages.getString("Messages.list-format"));
		ON_RELOAD = traslator(messages.getString("Messages.on-reload"));
		PLAYER_NOT_FOUND = traslator(messages.getString("Messages.player-not-found"));
		ALERT = traslator(messages.getString("Messages.Check.alert"));
		ON_PLAYER_ADD = traslator(messages.getString("Messages.player-added"));
		ON_PLAYER_REMOVE = traslator(messages.getString("Messages.player-removed"));
		PLAYER_ALREADY_IN_LIST = traslator(messages.getString("Messages.player-already-in-list"));
		PLAYER_NOT_FOUND_IN_LIST = traslator(messages.getString("Messages.player-not-found-in-list"));
		INCORRECT_USAGE = traslator(messages.getString("Messages.incorrect-usage"));
		NO_PERMISSION = traslator(messages.getString("Messages.no-permission"));
		ADD_BLOCKED_IN_GAME = traslator(messages.getString("Messages.add-blocked-in-game"));
		PLUGIN_ENABLED = traslator(messages.getString("Messages.plugin-enabled"));
		PLUGIN_DISABLED = traslator(messages.getString("Messages.plugin-disabled"));
		TASK_MESSAGE = traslator(messages.getString("Messages.Lock.task-message"));
		TASK_KICK_MESSAGE = traslator(messages.getString("Messages.Lock.kick-message"));
		WRONG_PASSWORD = traslator(messages.getString("Messages.Lock.wrong-password"));
		LOGIN_SUCCESSFUL = traslator(messages.getString("Messages.Lock.login-successful"));
		CHECKHOOK_DISABLED = traslator(messages.getString("Messages.Check.hook-disabled"));
		LOCK_HOOK_DISABLED = traslator(messages.getString("Messages.Lock.hook-disabled"));
		HELP = messages.getStringList("Messages.help");
	}

	public String traslator(String message) {
		return message.replaceAll("&", "§").replaceAll("%prefix%", PREFIX);
	}
}
