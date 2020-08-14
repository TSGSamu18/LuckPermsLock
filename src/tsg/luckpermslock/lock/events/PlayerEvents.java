package tsg.luckpermslock.lock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import tsg.luckpermslock.main.LuckPermsLock;

public class PlayerEvents implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDrop(PlayerDropItemEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(BlockBreakEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(BlockPlaceEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(PlayerItemDamageEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteract(PlayerPortalEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for (String permission : LuckPermsLock.instance.lockManager.permissions) {
			if (p.hasPermission(permission)) {
				LuckPermsLock.instance.lockManager.blockedPlayer.put(p.getUniqueId(), 0);
				new BukkitRunnable() {
					@Override
					public void run() {
						if (!LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(p.getUniqueId())) {
							this.cancel();
							return;
						}
						try {
							p.sendMessage(LuckPermsLock.instance.messages.TASK_MESSAGE);
						} catch (NullPointerException e) {
							this.cancel();
						}
					}
				}.runTaskTimer(LuckPermsLock.instance, 0L, LuckPermsLock.instance.lockManager.messageTaskDelay * 20L);
				new BukkitRunnable() {
					@Override
					public void run() {
						if (!LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(p.getUniqueId())) {
							this.cancel();
							return;
						}
						try {
							p.kickPlayer(LuckPermsLock.instance.messages.TASK_KICK_MESSAGE);
							this.cancel();
						} catch (NullPointerException e) {
							this.cancel();
						}
					}
				}.runTaskTimer(LuckPermsLock.instance, LuckPermsLock.instance.lockManager.kickTaskDelay * 20L, 0L);
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTeleport(PlayerTeleportEvent e) {
		if (LuckPermsLock.instance.lockManager.blockedPlayer.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
