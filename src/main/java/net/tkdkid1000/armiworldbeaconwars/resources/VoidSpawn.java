package net.tkdkid1000.armiworldbeaconwars.resources;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.tkdkid1000.armiworldbeaconwars.ArmiWorldBeaconwars;

public class VoidSpawn implements Listener {

	private FileConfiguration config;
	private ArmiWorldBeaconwars beacon;

	public VoidSpawn(ArmiWorldBeaconwars beacon, FileConfiguration config) {
		this.config = config;
		this.beacon = beacon;
	}
	
	public void register() {
		beacon.getServer().getPluginManager().registerEvents(this, beacon);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (!event.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(config.getString("spawnworld"))) return;
		if (event.getPlayer().getLocation().getY() < 110) {
			event.getPlayer().performCommand("spawn");
		}
	}
}
