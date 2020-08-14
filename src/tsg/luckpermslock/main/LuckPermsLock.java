package tsg.luckpermslock.main;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import tsg.luckpermslock.Utils.Utils;
import tsg.luckpermslock.check.CheckManager;
import tsg.luckpermslock.command.CommandListener;
import tsg.luckpermslock.file.FileManager;
import tsg.luckpermslock.file.Messages;
import tsg.luckpermslock.hook.HooksManager;
import tsg.luckpermslock.lock.LockManager;

public class LuckPermsLock extends JavaPlugin {

	public static LuckPermsLock instance;

	public Logger log;
	public YamlConfiguration config;

	public Messages messages;
	public Utils utils;
	public FileManager fileManager;
	public HooksManager hookManager;
	public CheckManager checkManager;
	public LockManager lockManager;

	public LuckPermsLock() {
		instance = this;
		this.saveDefaultConfig();
		config = (YamlConfiguration) this.getConfig();
		log = Bukkit.getLogger();
		fileManager = new FileManager();
		messages = new Messages();
		utils = new Utils();
		hookManager = new HooksManager();
	}

	@Override
	public void onDisable() {
		hookManager.unhook();
	}

	@Override
	public void onEnable() {
		fileManager.setup();
		messages.setup();
		hookManager.hook();
		registerCommand(this.config.getString("Command.Main"),
				new CommandListener(this.config.getString("Command.Main")));
	}

	public void registerCommand(String fallback, Command command) {
		try {
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			commandMap.register(fallback, command);
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
			this.log.severe("Error while registering commands:" + e.toString());
		}
	}

	public void reload() {
		this.saveDefaultConfig();
		config = (YamlConfiguration) this.getConfig();
		fileManager.setup();
		messages.setup();
		hookManager.hook();
	}
}
