package tsg.luckpermslock.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.user.UserLoadEvent;
import tsg.luckpermslock.check.CheckManager;
import tsg.luckpermslock.main.LuckPermsLock;

public class LuckPermsHook {

	public EventSubscription<UserLoadEvent> subscribtions;

	public void hook() {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		EventBus eventBus = provider.getProvider().getEventBus();
		if (subscribtions == null) {
			LuckPermsLock.instance.checkManager = new CheckManager();
			LuckPermsLock.instance.checkManager.setup();
			subscribtions = eventBus.subscribe(LuckPermsLock.instance, UserLoadEvent.class,
					LuckPermsLock.instance.checkManager::onUserLoad);
		}
	}
}
