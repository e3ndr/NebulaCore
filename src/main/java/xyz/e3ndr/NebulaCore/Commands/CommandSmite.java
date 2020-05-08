package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class CommandSmite extends BaseCommand {
	private static final HashSet<Material> filter = new HashSet<>();
	
	static {
		filter.add(Material.AIR);
		filter.add(Material.WATER);
		filter.add(Material.LAVA);
	}
	
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.smite")) {
			if (args.length == 0) {
				if (isConsole) {
					executor.sendMessage(NebulaCore.getLang("error.specify.name"));
				} else {
					Player player = ((Player) executor);
					player.getWorld().strikeLightning(player.getLastTwoTargetBlocks(filter, 100).get(0).getLocation());
				}
			} else {
				AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);
				
				if (player == null) {
					executor.sendMessage(NebulaCore.getLang("error.player.offline"));
				} else {
					player.player.getLocation().getWorld().strikeLightning(player.player.getLocation());
					player.player.sendMessage(NebulaCore.getLang("smite.smitten", player.uuid));
					executor.sendMessage(NebulaCore.getLang("smite.other", player.uuid));
				}
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.smite"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) && executor.hasPermission("Nebula.smite.others")) {
			ret.addAll(Util.getPlayerNames());
		}
		
		return ret;
	}
	
}
