package tsg.luckpermslock.hook;

import tsg.luckpermslock.lock.LockManager;
import tsg.luckpermslock.main.LuckPermsLock;

public class ProtocolLibHook {

	public void hook() {
		LuckPermsLock.instance.lockManager = new LockManager();
		LuckPermsLock.instance.lockManager.setup();
	}
}
