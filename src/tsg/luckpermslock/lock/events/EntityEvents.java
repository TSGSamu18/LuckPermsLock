package tsg.luckpermslock.lock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import tsg.luckpermslock.main.LuckPermsLock;

public class EntityEvents implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamageByBlock(EntityCombustEvent e) {
		if (e.getEntity() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamageByBlock(EntityDamageByBlockEvent e) {
		if (e.getEntity() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamageByBlock(EntityExplodeEvent e) {
		if (e.getEntity() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getDamager().getUniqueId())) {
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(EntityPickupItemEvent e) {
		if (e.getEntity() instanceof Player) {
			if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
}
