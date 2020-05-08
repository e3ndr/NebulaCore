package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class CommandReply extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.message")) {
			if (isConsole) {
				executor.sendMessage("Only players can execute this command.");
			} else if (args.length == 0) {
				executor.sendMessage(NebulaCore.getLang("message.error.specify.message"));
			} else {
				AbstractPlayer sender = AbstractPlayer.getPlayer((Player) executor);
				AbstractPlayer last = sender.lastReceived;
				
				if (last == null) {
					executor.sendMessage(NebulaCore.getLang("message.error.reply"));
				} else {
					CommandMessage.send(sender, last, Util.stringify(args, 0));
				}
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.message"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		return new ArrayList<>();
	}
	
}
