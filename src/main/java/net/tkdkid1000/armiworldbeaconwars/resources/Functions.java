package net.tkdkid1000.armiworldbeaconwars.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.tkdkid1000.armiworldbeaconwars.ArmiWorldBeaconwars;
import net.tkdkid1000.armiworldbeaconwars.utils.BoundingBox;

public class Functions {

	public static List<Block> getBlocks(BoundingBox box, World world) {
		int x1 = (int) box.getMinX();
		int y1 = (int) box.getMinY();
		int z1 = (int) box.getMinZ();
		int x2 = (int) box.getMaxX();
		int y2 = (int) box.getMaxY();
		int z2 = (int) box.getMaxZ();
		List<Block> blocks = new ArrayList<Block>();
		for (int x=x1; x<x2+1; x++) {
			for (int y=y1; y<y2+1; y++) {
				for (int z=z1; z<z2+1; z++) {
					if (!(new Location(world, x, y, z).getBlock().getType() == Material.AIR)) {
						blocks.add(new Location(world, x, y, z).getBlock());
					}
				}
			}
		}
		return blocks;
	}
	
	public static void elimPlayer(Player player) {
		for (UUID uuid : ArmiWorldBeaconwars.players) {
			Player p = Bukkit.getPlayer(uuid);
			p.sendMessage(ChatColor.RED + player.getName() + " has been eliminated!");
		}
		if (ArmiWorldBeaconwars.players.contains(player.getUniqueId())) {
			ArmiWorldBeaconwars.players.remove(player.getUniqueId());
		}
		if (player.isDead()) {
			player.spigot().respawn();
		}
		player.sendMessage(ChatColor.RED + "You have been eliminated from the game!");
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().setArmorContents(new ItemStack[] {new ItemStack(Material.AIR),
				new ItemStack(Material.AIR),
				new ItemStack(Material.AIR),
				new ItemStack(Material.AIR)});
		player.setGameMode(GameMode.SPECTATOR);
		for (int x=0; x<8; x++) {
			List<UUID> players = ArmiWorldBeaconwars.playerlist.get(x);
			List<String> colors = ArmiWorldBeaconwars.colors;
			if (players.contains(player.getUniqueId())) {
				players.remove(player.getUniqueId());
				if (players.size() == 0) {
					ArmiWorldBeaconwars.deadteams++;
					for (UUID uuid: ArmiWorldBeaconwars.players) {
						Player p = Bukkit.getPlayer(uuid);
						p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + colors.get(x) + " team has been eliminated!");
					}
				}
			}
		}
	}
}
