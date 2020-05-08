package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class CommandPing extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.ping")) {
			if (args.length == 0) {
				if (isConsole) {
					executor.sendMessage("Only players can execute this command.");
				} else {
					Player player = (Player) executor;
					executor.sendMessage(NebulaCore.getLang("ping", player));
				}
			} else if (executor.hasPermission("Nebula.ping.others")) {
				AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);
				
				if (player == null) {
					executor.sendMessage(NebulaCore.getLang("error.player.offline"));
				} else {
					executor.sendMessage(NebulaCore.getLang("ping.other", player.uuid));
				}
			} else {
				executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.ping.others"));
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.ping"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) && executor.hasPermission("Nebula.ping.others")) {
			ret.addAll(Util.getPlayerNames());
		}
		
		return ret;
	}
	
}
