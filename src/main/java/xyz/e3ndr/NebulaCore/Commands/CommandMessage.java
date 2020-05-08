package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class CommandMessage extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.message")) {
			if (isConsole) {
				executor.sendMessage("Only players can execute this command.");
			} else if (args.length == 0) {
				executor.sendMessage(NebulaCore.getLang("error.specify.name"));
			} else if (args.length == 1) {
				executor.sendMessage(NebulaCore.getLang("message.error.specify.message"));
			} else {
				AbstractPlayer sender = AbstractPlayer.getPlayer((Player) executor);
				AbstractPlayer receiver = AbstractPlayer.getPlayer(args[0]);
				
				send(sender, receiver, Util.stringify(args, 1));
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.message"));
		}
	}
	
	public static void send(AbstractPlayer sender, AbstractPlayer receiver, String message) {
		if (receiver != null) {
			int result = receiver.sendMessage(sender, message);
			
			if ((result == 1) || (result == -1)) {
				sender.sendMessage(NebulaCore.getLang("message.error.ignored"));
			}
		} else {
			sender.sendMessage(NebulaCore.getLang("error.player.offline"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) && executor.hasPermission("Nebula.message")) {
			ret.addAll(Util.getPlayerNames());
		}
		
		return ret;
	}
	
}
