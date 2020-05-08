package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class CommandInvsee extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.invsee")) {
			if (isConsole) {
				executor.sendMessage("Only players can execute this command.");
			} else {
				AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);
				
				if (player == null) {
					executor.sendMessage(NebulaCore.getLang("error.player.offline"));
				} else {
					((Player) executor).openInventory(player.player.getInventory());
				}
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.invsee"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) &&executor.hasPermission("Nebula.extinguish.others")) {
			ret.addAll(Util.getPlayerNames());
		}
		
		return ret;
	}
	
}
