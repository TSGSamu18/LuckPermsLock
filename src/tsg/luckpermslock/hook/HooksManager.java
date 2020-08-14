package tsg.luckpermslock.hook;

import com.comphenix.protocol.ProtocolLibrary;

import tsg.luckpermslock.main.LuckPermsLock;

public class HooksManager {

	LuckPermsHook luckPermsHook;
	ProtocolLibHook protocolLibHook;

	public boolean lockEnabled = false;
	public boolean checkEnabled = false;

	public void hook() {
		if (LuckPermsLock.instance.config.getBoolean("Check.Enabled")) {
			try {
				luckPermsHook = new LuckPermsHook();
				luckPermsHook.hook();
				LuckPermsLock.instance.checkManager.loadList();
				checkEnabled = true;
			} catch (NullPointerException e) {
				LuckPermsLock.instance.log.severe("LuckPerms not found!");
			}
		}
		if (LuckPermsLock.instance.config.getBoolean("Lock.Enabled")) {
			try {
				protocolLibHook = new ProtocolLibHook();
				protocolLibHook.hook();
				lockEnabled = true;
			} catch (NullPointerException e) {
				LuckPermsLock.instance.log.severe("ProtocolLb not found!");
			}
		}
	}

	public void unhook() {
		if (checkEnabled) {
			luckPermsHook.subscribtions.close();
			LuckPermsLock.instance.checkManager.saveList();
		}
		if (lockEnabled) {
			LuckPermsLock.instance.config.set("Lock.Password", LuckPermsLock.instance.lockManager.password);
			LuckPermsLock.instance.saveConfig();
			LuckPermsLock.instance.lockManager.blockedPlayer.clear();
			ProtocolLibrary.getProtocolManager().removePacketListeners(LuckPermsLock.instance);
		}
	}
}
