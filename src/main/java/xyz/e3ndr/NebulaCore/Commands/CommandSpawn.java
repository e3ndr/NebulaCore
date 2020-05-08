package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;
import xyz.e3ndr.NebulaCore.Module.Warps.Warp;

public class CommandSpawn extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.spawn")) {
			if (args.length == 0) {
				if (isConsole) {
					executor.sendMessage("Only players can execute this command.");
				} else {
					Player player = (Player) executor;
					
					player.sendMessage(NebulaCore.getLang("warp.teleported", player).replace("%warp%", "spawn"));
					spawn(player);
				}
			} else if (executor.hasPermission("Nebula.spawn.others")) {
				AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);
				
				if (player == null) {
					executor.sendMessage(NebulaCore.getLang("error.player.offline"));
				} else {
					player.sendMessage(NebulaCore.getLang("warp.teleported", player.uuid).replace("%warp%", "spawn"));
					executor.sendMessage(NebulaCore.getLang("warp-teleported-other", player.uuid).replace("%warp%", "spawn"));
					spawn(player.player);
				}
			} else {
				executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.spawn.others"));
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.spawn"));
		}
	}

	public static void spawn(Player player) {
		player.teleport(getSpawn(), TeleportCause.PLUGIN);
	}
	
	public static Location getSpawn() {
		Warp warp = Warp.getWarpFromName("spawn");
		return (warp == null) ? Bukkit.getWorlds().get(0).getSpawnLocation() : warp.loc;
	}
	
	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) && executor.hasPermission("Nebula.spawn.others")) {
			ret.addAll(Util.getPlayerNames());
		}
		
		return ret;
	}
	
}
